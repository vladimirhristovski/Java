package APS.APSKolokvium1.lab5APS.zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class OddEvenSort {

    static void oddEvenSort(int[] a, int n) {
        int[] even = new int[n];
        int k = 0;
        int[] odd = new int[n];
        int m = 0;
        for (int i : a) {
            if (i % 2 == 0) {
                even[k++] = i;
            } else {
                odd[m++] = i;
            }
        }
        odd = Arrays.copyOf(odd, m);
        even = Arrays.copyOf(even, k);
        Arrays.sort(odd);
        Arrays.sort(even);
        int[] temp = new int[k];
        int t = 0;
        for (int i = k - 1; i >= 0; i--) {
            temp[t++] = even[i];
        }
        System.arraycopy(odd, 0, a, 0, m);
        t = 0;
        for (int i = m; i < n; i++) {
            a[i] = temp[t++];
        }
    }

    public static void main(String[] args) throws IOException {
        int i;
        BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
        String s = stdin.readLine();
        int n = Integer.parseInt(s);

        s = stdin.readLine();
        String[] pom = s.split(" ");
        int[] a = new int[n];
        for (i = 0; i < n; i++)
            a[i] = Integer.parseInt(pom[i]);
        oddEvenSort(a, n);
        for (i = 0; i < n - 1; i++)
            System.out.print(a[i] + " ");
        System.out.print(a[i]);
    }
}