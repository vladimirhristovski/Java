package APS.APSKolokvium1.lab1APS.zad1;

import java.util.Scanner;

public class PushZero
{
    static void pushZerosToBeginning(int[] arr, int n) {
        int m = 0;
        int[] zeros = new int[n];
        System.out.println("Transformiranata niza e:");

//        for (int i = 0; i < n; i++) {
//            if (arr[i] == 0) {
//                System.out.print(arr[i] + " ");
//            }
//        }
//        for (int i = 0; i < n; i++) {
//            if (arr[i] != 0) {
//                System.out.print(arr[i] + " ");
//            }
//        }

        for (int element : arr) {
            if (element == 0) {
                zeros[m] = element;
                m++;
            }
        }

        for (int element : arr) {
            if (element != 0) {
                zeros[m] = element;
                m++;
            }
        }

//        for(int i=0;i<n;i++){
//            if(arr[i]==0){
//                zeros[m]=arr[i];
//                m++;
//            }
//        }
//        for(int i=0;i<n;i++) {
//            if (arr[i] != 0) {
//                zeros[m]=arr[i];
//                m++;
//            }
//        }

        for (int i = 0; i < m; i++) {
            System.out.print(zeros[i] + " ");
        }

//        System.out.println(Arrays.toString(zeros) +" ");
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = input.nextInt();
        }
        pushZerosToBeginning(array, n);

    }
}