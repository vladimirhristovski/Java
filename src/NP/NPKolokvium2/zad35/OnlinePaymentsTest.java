package NP.NPKolokvium2.zad35;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

class Payment {
    String name;
    int value;

    public Payment(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s %d\n", name, value);
    }
}

class User {
    String index;
    List<Payment> payments;
    int net;
    int total;
    int fee;

    public User(String index, String name, int price) {
        this.index = index;
        this.payments = new ArrayList<>();
        addPayment(name, price);
    }

    public void addPayment(String name, int price) {
//        this.payments.addFirst(new Payment(name, price));
        this.payments.add(new Payment(name, price));
        this.net = this.payments.stream().mapToInt(Payment::getValue).sum();
        if (this.net * 0.0114 < 3) {
            this.fee = 3;
        } else if (this.net * 0.0114 > 300) {
            this.fee = 300;
        } else {
            this.fee = (int) Math.round(this.net * 0.0114);
        }
        this.total = this.net + this.fee;
    }

    public String getIndex() {
        return index;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        final int[] i = {1};
        sb.append(String.format("Student: %s Net: %d Fee: %d Total: %d\n", index, net, fee, total));
        sb.append("Items:\n");
        payments.stream()
                .sorted(Comparator.comparing(Payment::getValue).thenComparing(Payment::getName).reversed())
                .forEach(payment -> {
                    sb.append(i[0]).append(". ").append(payment.toString());
                    i[0]++;
                });
        return sb.toString();
    }
}

class OnlinePayments {
    HashMap<String, User> users;

    public OnlinePayments() {
        this.users = new HashMap<>();
    }

    public void readItems(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines().forEach(line -> {
            String[] words = line.split(";");
            if (!users.containsKey(words[0])) {
                users.put(words[0], new User(words[0], words[1], Integer.parseInt(words[2])));
            } else {
                users.forEach((string, user) -> {
                    if (string.equals(words[0])) {
                        user.addPayment(words[1], Integer.parseInt(words[2]));
                    }
                });
            }
        });
    }

    void printStudentReport(String index, OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        if (users.containsKey(index)) {
            pw.print(users.get(index));
        } else {
            pw.println(String.format("Student %s not found!", index));
        }
        pw.flush();
    }
}

public class OnlinePaymentsTest {
    public static void main(String[] args) {
        OnlinePayments onlinePayments = new OnlinePayments();

        onlinePayments.readItems(System.in);

        IntStream.range(151020, 151025).mapToObj(String::valueOf).forEach(id -> onlinePayments.printStudentReport(id, System.out));
    }
}