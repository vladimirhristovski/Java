package NP.NPKolokvium1.zad9;

import java.util.Scanner;

public class TripleTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.avarage());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.avarage());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.avarage());
        tDouble.sort();
        System.out.println(tDouble);
    }
}
class Triple<T extends Number>{
    T a,b,c;

    public Triple(T a, T b, T c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }
    double max(){
        double max=a.doubleValue();
        if(b.doubleValue()>max){
            max=b.doubleValue();
        }
        if(c.doubleValue()>max){
            max=c.doubleValue();
        }
        return max;
    }
    double avarage(){
        return (a.doubleValue()+ b.doubleValue()+c.doubleValue())/3;
    }
    void sort(){
        T tmp;
        if(a.doubleValue()>b.doubleValue()){
            tmp=a;
            a=b;
            b=tmp;
        }
        if(a.doubleValue()>c.doubleValue()){
            tmp=a;
            a=c;
            c=tmp;
        }
        if(b.doubleValue()>c.doubleValue()){
            tmp=b;
            b=c;
            c=tmp;
        }
    }

    @Override
    public String toString() {
        return String.format("%.2f %.2f %.2f",a.doubleValue(),b.doubleValue(),c.doubleValue());
    }
}