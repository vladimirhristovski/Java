package APS.APSKolokvium1.lab6APS.zad3;

import java.util.NoSuchElementException;
import java.util.Scanner;

interface Queue<E> {

    // Elementi na redicata se objekti od proizvolen tip.

    // Metodi za pristap:

    boolean isEmpty();
    // Vrakja true ako i samo ako redicata e prazena.

    int size();
    // Ja vrakja dolzinata na redicata.

    E peek();
    // Go vrakja elementot na vrvot t.e. pocetokot od redicata.

    // Metodi za transformacija:

    void clear();
    // Ja prazni redicata.

    void enqueue(E x);
    // Go dodava x na kraj od redicata.

    E dequeue();
    // Go otstranuva i vrakja pochetniot element na redicata.
}

class ArrayQueue<E> implements Queue<E> {

    // Redicata e pretstavena na sledniot nacin:
    // length go sodrzi brojot na elementi.
    // Ako length > 0, togash elementite na redicata se zachuvani vo elems[front...rear-1]
    // Ako rear > front, togash vo  elems[front...maxlength-1] i elems[0...rear-1]
    E[] elems;
    int length, front, rear;

    // Konstruktor ...

    @SuppressWarnings("unchecked")
    public ArrayQueue(int maxlength) {
        elems = (E[]) new Object[maxlength];
        clear();
    }

    public boolean isEmpty() {
        // Vrakja true ako i samo ako redicata e prazena.
        return (length == 0);
    }

    public int size() {
        // Ja vrakja dolzinata na redicata.
        return length;
    }

    public E peek() {
        // Go vrakja elementot na vrvot t.e. pocetokot od redicata.
        if (length > 0) return elems[front];
        else throw new NoSuchElementException();
    }

    public void clear() {
        // Ja prazni redicata.
        length = 0;
        front = rear = 0;  // arbitrary
    }

    public void enqueue(E x) {
        // Go dodava x na kraj od redicata.
        elems[rear++] = x;
        if (rear == elems.length) rear = 0;
        length++;
    }

    public E dequeue() {
        // Go otstranuva i vrakja pochetniot element na redicata.
        if (length > 0) {
            E frontmost = elems[front];
            elems[front++] = null;
            if (front == elems.length) front = 0;
            length--;
            return frontmost;
        } else throw new NoSuchElementException();
    }
}

class Gragjanin {
    private final String name;
    private final int perosnalID;
    private final int passport;
    private final int driversLicence;

    public Gragjanin(String name, int perosnalID, int passport, int driversLicence) {
        this.name = name;
        this.perosnalID = perosnalID;
        this.passport = passport;
        this.driversLicence = driversLicence;
    }

    public int getPerosnalID() {
        return perosnalID;
    }

    public int getPassport() {
        return passport;
    }

    public int getDriversLicence() {
        return driversLicence;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }
}

public class MVR {

    public static void main(String[] args) {

        Scanner br = new Scanner(System.in);

        int N = Integer.parseInt(br.nextLine());

        Queue<Gragjanin> licniKarti = new ArrayQueue<>(N);
        Queue<Gragjanin> pasosi = new ArrayQueue<>(N);
        Queue<Gragjanin> vozacki = new ArrayQueue<>(N);

        for (int i = 1; i <= N; i++) {
            String imePrezime = br.nextLine();
            int lKarta = Integer.parseInt(br.nextLine());
            int pasos = Integer.parseInt(br.nextLine());
            int vozacka = Integer.parseInt(br.nextLine());
            Gragjanin covek = new Gragjanin(imePrezime, lKarta, pasos, vozacka);
            if (lKarta == 1) {
                licniKarti.enqueue(covek);
            } else if (pasos == 1) {
                pasosi.enqueue(covek);
            } else if (vozacka == 1) {
                vozacki.enqueue(covek);
            }
        }
        while (!licniKarti.isEmpty()) {
            Gragjanin next = licniKarti.peek();
            if (next.getPassport() == 1) {
                pasosi.enqueue(licniKarti.dequeue());
            } else if (next.getDriversLicence() == 1) {
                vozacki.enqueue(licniKarti.dequeue());
            } else {
                System.out.println(licniKarti.dequeue());
            }
        }

        while (!pasosi.isEmpty()) {
            Gragjanin next = pasosi.peek();
            if (next.getDriversLicence() == 1) {
                vozacki.enqueue(pasosi.dequeue());
            } else {
                System.out.println(pasosi.dequeue());
            }
        }

        while (!vozacki.isEmpty()) {
            System.out.println(vozacki.dequeue());
        }
    }
}