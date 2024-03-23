//package APS.APSKolokvium2.lab7APS.zad4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

class Lek{
    String ime;
    int pozLista;
    int cena;
    int kolicina;

    public String getIme() {                return ime;                 }
    public void setIme(String ime) {        this.ime = ime;	            }
    public int getCena() {      		    return cena;	            }
    public void setCena(int cena) {		    this.cena = cena;           }
    public int getKolicina() {  		    return kolicina;	        }
    public void setKolicina(int kolicina) { this.kolicina = kolicina;	}
    public int getPozLista() {      		return pozLista;        	}

    public Lek(String ime, int pozLista, int cena, int kolicina) {
        this.ime = ime.toUpperCase();
        this.pozLista = pozLista;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    @Override
    public String toString() {
        if(pozLista==1) return ime+"\n"+"POZ\n"+cena+"\n"+kolicina;
        else return ime+"\n"+"NEG\n"+cena+"\n"+kolicina;
    }
}

class LekKluch{
    String ime;
    public LekKluch(String ime) {
        this.ime = ime.toUpperCase();
    }

    @Override
    public int hashCode() {
        // TODO implement
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LekKluch lekKluch = (LekKluch) o;
        return Objects.equals(ime, lekKluch.ime);
    }
}

class PillInfo{
    String name;
    String positivity;
    int price;
    int amount;

    public PillInfo(String name,int positivity, int price, int amount) {
        this.name=name;
        if(positivity==1){
            this.positivity="POZ";
        } else if (positivity==0) {
            this.positivity="NEG";
        }
        this.price = price;
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s\n%s\n%d\n%d",name,positivity,price,amount);
    }
}

public class Apteka {
    public static void main(String[] args) throws  IOException {
        HashMap<String,PillInfo>apteka=new HashMap<>();
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int pillNum=Integer.parseInt(br.readLine());
        for (int i = 0; i < pillNum; i++) {
            String input = br.readLine();
            String[] words = input.split("\\s+");
            String name=words[0].toUpperCase();
            int pos=Integer.parseInt(words[1]);
            int price = Integer.parseInt(words[2]);
            int amount=Integer.parseInt(words[3]);
            PillInfo info=new PillInfo(name,pos,price,amount);
            apteka.put(name,info);
        }

        String buy=br.readLine().toUpperCase();
        boolean flag;
        while(!buy.equals("KRAJ")){
            int pillAmmount=Integer.parseInt(br.readLine());
            flag=false;
            if(apteka.containsKey(buy)){
                PillInfo info=apteka.get(buy);
                if(info.getAmount()<pillAmmount){
                    flag=true;
                }
                if(flag){
                    System.out.println(info);
                    System.out.println("Nema dovolno lekovi");
                }else {
                    System.out.println(info);
                    System.out.println("Napravena naracka");
                    apteka.get(buy).setAmount(info.getAmount()-pillAmmount);
                }
            }else {
                System.out.println("Nema takov lek");
            }
            buy=br.readLine().toUpperCase();
        }
    }
}




class CBHT<K, E> {
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    // probajte da ja reshite ovaa zadacha bez koristenje na ovoj metod
    // try to solve this task without using this method

    // public SLLNode<MapEntry<K,E>> search(K targetKey) {
    //     int b = hash(targetKey);
    //     for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
    //         if (targetKey.equals(curr.element.key))     return curr;
    //     }
    //     return null;
    // }

    public void insert(K key, E val) {
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(curr.element.key)) {
                curr.element = newEntry;
                return;
            }
        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }

    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(curr.element.key)) {
                if (pred == null)   buckets[b] = curr.succ;
                else                pred.succ = curr.succ;
                return;
            }
        }
    }

    public String toString() {
        String temp = "";
        for (int i = 0; i < buckets.length; i++) {
            temp += i + ":";
            for (SLLNode<MapEntry<K,E>> curr = buckets[i]; curr != null; curr = curr.succ) {
                temp += curr.element.toString() + " ";
            }
            temp += "\n";
        }
        return temp;
    }

}
class MapEntry<K,E> {
    K key;
    E value;
    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }
    public String toString () {
        return "<" + key + "," + value + ">";
    }
}

class SLLNode<E> {
    protected E element;
    protected SLLNode<E> succ;
    public SLLNode(E elem, SLLNode<E> succ) {
        this.element = elem;
        this.succ = succ;
    }
    @Override
    public String toString() {
        return element.toString();
    }
}