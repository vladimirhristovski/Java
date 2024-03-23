package APS.APSKolokvium1.zad5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExpressionEvaluator {

    public static int evaluateExpression(String expression) {
        String[] plusNumbers = expression.split("\\+");

        int sum;
        int total = 0;

        for (String plusNumber : plusNumbers) {
            String[] multNumber = plusNumber.split("\\*");
            sum = 1;
            for (String s : multNumber) {
                sum *= Integer.parseInt(s);
            }
            total += sum;
        }

        return total;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        System.out.println(evaluateExpression(input.readLine()));
    }

}