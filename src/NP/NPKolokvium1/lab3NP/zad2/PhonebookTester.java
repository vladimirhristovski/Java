package NP.NPKolokvium1.lab3NP.zad2;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

import static java.util.Arrays.sort;

class InvalidNameException extends Exception {
    public String name;

    InvalidNameException(String name) {
        this.name = name;
    }

}

class InvalidNumberException extends Exception {
}

class MaximumSizeExceddedException extends Exception {
}

class InvalidFormatException extends Exception {
}

class Contact {
    private final String name;
    private String[] phonenumber;

    public Contact(String name, String... phonenumber) throws InvalidNameException, InvalidNumberException, MaximumSizeExceddedException {
        validName(name);
        validNumber(phonenumber);
        this.name = name;
        this.phonenumber = phonenumber;
    }

    static Contact valueOf(String s) throws InvalidFormatException {
        String[] data = s.split("-");
        String[] numbers = data[1].split("_");

        try {
            return new Contact(data[0], numbers);
        } catch (Exception e) {
            throw new InvalidFormatException();
        }
    }

    void validName(String name) throws InvalidNameException {
        if (name.length() >= 4 && name.length() <= 10) {
            if (!name.matches("[a-zA-Z0-9]*")) throw new InvalidNameException(name);
        } else {
            throw new InvalidNameException(name);
        }
    }

    void validOneNumber(String string) throws InvalidNumberException {
        if (string.length() == 9) {
            if (!((string.startsWith("070")) || (string.startsWith("071")) || (string.startsWith("072")) || (string.startsWith("075")) || (string.startsWith("076")) || (string.startsWith("077")) || (string.startsWith("078")))) {
                throw new InvalidNumberException();
            }
            if (!string.matches("[0-9]*")) throw new InvalidNumberException();
        } else {
            throw new InvalidNumberException();
        }
    }

    void validNumber(String[] string) throws InvalidNumberException, MaximumSizeExceddedException {
        if (string != null) {
            if (string.length < 5) {
                for (String s : string) {
                    validOneNumber(s);
                }
            } else {
                throw new MaximumSizeExceddedException();
            }
        } else {
            throw new InvalidNumberException();
        }
    }

    public String getName() {
        return name;
    }

    public String[] getNumbers() {
        String[] phonenumbers = Arrays.copyOf(phonenumber, phonenumber.length);
        sort(phonenumbers);
        return phonenumbers;
    }

    void addNumber(String number) throws InvalidNumberException, MaximumSizeExceddedException {
        validOneNumber(number);
        if (phonenumber.length < 5) {
            String[] newPhonenumber = Arrays.copyOf(phonenumber, phonenumber.length + 1);
            newPhonenumber[newPhonenumber.length - 1] = number;
            phonenumber = newPhonenumber;
        } else {
            throw new MaximumSizeExceddedException();
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n").append(phonenumber.length).append("\n");
        String[] phoneNumbers = getNumbers();
        for (String phoneNumber : phoneNumbers) {
            sb.append(phoneNumber).append("\n");
        }
        return sb.toString();
    }
}

class PhoneBook {
    private Contact[] contacts;


    public PhoneBook() {
        this.contacts = new Contact[0];
    }

    static boolean saveAsTextFile(PhoneBook phonebook, String path) throws IOException {
        File file = new File(path);
        if (file.exists()) {
            try {
                PrintWriter fw = new PrintWriter(path);
                fw.write(phonebook.toString());
                fw.close();
            } catch (Exception e) {
                e.getStackTrace();
                return false;
            }
        } else if (file.createNewFile()) {
            try {
                PrintWriter fw = new PrintWriter(path);
                fw.write(phonebook.toString());
                fw.close();
            } catch (Exception e) {
                e.getStackTrace();
                return false;
            }
        }
        return true;
    }

