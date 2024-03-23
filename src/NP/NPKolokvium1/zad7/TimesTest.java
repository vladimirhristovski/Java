package NP.NPKolokvium1.zad7;

import java.io.*;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
class TimeTable{
    List<LocalTime>times;
    public TimeTable() {
        times=new ArrayList<>();
    }

    void readTimes(InputStream inputStream) throws UnsupportedFormatException, InvalidTimeException {
        Scanner scanner = new Scanner(inputStream);
        String currentTime;
        while(scanner.hasNext()){
            currentTime=scanner.next();
            currentTime=currentTime.replace(".",":");
            if(!currentTime.contains(":")){
                throw new UnsupportedFormatException(String.format("%s",currentTime));
            }
            String[]words=currentTime.split(":");
            if(Integer.parseInt(words[0])>23||Integer.parseInt(words[0])<0){
                throw new InvalidTimeException(String.format("%s",currentTime));
            }
            if(Integer.parseInt(words[1])>59||Integer.parseInt(words[1])<0){
                throw new InvalidTimeException(String.format("%s",currentTime));
            }
            times.add(LocalTime.parse(currentTime, DateTimeFormatter.ofPattern("H:mm")));

        }
    }

    void print24(OutputStream outputStream,List<LocalTime>times){
        PrintWriter pw=new PrintWriter(outputStream);
        times.stream()
                .sorted(Comparator.comparing(LocalTime::getHour).thenComparing(LocalTime::getMinute))
                .forEach(time->pw.println(String.format("%5s",time.format(DateTimeFormatter.ofPattern("H:mm")))));
        pw.flush();
    }
    void printAMPM(OutputStream outputStream,List<LocalTime>times){
        PrintWriter pw=new PrintWriter(outputStream);
        times.stream()
                .sorted(Comparator.comparing(LocalTime::getHour).thenComparing(LocalTime::getMinute))
                .forEach(time->pw.println(String.format("%8s",time.format(DateTimeFormatter.ofPattern("h:mm a")))));
        pw.flush();
    }
    void writeTimes(OutputStream outputStream, TimeFormat format){
        if(format==TimeFormat.FORMAT_24){
            print24(outputStream,times);
        }
        if(format==TimeFormat.FORMAT_AMPM){
            printAMPM(outputStream,times);
        }
    }
}
class UnsupportedFormatException extends Exception{
    public UnsupportedFormatException(String message) {
        super(message);
    }
}
class InvalidTimeException extends Exception{
    public InvalidTimeException(String message) {
        super(message);
    }
}

public class TimesTest {

    public static void main(String[] args) {
        TimeTable timeTable = new TimeTable();
        try {
            timeTable.readTimes(System.in);
        } catch (UnsupportedFormatException e) {
            System.out.println("UnsupportedFormatException: " + e.getMessage());
        } catch (InvalidTimeException e) {
            System.out.println("InvalidTimeException: " + e.getMessage());
        }
        System.out.println("24 HOUR FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_24);
        System.out.println("AM/PM FORMAT");
        timeTable.writeTimes(System.out, TimeFormat.FORMAT_AMPM);
    }

}

enum TimeFormat {
    FORMAT_24, FORMAT_AMPM
}