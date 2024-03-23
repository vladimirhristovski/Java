package NP.NPKolokvium2.zad22;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.stream.Collectors;

class WrongDateException extends Exception {
    public WrongDateException(String message) {
        super(message);
    }
}

class Event {
    String name;
    String location;
    LocalDateTime date;

    public Event(String name, String location, LocalDateTime date) {
        this.name = name;
        this.location = location;
        this.date = date;
    }

    @Override
    public String toString() {
        return String.format("%s at %s, %s", date.format(DateTimeFormatter.ofPattern("dd MMM, yyy HH:mm")), location, name);
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

class EventCalendar {
    int year;
    TreeSet<Event> events;


    public EventCalendar(int year) {
        this.year = year;
        this.events = new TreeSet<>(Comparator.comparing(Event::getDate).thenComparing(Event::getName));
    }

    public void addEvent(String name, String location, LocalDateTime date) throws WrongDateException {
        int compareYear = date.getYear();
        if (compareYear == year) {
            events.add(new Event(name, location, date));
        } else {
            throw new WrongDateException(String.format("Wrong date: %s", date.format(DateTimeFormatter.ofPattern("eee MMM dd HH:mm:ss 'UTC' yyyy"))));
        }
    }

    public void listEvents(LocalDateTime date) {
        List<Event> check = events.stream()
                .filter(event -> (event.getDate().getDayOfMonth() == date.getDayOfMonth() && event.getDate().getMonth() == date.getMonth()))
                .collect(Collectors.toList());
        if (check.isEmpty()) {
            System.out.println("No events on this day!");
        } else {
            check.forEach(System.out::println);
        }
    }

    public void listByMonth() {
        for (int i = 1; i <= 12; i++) {
            int val = i;
            List<Event> check = events.stream()
                    .filter(event -> (event.getDate().getMonthValue() == val))
                    .collect(Collectors.toList());
            System.out.printf("%d : %d%n", val, check.size());
        }
    }
}

public class EventCalendarTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        int year = scanner.nextInt();
        scanner.nextLine();
        EventCalendar eventCalendar = new EventCalendar(year);
//        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            String name = parts[0];
            String location = parts[1];
            LocalDateTime date = LocalDateTime.parse(parts[2], dtf);
            try {
                eventCalendar.addEvent(name, location, date);
            } catch (WrongDateException e) {
                System.out.println(e.getMessage());
            }
        }
        String nl = scanner.nextLine();
        LocalDateTime date = LocalDateTime.parse(nl, dtf);
//        LocalDateTime date = df.parse(scanner.nextLine());
        eventCalendar.listEvents(date);
        eventCalendar.listByMonth();
    }
}