    static PhoneBook loadFromTextFile(String path) throws IOException, InvalidFormatException {
        BufferedReader bf = new BufferedReader(new FileReader(path));
        String line;
        PhoneBook phoneBook = new PhoneBook();

        while ((line = bf.readLine()) != null) {
            StringBuilder sb = new StringBuilder(line + "-");
            int n = Integer.parseInt(bf.readLine());
            for (int i = 0; i < n; i++) {
                if (i == n - 1) {
                    sb.append((bf.read()));
                } else {
                    sb.append(bf.readLine()).append("-");
                }
            }
            Contact newC = Contact.valueOf((sb.toString()));
            try {
                phoneBook.addContact(newC);
            } catch (Exception ignore) {
                throw new InvalidFormatException();
            }
            bf.readLine();
        }
        return phoneBook;
    }

    void validName(Contact contact) throws InvalidNameException {
        for (Contact contact1 : contacts) {
            if (contact1.getName().equals(contact.getName())) {
                throw new InvalidNameException(contact.getName());
            }
        }
    }

    Contact[] getContactsForNumber(String number_prefix) {
        int n = 0;
        Contact[] listOfContacts = new Contact[getContacts().length];
        for (Contact contact : getContacts()) {
            for (String number : contact.getNumbers()) {
                if (number.startsWith(number_prefix)) {
                    listOfContacts[n++] = contact;
                }
            }
        }
        if (n == 0) return null;
        return Arrays.copyOf(listOfContacts, n);
    }


    void addContact(Contact contact) throws MaximumSizeExceddedException, InvalidNameException {
        validName(contact);
        if (contacts.length + 1 < 250) {
            Contact[] newContacts = Arrays.copyOf(contacts, contacts.length + 1);
            newContacts[newContacts.length - 1] = contact;
            contacts = newContacts;
        } else {
            throw new MaximumSizeExceddedException();
        }
    }

    Contact findName(String name) {
        for (Contact contact : contacts) {
            if (contact.getName().equals(name)) {
                return contact;
            }
        }
        return null;
    }

    Contact getContactForName(String name) {
        return findName(name);
    }

    int numberOfContacts() {
        return contacts.length;
    }

    Contact[] getContacts() {
        Contact[] updatedContacts = Arrays.copyOf(contacts, contacts.length);
        Arrays.sort(updatedContacts, Comparator.comparing(Contact::getName));
        return updatedContacts;
    }

    void deleteContact(Contact contact) {
        Contact[] newContacts = Arrays.copyOf(contacts, contacts.length);
        int j = 0;
        for (Contact value : contacts) {
            if (contact != value) {
                newContacts[j++] = value;
            }
        }
        contacts = Arrays.copyOf(newContacts, j);
    }

    boolean removeContact(String name) {
        Contact contact = findName(name);
        if (contact != null) {
            deleteContact(contact);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Contact contact : getContacts()) {
            sb.append(contact).append("\n");
        }
        return sb.toString();
    }
}

public class PhonebookTester {

    static String[] legit_prefixes = {"070", "071", "072", "075", "076", "077", "078"};
    static Random rnd = new Random();

    public static void main(String[] args) throws Exception {
        Scanner jin = new Scanner(System.in);
        String line = jin.nextLine();
        switch (line) {
            case "test_contact":
                testContact(jin);
                break;
            case "test_phonebook_exceptions":
                testPhonebookExceptions(jin);
                break;
            case "test_usage":
                testUsage(jin);
                break;
        }
    }

    private static void testFile(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
        String text_file = "phonebook.txt";
        PhoneBook.saveAsTextFile(phonebook, text_file);
        PhoneBook pb = PhoneBook.loadFromTextFile(text_file);
        if (!pb.equals(phonebook)) System.out.println("Your file saving and loading doesn't seem to work right");
        else System.out.println("Your file saving and loading works great. Good job!");
    }

