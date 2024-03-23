package APS.APSKolokvium2.lab7APS.zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;


public class Lozinki {
    public static void main (String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        HashMap<String,String>accounts=new HashMap<String,String>();
        for(int i=1;i<=N;i++){
            String imelozinka = br.readLine();
            String[] pom = imelozinka.split(" ");
            String name=pom[0];
            String password=pom[1];
            accounts.put(name,password);
        }
        String logIn=br.readLine();
        while(!logIn.equals("KRAJ")){
            String[] pom = logIn.split(" ");
            String name=pom[0];
            String password=pom[1];
            if(accounts.containsKey(name)&&accounts.get(name).equals(password)){
                System.out.println("Najaven");
                break;
            }else {
                System.out.println("Nenajaven");
            }
            logIn=br.readLine();
        }
    }
}