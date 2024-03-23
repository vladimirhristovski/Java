package APS.APSKolokvium1.lab1APS.zad2;

import java.util.Scanner;

public class ReverseWord
{
    public static void printReversed(String word) {
        StringBuilder reverseWord = new StringBuilder();
        reverseWord.append(word);
        reverseWord.reverse();
        System.out.println(reverseWord);
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        String[] words = new String[n];
        for (int i = 0; i < n; i++) {
            words[i] = input.next();
        }
//        for(int i=0;i<n;i++){
//            printReversed(words[i]);
//        }
        for (String word : words) {
            printReversed(word);
        }
    }
}