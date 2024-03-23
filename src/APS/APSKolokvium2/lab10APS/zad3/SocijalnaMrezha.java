package APS.APSKolokvium2.lab10APS.zad3;

import java.util.*;

public class SocijalnaMrezha {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), k, ct=0; sc.nextLine();
        Map<String, ArrayList<String> > lugje = new HashMap<>();
        Map<String, Boolean> visited = new HashMap<>();
        String ime,covek,from,to;
        for(int i=0; i<n; i++)
        {
            ime = sc.nextLine();
            lugje.put(ime, new ArrayList<>());
            visited.put(ime,false);
            k=sc.nextInt();
            sc.nextLine();
            for(int j=0; j<k; j++)
            {
                covek = sc.nextLine();
                lugje.get(ime).add(covek);
                if(!visited.containsKey(covek))
                {
                    visited.put(covek,false);
                }
            }
        }
        from=sc.nextLine(); to=sc.nextLine();
        Queue<String> q = new ArrayDeque<>();
        List<String> lista;
        q.add(from);
        while(!visited.get(to))
        {
            k=q.size();

            for(int i=0; i<k; i++) {
                System.out.println(q.peek());
                lista = lugje.get(q.peek());
                for (String s : lista) {
                    if (!visited.get(s)) q.add(s);
                }
                visited.replace(q.remove(),true);
            }
            ct++;
        }
        System.out.println(ct-1);
    }
}