    private static void testUsage(Scanner jin) throws Exception {
        PhoneBook phonebook = new PhoneBook();
        while (jin.hasNextLine()) {
            String command = jin.nextLine();
            switch (command) {
                case "add":
                    phonebook.addContact(new Contact(jin.nextLine(), jin.nextLine().split("\\s++")));
                    break;
                case "remove":
                    phonebook.removeContact(jin.nextLine());
                    break;
                case "print":
                    System.out.println(phonebook.numberOfContacts());
                    System.out.println(Arrays.toString(phonebook.getContacts()));
                    System.out.println(phonebook);
                    break;
                case "get_name":
                    System.out.println(phonebook.getContactForName(jin.nextLine()));
                    break;
                case "get_number":
                    System.out.println(Arrays.toString(phonebook.getContactsForNumber(jin.nextLine())));
                    break;
            }
        }
    }

    private static void testPhonebookExceptions(Scanner jin) {
        PhoneBook phonebook = new PhoneBook();
        boolean exception_thrown = false;
        try {
            while (jin.hasNextLine()) {
                phonebook.addContact(new Contact(jin.nextLine()));
            }
        } catch (InvalidNameException e) {
            System.out.println(e.name);
            exception_thrown = true;
        } catch (Exception e) {
        }
        if (!exception_thrown) System.out.println("Your addContact method doesn't throw InvalidNameException");
        /*
		exception_thrown = false;
		try {
		phonebook.addContact(new Contact(jin.nextLine()));
		} catch ( MaximumSizeExceddedException e ) {
			exception_thrown = true;
		}
		catch ( Exception e ) {}
		if ( ! exception_thrown ) System.out.println("Your addContact method doesn't throw MaximumSizeExcededException");
        */
    }

    private static void testContact(Scanner jin) throws Exception {
        boolean exception_thrown = true;
        String[] names_to_test = {"And\nrej", "asd", "AAAAAAAAAAAAAAAAAAAAAA", "Ð�Ð½Ð´Ñ€ÐµÑ˜A123213", "Andrej#", "Andrej<3"};
        for (String name : names_to_test) {
            try {
                new Contact(name);
                exception_thrown = false;
            } catch (InvalidNameException e) {
                exception_thrown = true;
            }
            if (!exception_thrown) System.out.println("Your Contact constructor doesn't throw an InvalidNameException");
        }
        String[] numbers_to_test = {"+071718028", "number", "078asdasdasd", "070asdqwe", "070a56798", "07045678a", "123456789", "074456798", "073456798", "079456798"};
        for (String number : numbers_to_test) {
            try {
                new Contact("Andrej", number);
                exception_thrown = false;
            } catch (InvalidNumberException e) {
                exception_thrown = true;
            }
            if (!exception_thrown)
                System.out.println("Your Contact constructor doesn't throw an InvalidNumberException");
        }
        String[] nums = new String[10];
        for (int i = 0; i < nums.length; ++i) nums[i] = getRandomLegitNumber();
        try {
            new Contact("Andrej", nums);
            exception_thrown = false;
        } catch (MaximumSizeExceddedException e) {
            exception_thrown = true;
        }
        if (!exception_thrown)
            System.out.println("Your Contact constructor doesn't throw a MaximumSizeExceddedException");
        Random rnd = new Random(5);
        Contact contact = new Contact("Andrej", getRandomLegitNumber(rnd), getRandomLegitNumber(rnd), getRandomLegitNumber(rnd));
        System.out.println(contact.getName());
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
        contact.addNumber(getRandomLegitNumber(rnd));
        System.out.println(Arrays.toString(contact.getNumbers()));
        System.out.println(contact);
    }

    private static String getRandomLegitNumber() {
        return getRandomLegitNumber(rnd);
    }

    private static String getRandomLegitNumber(Random rnd) {
        StringBuilder sb = new StringBuilder(legit_prefixes[rnd.nextInt(legit_prefixes.length)]);
        for (int i = 3; i < 9; ++i)
            sb.append(rnd.nextInt(10));
        return sb.toString();
    }


}