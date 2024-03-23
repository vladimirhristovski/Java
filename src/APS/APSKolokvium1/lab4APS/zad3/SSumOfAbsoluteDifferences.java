package APS.APSKolokvium1.lab4APS.zad3;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class SSumOfAbsoluteDifferences {

    static class largestDiff{
        private int leftIndex;
        private int rightIndex;
        private int largestDiff;

        public largestDiff(int leftIndex, int rightIndex, int largestDiff) {
            this.leftIndex = leftIndex;
            this.rightIndex = rightIndex;
            this.largestDiff = largestDiff;
        }

        public int getLeftIndex() {
            return leftIndex;
        }

        public int getRightIndex() {
            return rightIndex;
        }

        public int getLargestDiff() {
            return largestDiff;
        }
    }
    static int solve(int numbers[], int N, int K) {
        largestDiff[]array=new largestDiff[N];
        largestDiff[]finalArray=new largestDiff[K];
        int k=0;
        int s=0;
        int maxDiff;
        int leftIdx;
        int rightIdx;
        for (int i = 0; i < N; i++) {
            maxDiff=0;
            leftIdx=0;
            rightIdx=0;
            for (int j = 0; j < i; j++) {
                if(Math.abs(numbers[i]-numbers[j])>maxDiff){
                    maxDiff=Math.abs(numbers[i]-numbers[j]);
                    leftIdx=j;
                    rightIdx=i;
                }
            }
            array[k++]=new largestDiff(leftIdx,rightIdx,maxDiff);
        }
        Arrays.sort(array, Comparator.comparing(largestDiff::getLargestDiff));
        for (int i = N-K+1; i < N; i++) {
            finalArray[s++]=array[i];
        }
        int maxValue=0;
        for (int i = 0; i < s; i++) {
            maxValue+=finalArray[i].getLargestDiff();
        }
        return maxValue;
    }

    public static void main(String[] args) throws Exception {
        int i,j,k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        int numbers[] = new int[N];

        st = new StringTokenizer(br.readLine());
        for (i=0;i<N;i++) {
            numbers[i] = Integer.parseInt(st.nextToken());
        }

        int res = solve(numbers, N, K);
        System.out.println(res);

        br.close();

    }

}