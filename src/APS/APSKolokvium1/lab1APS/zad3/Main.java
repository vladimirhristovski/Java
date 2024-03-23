package APS.APSKolokvium1.lab1APS.zad3;

import java.util.Arrays;
import java.util.Scanner;

class QuarterlySales {

    private final int numOfSales;
    private final int[] revenues;
    private final int quarterNo;

    public QuarterlySales(int numOfSales, int[] revenues, int quarterNo) {
        this.numOfSales = numOfSales;
        this.revenues = revenues;
        this.quarterNo = quarterNo;
    }

    public int totalRevenue() {
//        int total = 0;
//        for (int revenue : revenues) {
//            total += revenue;
//        }
//        return total;

        return Arrays.stream(revenues).sum();

    }
}

class SalesPerson {

    private final String name;
    private final QuarterlySales[] quarters;

    public SalesPerson(String name, QuarterlySales[] quarters) {
        this.name = name;
        this.quarters = quarters;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append(name).append("   ");
        int all = 0;
        for (QuarterlySales quarter : quarters) {
            output.append(quarter.totalRevenue()).append("   ");
            all += quarter.totalRevenue();
        }
        output.append(all);
        return output.toString();
    }

    public QuarterlySales[] getQuarters() {
        return quarters;
    }

    public String getName() {
        return name;
    }
}

public class Main
{

    public static int sumSales(SalesPerson sp) {
//        int total = 0;
//        for (QuarterlySales quarter : sp.getQuarters()) {
//            total += quarter.totalRevenue();
//        }
//        return total;
        return Arrays.stream(sp.getQuarters()).mapToInt(quarter -> quarter.totalRevenue()).sum();
    }

    public static SalesPerson salesChampion(SalesPerson[] arr) {
        SalesPerson champion = null;
        for (SalesPerson person : arr) {
            if (champion == null) {
                champion = person;
            }
            if (sumSales(champion) < sumSales(person)) {
                champion = person;
            }
        }
        return champion;
    }

    public static void table(SalesPerson[] arr) {
        System.out.println("SP   1   2   3   4   Total");
        for (SalesPerson person : arr) {
            System.out.println(person.toString());
        }
        System.out.println();
    }

    public static void main(String[] args) {

        int n;
        Scanner input = new Scanner(System.in);
        n = input.nextInt();
        SalesPerson[] arr = new SalesPerson[n];
        for (int i = 0; i < n; i++) {
            String namePerson = input.next();
            input.nextLine();
            QuarterlySales[] quarters = new QuarterlySales[4];
            for (int j = 0; j < 4; j++) {
                int revNum = input.nextInt();
                int[] revs = new int[revNum];
                for (int k = 0; k < revNum; k++) {
                    revs[k] = input.nextInt();
                }
                quarters[j] = new QuarterlySales(revNum, revs, j);
            }
            arr[i] = new SalesPerson(namePerson, quarters);
        }

        table(arr);
        System.out.println("SALES CHAMPION: " + salesChampion(arr).getName());
    }
}