package NP.NPKolokvium2.zad1;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

class Participant {
    String city;
    String code;
    String name;
    int age;

    public Participant(String city, String code, String name, int age) {
        this.city = city;
        this.code = code;
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return String.format("%s %s %d", code, name, age);
    }

    public String getCity() {
        return city;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

class Audition {
    Map<String, TreeSet<Participant>> participants = new HashMap<>();

    public boolean check(Participant checkParticipant) {
        AtomicBoolean checked = new AtomicBoolean(true);
        participants.forEach((string, participant) -> {
            if (checkParticipant.getCity().equals(string)) {
                participant.forEach(p -> {
                    if (p.getCode().equals(checkParticipant.getCode())) {
                        checked.set(false);
                    }
                });
            }
        });
        return checked.get();
    }

    public void addParticpant(String city, String code, String name, int age) {
        Participant participant = new Participant(city, code, name, age);
        if (check(participant)) {
            participants.putIfAbsent(city, new TreeSet<>(Comparator.comparing(Participant::getName).thenComparing(Participant::getAge).thenComparing(Participant::getCode)));
            participants.get(city).add(participant);
        }
    }

    public void listByCity(String city) {
        participants.get(city).forEach(participant -> System.out.println(participant));
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}