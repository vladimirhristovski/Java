package NP.NPKolokvium1.zad21;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class Time{
    int min;
    int sec;
    int msec;
    Time(String input){
        String[]words=input.split(":");
        this.min=Integer.parseInt(words[0]);
        this.sec=Integer.parseInt(words[1]);
        this.msec=Integer.parseInt(words[2]);
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public int getMsec() {
        return msec;
    }
}

class Driver{
    String name;
    List<Time>times;
    int min;
    int sec;
    int msec;
    Driver(String input){
        String[] words=input.split("\\s+");
        this.name=words[0];
        times=Arrays.stream(words)
                .skip(1)
                .map(word->new Time(word))
                .sorted(Comparator.comparing(Time::getMin).thenComparing(Time::getSec).thenComparing(Time::getMsec))
                .collect(Collectors.toList());
        this.min=times.get(0).getMin();
        this.sec=times.get(0).getSec();
        this.msec=times.get(0).getMsec();
    }

    String bestTime(){
        return String.format("%d:%02d:%03d",min,sec,msec);
    }
    @Override
    public String toString() {
        return String.format("%-11s %-10s",name,bestTime());
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public int getMsec() {
        return msec;
    }
}

class F1Race {

    List<Driver>drivers;
    public F1Race() {
    }
    void readResults(InputStream is){
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        drivers=br.lines()
                .map(line->new Driver(line))
                .sorted(Comparator.comparing(Driver::getMin).thenComparing(Driver::getSec).thenComparing(Driver::getMsec))
                .collect(Collectors.toList());
    }
    void printSorted(OutputStream os){
        int i=1;
        PrintWriter pw=new PrintWriter(os);
        for (Driver driver : drivers) {
            pw.format("%d. %s\n",i++,driver);
        }
        pw.flush();
    }
}
//package NP.NPKolokvium1.zad21;
//
//import java.io.*;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class F1Test {
//
//    public static void main(String[] args) {
//        F1Race f1Race = new F1Race();
//        f1Race.readResults(System.in);
//        f1Race.printSorted(System.out);
//    }
//
//}
//class Driver{
//    String name;
//    List<LocalTime>times;
//
//    String changeTime(String string){
//        String[]time=string.split(":");
//        return time[0]+":"+time[1]+"."+time[2];
//    }
//    Driver(String input){
//        String[] words=input.split("\\s+");
//        this.name=words[0];
//        for (int i = 1; i < words.length; i++) {
//            words[i]=changeTime(words[i]);
//        }
//        times=Arrays.stream(words)
//                .skip(1)
//                .map(word->LocalTime.parse(word, DateTimeFormatter.ofPattern("m:ss.SSS")))
//                .sorted(Comparator.comparing(LocalTime::getMinute).thenComparing(LocalTime::getSecond).thenComparing(LocalTime::getNano))
//                .collect(Collectors.toList());
//    }
//
//    String bestTime(){
//        return String.format("%d:%02d:%03d",times.get(0).getMinute(),times.get(0).getSecond(),times.get(0).getNano());
//    }
//    @Override
//    public String toString() {
//        return String.format("%-11s %-10s",name,bestTime());
//    }
//
//    public int getMin() {
//        return times.get(0).getMinute();
//    }
//
//    public int getSec() {
//        return times.get(0).getSecond();
//    }
//
//    public int getMsec() {
//        return times.get(0).getNano();
//    }
//}
//
//class F1Race {
//
//    List<Driver>drivers;
//    public F1Race() {
//    }
//    void readResults(InputStream is){
//        BufferedReader br=new BufferedReader(new InputStreamReader(is));
//        drivers=br.lines()
//                .map(Driver::new)
//                .sorted(Comparator.comparing(Driver::getMin).thenComparing(Driver::getSec).thenComparing(Driver::getMsec))
//                .collect(Collectors.toList());
//    }
//    void printSorted(OutputStream os){
//        int i=1;
//        PrintWriter pw=new PrintWriter(os);
//        for (Driver driver : drivers) {
//            pw.format("%d. %s\n",i++,driver);
//        }
//        pw.flush();
//    }
//}