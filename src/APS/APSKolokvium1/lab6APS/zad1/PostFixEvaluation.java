package APS.APSKolokvium1.lab6APS.zad1;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;

interface Stack<E> {

    // Elementi na stekot se objekti od proizvolen tip.

    // Metodi za pristap:

    boolean isEmpty();
    // Vrakja true ako i samo ako stekot e prazen.

    E peek();
    // Go vrakja elementot na vrvot od stekot.

    // Metodi za transformacija:

    void clear();
    // Go prazni stekot.

    void push(E x);
    // Go dodava x na vrvot na stekot.

    E pop();
    // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
}

class ArrayStack<E> implements Stack<E> {
    private final E[] elems;
    private int depth;

    @SuppressWarnings("unchecked")
    public ArrayStack(int maxDepth) {
        // Konstrukcija na nov, prazen stek.
        elems = (E[]) new Object[maxDepth];
        depth = 0;
    }


    public boolean isEmpty() {
        // Vrakja true ako i samo ako stekot e prazen.
        return (depth == 0);
    }


    public E peek() {
        // Go vrakja elementot na vrvot od stekot.
        if (depth == 0) throw new NoSuchElementException();
        return elems[depth - 1];
    }


    public void clear() {
        // Go prazni stekot.
        for (int i = 0; i < depth; i++) elems[i] = null;
        depth = 0;
    }


    public void push(E x) {
        // Go dodava x na vrvot na stekot.
        elems[depth++] = x;
    }


    public E pop() {
        // Go otstranuva i vrakja elementot shto e na vrvot na stekot.
        if (depth == 0) throw new NoSuchElementException();
        E topmost = elems[--depth];
        elems[depth] = null;
        return topmost;
    }
}

public class PostFixEvaluation {

    static int evaluatePostfix(char[] izraz, int n) {
        Stack<String> elements = new ArrayStack<>(izraz.length);
        int num1;
        int num2;
        StringBuilder transform = new StringBuilder();
        for (char c : izraz) {
            transform.append(c);
        }
        String[] numbers = transform.toString().split(" ");
        for (String number : numbers) {
            if (number.equals("+") || number.equals("-") || number.equals("*") || number.equals("/")) {
                num2 = Integer.parseInt(elements.pop());
                num1 = Integer.parseInt(elements.pop());
                switch (number) {
                    case "+":
                        elements.push(String.valueOf(num1 + num2));
                        continue;
                    case "-":
                        elements.push(String.valueOf(num1 - num2));
                        continue;
                    case "*":
                        elements.push(String.valueOf(num1 * num2));
                        continue;
                    case "/":
                        elements.push(String.valueOf(num1 / num2));
                }
            } else {
                elements.push((number));
            }
        }
        return Integer.parseInt(elements.pop());
    }

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String expression = br.readLine();
        char[] exp = expression.toCharArray();

        int rez = evaluatePostfix(exp, exp.length);
        System.out.println(rez);

        br.close();

    }

}