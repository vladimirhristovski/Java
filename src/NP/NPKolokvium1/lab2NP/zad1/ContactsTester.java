package NP.NPKolokvium1.lab2NP.zad1;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

enum Operator {VIP, ONE, TMOBILE}

abstract class Contact {
    private final int year;
    private final int month;
    private final int day;

    public Contact(String date) {
        this.year = Integer.parseInt(date.split("-")[0]);
        this.month = Integer.parseInt(date.split("-")[1]);
        this.day = Integer.parseInt(date.split("-")[2]);
    }

    public abstract String getType();

    private long getDays() {
        return year * 365L + month * 30L + day;
    }

    public boolean isNewerThan(Contact c) {
        return getDays() > c.getDays();
    }
}

class EmailContact extends Contact {
    private final String email;

    public EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getType() {
        return "Email";
    }

    @Override
    public String toString() {
        return "\"" + email + "\"";
    }
}

class PhoneContact extends Contact {
    private final String phone;
    private final Operator operator;

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
        char op = phone.charAt(2);

        if (op == '0' || op == '1' || op == '2') {
            operator = Operator.TMOBILE;
        } else if (op == '5' || op == '6') {
            operator = Operator.ONE;
        } else {
            operator = Operator.VIP;
        }
    }

    public String getPhone() {

        return phone;
    }

    @Override
    public String getType() {
        return "Phone";
    }

    public Operator getOperator() {
        return operator;
    }

    @Override
    public String toString() {
        return "\"" + phone + "\"";
    }
}


class Student {
    private final String firstName;
    private final String lastName;
    private final String city;
    private final int age;
    private final long index;
    private Contact[] contacts;
    private int n;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        this.contacts = new Contact[0];
        this.n = 0;
    }

    public Contact[] getContacts() {
        return contacts;
    }

    public void addEmailContact(String date, String email) {
        contacts = Arrays.copyOf(contacts, n + 1);
        contacts[n++] = new EmailContact(date, email);
    }

    public void addPhoneContact(String date, String phone) {
        contacts = Arrays.copyOf(contacts, n + 1);
        contacts[n++] = new PhoneContact(date, phone);
    }

    public Contact[] getEmailContacts() {
        int m = 0;
        Contact[] emailContacts;
        for (Contact contact : contacts) {
            if (contact.getType().equals("Email")) {
                m++;
            }
        }
        emailContacts = new Contact[m];
        m = 0;
        for (Contact contact : contacts) {
            if (contact.getType().equals("Email")) {
                emailContacts[m] = contact;
                m++;
            }
        }
        return emailContacts;
    }

    public Contact[] getPhoneContacts() {
        int m = 0;
        Contact[] phoneContacts;
        for (Contact contact : contacts) {
            if (contact.getType().equals("Phone")) {
                m++;
            }
        }
        phoneContacts = new Contact[m];
        m = 0;
        for (Contact contact : contacts) {
            if (contact.getType().equals("Phone")) {
                phoneContacts[m] = contact;
                m++;
            }
        }
        return phoneContacts;
    }

    public String getCity() {
        return city;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public long getIndex() {
        return index;
    }

    public Contact getLatestContact() {
        Contact latestContact = contacts[0];
        for (Contact contact : contacts) {
            if (contact.isNewerThan(latestContact)) {
                latestContact = contact;
            }
        }
        return latestContact;
    }

    @Override
    public String toString() {
        return "{\"ime\":\"" + firstName + "\", \"prezime\":\"" + lastName + "\", \"vozrast\":" + age + ", \"grad\":\"" + city + "\", \"indeks\":" + index + ", \"telefonskiKontakti\":" + Arrays.toString(getPhoneContacts()) + ", \"emailKontakti\":" + Arrays.toString(getEmailContacts()) + "}";
    }
}

class Faculty {
    private final String name;
    private final Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        int counter = 0;
        for (Student student : students) {
            if (student.getCity().equals(cityName)) {
                counter++;
            }
        }
        return counter;
    }

    public Student getStudent(long index) {
        for (Student student : students) {
            if (student.getIndex() == index) {
                return student;
            }
        }
        return null;
    }

    public double getAverageNumberOfContacts() {
        double averageContactNumber = 0.0;
        for (Student student : students) {
            averageContactNumber += student.getContacts().length;
        }
        return averageContactNumber / students.length;
    }

    public Student getStudentWithMostContacts() {
        Student studentWithMostCOntacts = students[0];
        for (Student student : students) {
            if (studentWithMostCOntacts.getContacts().length < student.getContacts().length) {
                studentWithMostCOntacts = student;
            }
            if (studentWithMostCOntacts.getContacts().length == student.getContacts().length) {
                if (studentWithMostCOntacts.getIndex() < student.getIndex()) {
                    studentWithMostCOntacts = student;
                }
            }
        }
        return studentWithMostCOntacts;
    }

    @Override
    public String toString() {
        return "{\"fakultet\":\"" + name + "\", \"studenti\":" + Arrays.toString(students) + "}";
    }
}

public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0)) rindex = index;

                        Student student = new Student(firstName, lastName, city, age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0)) rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: " + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": " + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex).getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact).getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact).getPhone() + " (" + ((PhoneContact) latestContact).getOperator().toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0 && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out.println(faculty.getStudent(rindex).getEmailContacts().length + " " + faculty.getStudent(rindex).getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue % faculty.getStudent(rindex).getPhoneContacts().length;

                        System.out.println(faculty.getStudent(rindex).getEmailContacts()[posEmail].isNewerThan(faculty.getStudent(rindex).getPhoneContacts()[posPhone]));
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: " + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}