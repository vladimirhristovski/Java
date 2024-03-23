package APS.APSKolokvium1.lab3APS.zad2;

import java.util.Scanner;

public class CountWordPairs {

    //TODO: implement function
    public static int countWordPairs(String[] words) {
        int pairs = 0;

        for (String word : words) {
            for (String s : words) {
                if (word.equals(s)) {
                    continue;
                }
                if (s.startsWith(String.valueOf(word.charAt(0)))) {
                    pairs++;
                }
            }
        }
        return pairs / 2;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int N = input.nextInt();

        String[] words = new String[N];

        for (int i = 0; i < N; i++) {
            words[i] = input.next();
        }

        System.out.println(countWordPairs(words));

    }
}