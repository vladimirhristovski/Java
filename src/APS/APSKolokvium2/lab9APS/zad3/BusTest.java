package APS.APSKolokvium2.lab9APS.zad3;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Ride{
    LocalTime start;
    LocalTime end;
    public Ride(String input){
        String[]words=input.split("\\s+");
        this.start=LocalTime.parse(words[0],DateTimeFormatter.ofPattern("HH:mm"));
        this.end=LocalTime.parse(words[1],DateTimeFormatter.ofPattern("HH:mm"));
    }

    public LocalTime getStart() {
        return start;
    }

    public LocalTime getEnd() {
        return end;
    }
}

class Bus{
    List<Ride> rides;
    int capacity;
    int riders;

    public Bus() {
        this.rides=new ArrayList<>();
    }
    public void add(Ride ride){
        this.rides.add(ride);
        this.rides=this.rides.stream().sorted(Comparator.comparing(Ride::getStart)).collect(Collectors.toList());
    }

    public boolean check(){
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 60; j++) {
                LocalTime endd = LocalTime.parse(LocalTime.of(i,j).format(DateTimeFormatter.ofPattern("HH:mm")));
                List<Ride>test=rides.stream()
                        .filter(ride->ride.getStart().isBefore(endd))
                        .filter(ride->ride.getEnd().isAfter(endd))
                        .collect(Collectors.toList());
                if(test.size()>capacity){
                    return false;
                }
            }
        }
        return true;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setRiders(int riders) {
        this.riders = riders;
    }

}

public class BusTest {
    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        Bus bus=new Bus();
        int capacity= scanner.nextInt();
        int riders = scanner.nextInt();
        bus.setCapacity(capacity);
        bus.setRiders(riders);
        scanner.nextLine();
        for (int i = 0; i < riders; i++) {
            String time= scanner.nextLine();
            bus.add(new Ride(time));
        }
        System.out.println(bus.check());;
    }
}