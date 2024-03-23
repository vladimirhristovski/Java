package APS.APSKolokvium1.lab4APS.zad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ZigZagSequence {

    static int najdiNajdolgaCikCak(int[] a) {
        int zigZag = 1;
        int maxZigZag = 0;
        boolean positive = a[0] > 0;
        for (int j : a) {
            if (j > 0 && positive) {
                maxZigZag = Math.max(zigZag, maxZigZag);
                zigZag = 1;
                continue;
            } else if (j < 0 && !positive) {
                maxZigZag = Math.max(zigZag, maxZigZag);
                zigZag = 1;
                continue;
            } else if (j == 0) {
                maxZigZag = Math.max(zigZag, maxZigZag);
                zigZag = 0;
                continue;
            }
            positive = j > 0;
            zigZag++;
        }
        maxZigZag = Math.max(zigZag, maxZigZag);
        return maxZigZag;
    }

    public static void main(String[] args) throws Exception {
        int i, j, k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        int[] a = new int[N];
        for (i = 0; i < N; i++)
            a[i] = Integer.parseInt(br.readLine());

        int rez = najdiNajdolgaCikCak(a);
        System.out.println(rez);

        br.close();

    }

}