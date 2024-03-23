package APS.APSKolokvium2.lab9APS.zad2;

import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeMap;


public class CompetitionTest {
    public static TreeMap<Double, Double> put(TreeMap<Double, Double> treemap, double competitor){
        if(!treemap.containsKey(competitor+0.0)){
            treemap.put(competitor,competitor);
        }else {
            put(treemap,competitor+0.1);
        }
        return treemap;
    }

    public static void get(TreeMap<Double, Double> treemap, double competitor){
        DecimalFormat df = new DecimalFormat("#.#");
        if(!treemap.containsKey(competitor)){
            competitor-=0.1;
            competitor= Double.parseDouble(df.format(competitor));
            get(treemap,competitor);

        }else {
            int pos=treemap.headMap(competitor).size();
            System.out.println(pos+1);
            return;
        }
        if(competitor== Math.floor(competitor)){
            return;
        }
    }

    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        TreeMap<Double, Double> treemap=new TreeMap<Double, Double>(Collections.reverseOrder());
        int n=scanner.nextInt();
        double competitor;

        for (int i = 0; i < n; i++) {
            competitor= scanner.nextInt();
            treemap=put(treemap,competitor);
        }
        double m=scanner.nextInt();
        get(treemap,m+0.9);
    }
}