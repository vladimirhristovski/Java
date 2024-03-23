package NP.NPKolokvium1.lab1NP.zad2;

import java.util.Scanner;
import java.util.stream.IntStream;

public class RomanConverterTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        IntStream.range(0, n).forEach(x -> System.out.println(RomanConverter.toRoman(scanner.nextInt())));
        scanner.close();
    }
}


class RomanConverter {
    /**
     * Roman to decimal converter
     *
     * @param n number in decimal format
     * @return string representation of the number in Roman numeral
     */
    public static String toRoman(int n) {
        String romanString = "";
        if (n >= 1000) {
            int Mcount = n / 1000;
            romanString = romanString + "M".repeat(Mcount);
            n %= 1000;
        }
        if (n >= 900) {
            int CMcount = n / 900;
            romanString = romanString + "CM".repeat(CMcount);
            n %= 900;
        }
        if (n >= 500) {
            int Dcount = n / 500;
            romanString = romanString + "D".repeat(Dcount);
            n %= 500;
        }
        if (n >= 400) {
            int CDcount = n / 400;
            romanString = romanString + "CD".repeat(CDcount);
            n %= 400;
        }
        if (n >= 100) {
            int Ccount = n / 100;
            romanString = romanString + "C".repeat(Ccount);
            n %= 100;
        }
        if (n >= 90) {
            int XCcount = n / 90;
            romanString = romanString + "XC".repeat(XCcount);
            n %= 90;
        }
        if (n >= 50) {
            int Lcount = n / 50;
            romanString = romanString + "L".repeat(Lcount);
            n %= 50;
        }
        if (n >= 40) {
            int XLcount = n / 40;
            romanString = romanString + "XL".repeat(XLcount);
            n %= 40;
        }
        if (n >= 10) {
            int Xcount = n / 10;
            romanString = romanString + "X".repeat(Xcount);
            n %= 10;
        }
        if (n >= 9) {
            int IXcount = n / 9;
            romanString = romanString + "IX".repeat(IXcount);
            n %= 9;
        }
        if (n >= 5) {
            int Vcount = n / 5;
            romanString = romanString + "V".repeat(Vcount);
            n %= 5;
        }
        if (n >= 4) {
            int IVcount = n / 4;
            romanString = romanString + "IV".repeat(IVcount);
            n %= 4;
        }
        if (n >= 1) {
            romanString = romanString + "I".repeat(n);
        }
        return romanString;
    }

}