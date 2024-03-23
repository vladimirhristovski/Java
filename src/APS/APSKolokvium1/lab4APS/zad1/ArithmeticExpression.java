package APS.APSKolokvium1.lab4APS.zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArithmeticExpression {

//    static int presmetaj(char[] c, int l, int r) {
//        Stack<Character>zagradi=new Stack<Character>();
//        Stack<Integer>operands=new Stack<Integer>();
//        Stack<Character>operators=new Stack<Character>();
//        int sum=0;
//        int num1=0;
//        int num2=0;
//        char sign=0;
//        for (int i = l; i < r+1; i++) {
//            if(c[i]=='('){
//                zagradi.push(c[i]);
//            } else if(c[i]=='+'||c[i]=='-'){
//                operators.push(c[i]);
//            } else if(c[i]==')'){
//                zagradi.pop();
//                num2=operands.pop();
//                num1=operands.pop();
//                sign=operators.pop();
//                if(sign=='+'){
//                    sum=num1+num2;
//                } else if (sign=='-') {
//                    sum=num1-num2;
//                }
//                operands.push(sum);
//                sum=0;
//            } else if (Character.isDigit(c[i])) {
//                operands.push(Integer.parseInt(String.valueOf(c[i])));
//            }
//        }
//        sum=operands.pop();
//        return sum;
//    }

    // static int presmetaj(char[] c, int l, int r) {
    //     Stack<String> elements = new Stack<String>();
    //     int sum = 0;
    //     int num1 = 0;
    //     int num2 = 0;
    //     String sign = "";
    //     for (int i = l; i < r + 1; i++) {
    //         if (c[i] != ')') {
    //             elements.push(String.valueOf(c[i]));
    //         } else if (c[i] == ')') {
    //             num2 = Integer.parseInt(String.valueOf(elements.pop()));
    //             sign = elements.pop();
    //             num1 = Integer.parseInt(String.valueOf(elements.pop()));
    //             if (sign.equals("+")) {
    //                 sum += num1 + num2;
    //             } else if (sign.equals("-")) {
    //                 sum += num1 - num2;
    //             }
    //             elements.pop();
    //             elements.push(Integer.toString(sum));
    //             sum = 0;
    //         }
    //     }
    //     sum = Integer.parseInt(String.valueOf(elements.pop()));
    //     return sum;
    // }

    static int presmetaj(char[] c, int l, int r) {
        if (l == r) {
            return toInt(c[l]);
        }

        int bracketsNumber = 0, signIndex = -1;
        for (int i = l; i < r; i++) {
            if (c[i] == '(') bracketsNumber++;
            else if (c[i] == ')') bracketsNumber--;
            if ((c[i] == '+' || c[i] == '-') && bracketsNumber == 0) signIndex = i;
        }

        if (signIndex == -1) return presmetaj(c, l + 1, r - 1);

        if (c[signIndex] == '+') return presmetaj(c, l, signIndex - 1) + presmetaj(c, signIndex + 1, r);
        else if (c[signIndex] == '-') return presmetaj(c, l, signIndex - 1) - presmetaj(c, signIndex + 1, r);

        return 0;
    }

    static int toInt(char c) {
        return Integer.parseInt(String.valueOf(c));
    }


    public static void main(String[] args) throws Exception {
        int i, j, k;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String expression = br.readLine();
        char[] exp = expression.toCharArray();

        int rez = presmetaj(exp, 0, exp.length - 1);
        System.out.println(rez);

        br.close();

    }

}