package APS.APSKolokvium1.lab6APS.zad2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;

public class CheckXML {

    public static void main(String[] args) throws Exception {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int n = Integer.parseInt(s);
        String[] redovi = new String[n];

        for (int i = 0; i < n; i++)
            redovi[i] = br.readLine();

        int valid = 1;

        Stack<String> tags = new Stack<String>();
        String lastTag;
        for (String line : redovi) {
            if (line.startsWith("[")) {
                if (line.startsWith("[/")) {
                    lastTag = tags.pop();
                    if (!line.contains(lastTag)) {
                        valid = 0;
                        break;
                    }
                } else {
                    tags.push(line.substring(1));
                }
            }
        }
        // Vasiot kod tuka
        // Moze da koristite dopolnitelni funkcii ako vi se potrebni

        System.out.println(valid);

        br.close();
    }
}