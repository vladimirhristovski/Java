package NP.NPKolokvium2.zad27;

import java.util.*;
import java.util.stream.Collectors;

class Name {
    String name;
    int appearances;

    public Name(String name) {
        this.name = name;
        this.appearances = 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAppearances() {
        return appearances;
    }

    public void setAppearances(int appearances) {
        this.appearances = appearances;
    }

    public int getUniqueLetters() {
        TreeSet<String> checked = new TreeSet<>(Arrays.asList(name.toLowerCase().split("")));
        return checked.size();
    }

    @Override
    public String toString() {
        return String.format("%s (%d) %d", name, appearances, getUniqueLetters());
    }
}

class Names {
    TreeSet<Name> names;

    public Names() {
        this.names = new TreeSet<>(Comparator.comparing(Name::getName));
    }

    public void addName(String name) {
        Name check = new Name(name);

        if (!names.contains(check)) {
            names.add(check);
        } else {
            names.forEach(n -> {
                if (n.getName().equals(name)) {
                    n.setAppearances(n.getAppearances() + 1);
                }
            });
        }
    }

    public void printN(int n) {
        names.forEach(name -> {
            if (name.getAppearances() >= n) {
                System.out.println(name);
            }
        });
    }

    public int getIdx(int i, int size) {
        if (i % size == 0) {
            return 0;
        }
        int num = Math.abs(i - size);
        if (num > size) {
            return getIdx(num, size);
        } else {
            return num;
        }
    }

    public String findName(int len, int index) {
        List<Name> check = names.stream()
                .filter(name -> name.getName().length() < len)
                .sorted(Comparator.comparing(Name::getName))
                .collect(Collectors.toList());
        int num = getIdx(index, check.size());
        return check.get(num).getName();
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}