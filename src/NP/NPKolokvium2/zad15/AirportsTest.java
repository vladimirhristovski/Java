package NP.NPKolokvium2.zad15;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

class Airport {
    String name;
    String country;
    String code;
    int passengers;

    public Airport(String name, String country, String code, int passengers) {
        this.name = name;
        this.country = country;
        this.code = code;
        this.passengers = passengers;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)\n%s\n%d", name, code, country, passengers);
    }
}

class Flight {
    String from;
    String to;
    int time;
    int duration;

    public Flight(String from, String to, int time, int duration) {
        this.from = from;
        this.to = to;
        this.time = time;
        this.duration = duration;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getTime() {
        return time;
    }

    public String getDuration() {
        int h = duration / 60;
        int min = duration % 60;
        if (min < 10) {
            return String.format("%dh0%dm", h, min);
        } else {
            return String.format("%dh%dm", h, min);
        }
    }

    public String getTakeOffTime() {
        int h = time / 60;
        int min = time % 60;
        int days = 0;
        if (h >= 24) {
            h -= 24;
            days = 1;
        }
        if (h < 10 && days > 0 && min < 10) {
            return String.format("0%d:0%d +%dd", h, min, days);
        } else if (h >= 10 && days > 0 && min >= 10) {
            return String.format("%d:%d +%dd", h, min, days);
        } else if (h >= 10 && days > 0 && min < 10) {
            return String.format("%d:0%d +%dd", h, min, days);
        } else if (h < 10 && days > 0 && min >= 10) {
            return String.format("0%d:%d +%dd", h, min, days);
        } else if (h < 10 && days == 0 && min < 10) {
            return String.format("0%d:0%d", h, min);
        } else if (h >= 10 && days == 0 && min >= 10) {
            return String.format("%d:%d", h, min);
        } else if (h >= 10 && days == 0 && min < 10) {
            return String.format("%d:0%d", h, min);
        } else if (h < 10 && days == 0 && min >= 10) {
            return String.format("0%d:%d", h, min);
        } else return null;
    }

    public String getLandingTime() {
        int h = (time + duration) / 60;
        int min = (time + duration) % 60;
        int days = 0;
        if (h >= 24) {
            h -= 24;
            days = 1;
        }
        if (h < 10 && days > 0 && min < 10) {
            return String.format("0%d:0%d +%dd", h, min, days);
        } else if (h >= 10 && days > 0 && min >= 10) {
            return String.format("%d:%d +%dd", h, min, days);
        } else if (h >= 10 && days > 0 && min < 10) {
            return String.format("%d:0%d +%dd", h, min, days);
        } else if (h < 10 && days > 0 && min >= 10) {
            return String.format("0%d:%d +%dd", h, min, days);
        } else if (h < 10 && days == 0 && min < 10) {
            return String.format("0%d:0%d", h, min);
        } else if (h >= 10 && days == 0 && min >= 10) {
            return String.format("%d:%d", h, min);
        } else if (h >= 10 && days == 0 && min < 10) {
            return String.format("%d:0%d", h, min);
        } else if (h < 10 && days == 0 && min >= 10) {
            return String.format("0%d:%d", h, min);
        } else return null;
    }

    @Override
    public String toString() {
        return String.format("%s-%s %s-%s %s", from, to, getTakeOffTime(), getLandingTime(), getDuration());
    }
}

class Airports {
    HashMap<String, Airport> airports;
    TreeSet<Flight> flights;

    public Airports() {
        this.airports = new HashMap<>();
        this.flights = new TreeSet<>(Comparator.comparing(Flight::getTime).thenComparing(Flight::getFrom).thenComparing(Flight::getTo));
    }


    public void addAirport(String name, String country, String code, int passengers) {
        airports.put(code, new Airport(name, country, code, passengers));
    }

    public void addFlights(String from, String to, int time, int duration) {
        flights.add(new Flight(from, to, time, duration));
    }


    public void showFlightsFromAirport(String code) {
        TreeSet<Flight> check = flights.stream()
                .filter(flight -> flight.getFrom().equals(code))
                .collect(Collectors.toCollection(
                        () -> new TreeSet<>(Comparator.comparing(Flight::getTo).thenComparing(Flight::getTime))
                ));
        System.out.println(airports.get(code).toString());
        AtomicInteger i = new AtomicInteger(1);
        check.forEach(flight -> {
            String sb = String.format("%d. ", i.get()) + flight.toString();
            i.getAndIncrement();
            System.out.println(sb);
        });
    }

    public void showDirectFlightsFromTo(String from, String to) {
        List<Flight> check = flights.stream()
                .filter(flight -> flight.getFrom().equals(from))
                .filter(flight -> flight.getTo().equals(to))
                .collect(Collectors.toList());
        if (check.isEmpty()) {
            System.out.printf("No flights from %s to %s%n", from, to);
        } else {
            check.forEach(System.out::println);
        }
    }

    public void showDirectFlightsTo(String to) {
        flights.stream()
                .filter(flight -> flight.getTo().equals(to))
                .forEach(System.out::println);

    }
}

public class AirportsTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Airports airports = new Airports();
        int n = scanner.nextInt();
        scanner.nextLine();
        String[] codes = new String[n];
        for (int i = 0; i < n; ++i) {
            String al = scanner.nextLine();
            String[] parts = al.split(";");
            airports.addAirport(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
            codes[i] = parts[2];
        }
        int nn = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < nn; ++i) {
            String fl = scanner.nextLine();
            String[] parts = fl.split(";");
            airports.addFlights(parts[0], parts[1], Integer.parseInt(parts[2]), Integer.parseInt(parts[3]));
        }
        int f = scanner.nextInt();
        int t = scanner.nextInt();
        String from = codes[f];
        String to = codes[t];
        System.out.printf("===== FLIGHTS FROM %S =====\n", from);
        airports.showFlightsFromAirport(from);
        System.out.printf("===== DIRECT FLIGHTS FROM %S TO %S =====\n", from, to);
        airports.showDirectFlightsFromTo(from, to);
        t += 5;
        t = t % n;
        to = codes[t];
        System.out.printf("===== DIRECT FLIGHTS TO %S =====\n", to);
        airports.showDirectFlightsTo(to);
    }
}