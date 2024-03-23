package NP.NPKolokvium2.zad21;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

class DuplicateNumberException extends Exception {
    public DuplicateNumberException(String message) {
        super(message);
    }
}

class Contact {
    String name;
    String number;

    public Contact(String name, String number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public String getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return String.format("%s %s", name, number);
    }
}

class PhoneBook {
    Map<String, TreeSet<Contact>> contacts;

    public PhoneBook() {
        this.contacts = new TreeMap<>();
    }

    public boolean check(Contact c) {
        AtomicBoolean checked = new AtomicBoolean(true);
        contacts.forEach((string, contact) -> {
            if (c.getName().equals(string)) {
                contact.forEach(cont -> {
                    if (cont.getNumber().equals(c.getNumber())) {
                        checked.set(false);
                    }
                });
            }
        });
        return checked.get();
    }

    public void addContact(String name, String number) throws DuplicateNumberException {
        Contact contact = new Contact(name, number);
        if (check(contact)) {
            contacts.putIfAbsent(name, new TreeSet<>(Comparator.comparing(Contact::getName).thenComparing(Contact::getNumber)));
            contacts.get(name).add(contact);
        } else {
            throw new DuplicateNumberException(String.format("Duplicate number: %s", number));
        }
    }


    public void contactsByNumber(String number) {
        AtomicBoolean checked = new AtomicBoolean(true);
        contacts.forEach((string, contact) -> {
            contact.forEach(cont -> {
                if (cont.getNumber().contains(number)) {
                    System.out.println(cont);
                    checked.set(false);
                }
            });
        });
        if (checked.get()) {
            System.out.println("NOT FOUND");
        }
    }

    public void contactsByName(String name) {
        if (contacts.containsKey(name)) {
            contacts.get(name).forEach(contact -> System.out.println(contact));
        } else {
            System.out.println("NOT FOUND");
        }
    }
}

public class PhoneBookTest {

    public static void main(String[] args) {
        PhoneBook phoneBook = new PhoneBook();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            try {
                phoneBook.addContact(parts[0], parts[1]);
            } catch (DuplicateNumberException e) {
                System.out.println(e.getMessage());
            }
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            System.out.println(line);
            String[] parts = line.split(":");
            if (parts[0].equals("NUM")) {
                phoneBook.contactsByNumber(parts[1]);
            } else {
                phoneBook.contactsByName(parts[1]);
            }
        }
    }
}