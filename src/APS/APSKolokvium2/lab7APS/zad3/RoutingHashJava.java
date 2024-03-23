package APS.APSKolokvium2.lab7APS.zad3;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class MapEntry<K extends Comparable<K>,E> implements Comparable<K> {

    K key;
    E value;
    public MapEntry (K key, E val) {
        this.key = key;
        this.value = val;
    }

    public int compareTo (K that) {
        @SuppressWarnings("unchecked")
        MapEntry<K,E> other = (MapEntry<K,E>) that;
        return this.key.compareTo(other.key);
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



class CBHT<K extends Comparable<K>, E> {
    private SLLNode<MapEntry<K,E>>[] buckets;

    @SuppressWarnings("unchecked")
    public CBHT(int m) {
        buckets = (SLLNode<MapEntry<K,E>>[]) new SLLNode[m];
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % buckets.length;
    }

    public SLLNode<MapEntry<K,E>> search(K targetKey) {
        int b = hash(targetKey);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (targetKey.equals(((MapEntry<K, E>) curr.element).key))
                return curr;
        }
        return null;
    }

    public void insert(K key, E val) {
        MapEntry<K, E> newEntry = new MapEntry<K, E>(key, val);
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> curr = buckets[b]; curr != null; curr = curr.succ) {
            if (key.equals(((MapEntry<K, E>) curr.element).key)) {
                curr.element = newEntry;
                return;
            }
        }
        buckets[b] = new SLLNode<MapEntry<K,E>>(newEntry, buckets[b]);
    }



    public void delete(K key) {
        int b = hash(key);
        for (SLLNode<MapEntry<K,E>> pred = null, curr = buckets[b]; curr != null; pred = curr, curr = curr.succ) {
            if (key.equals(((MapEntry<K,E>) curr.element).key)) {
                if (pred == null)
                    buckets[b] = curr.succ;
                else
                    pred.succ = curr.succ;
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



public class RoutingHashJava {
    public static void main (String[] args) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int networkNum=Integer.parseInt(br.readLine());
        HashMap<String, List<String>>networks=new HashMap<>();
        for (int i = 0; i < networkNum; i++) {
            String ip=br.readLine();
            String network=br.readLine();
            List<String>ips=new ArrayList<>();
            String[] ipN=network.split(",");
            for (String s : ipN) {
                String[]words=s.split("\\.");
                String networkIp=words[0]+"."+words[1]+"."+words[2];
                ips.add(networkIp);
            }

            networks.put(ip,ips);
        }
        int logInNetworks=Integer.parseInt(br.readLine());
        for (int i = 0; i < logInNetworks; i++) {
            String ip=br.readLine();
            String network=br.readLine();

            String[]words=network.split("\\.");
            int mask=Integer.parseInt(words[3]);
            if(mask<0||mask>255){
                System.out.println("ne postoi");
                return;
            }
            String networkIp=words[0]+"."+words[1]+"."+words[2];
            boolean flag=true;
            if(networks.containsKey(ip)){
                List<String>examine=networks.get(ip);
                for (String s : examine) {
                    if(s.equals(networkIp)){
                        System.out.println("postoi");
                        flag=false;
                        break;
                    }
                }
                if(flag) {
                    System.out.println("ne postoi");
                }
            }else {
                System.out.println("ne postoi");
            }
        }
    }
}