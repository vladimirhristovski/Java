package APS.APSKolokvium1.lab2APS.zad1;

import java.util.Scanner;


@SuppressWarnings("unchecked")
class Array<E> {
    private E[] data; // declared to be an Object since it would be too
    // complicated with generics
    private int size;

    public Array(int capacity) {
        this.data = (E[]) new Object[capacity];
        this.size = 0;
    }

    public void insertLast(E o) {
        //check if there is enough capacity, and if not - resize
        if (size + 1 > data.length) this.resize();
        data[size++] = o;
    }

    public void insert(int position, E o) {
        // before all we check if position is within range
        if (position >= 0 && position <= size) {
            //check if there is enough capacity, and if not - resize
            if (size + 1 > data.length) this.resize();
            //copy the data, before doing the insertion
            for (int i = size; i > position; i--) {
                data[i] = data[i - 1];
            }
            data[position] = o;
            size++;
        } else {
            System.out.println("Ne mozhe da se vmetne element na taa pozicija");
        }
    }

    public void set(int position, E o) {
        if (position >= 0 && position < size) data[position] = o;
        else System.out.println("Ne moze da se vmetne element na dadenata pozicija");
    }

    public E get(int position) {
        if (position >= 0 && position < size) return data[position];
        else System.out.println("Ne e validna dadenata pozicija");
        return null;
    }

    public int find(E o) {
        for (int i = 0; i < size; i++) {
            if (o.equals(data[i])) return i;
        }
        return -1;
    }

    public int getSize() {
        return size;
    }

    public void delete(int position) {
        // before all we check if position is within range
        if (position >= 0 && position < size) {
            // first resize the storage array
            E[] newData = (E[]) new Object[size - 1];
            // copy the data prior to the delition
            System.arraycopy(data, 0, newData, 0, position);
            // move the data after the deletion
            if (size - (position + 1) >= 0)
                System.arraycopy(data, position + 1, newData, position + 1 - 1, size - (position + 1));
            // replace the storage with the new array
            data = newData;
            size--;
        }
    }

    public void resize() {
        // first resize the storage array
        E[] newData = (E[]) new Object[size * 2];
        // copy the data
        if (size >= 0) System.arraycopy(data, 0, newData, 0, size);
        // replace the storage with the new array
        this.data = newData;
    }

    @Override
    public String toString() {
        String ret = "";
        if (size > 0) {
            ret = "{";
            ret += data[0];
            for (int i = 1; i < size; i++) {
                ret += "," + data[i];
            }
            ret += "}";
            return ret;
        } else {
            ret = "Prazna niza!";
        }
        return ret;
    }

}

public class ArrayMeanWordLength {

    //TODO: implement function
    public static String wordClosestToAverageLength(Array<String> arr) {
        String element = null;
        int arrLenght = arr.getSize();
        double sum = 0.0;
        for (int i = 0; i < arrLenght; i++) {
            sum += arr.get(i).length();
        }
        double avgLenghtSum = sum / arrLenght;
        for (int i = 0; i < arrLenght; i++) {
            if (avgLenghtSum == arr.get(i).length()) {
                element = arr.get(i);
                return element;
            }
        }
        for (int i = 0; i < arrLenght; i++) {
            if (Math.ceil(avgLenghtSum) == arr.get(i).length()) {
                element = arr.get(i);
                return element;
            }
        }
        double nearestLenght = Math.abs(avgLenghtSum - arr.get(0).length());
        element = arr.get(0);
        for (int i = 0; i < arrLenght; i++) {
            if (nearestLenght > Math.abs(avgLenghtSum - arr.get(i).length())) {
                nearestLenght = Math.abs(avgLenghtSum - arr.get(i).length());
                element = arr.get(i);
            }
        }
        return element;
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        int N = input.nextInt();
        Array<String> arr = new Array<>(N);
        input.nextLine();

        for (int i = 0; i < N; i++) {
            arr.insertLast(input.nextLine());
        }

        System.out.println(wordClosestToAverageLength(arr));
    }
}