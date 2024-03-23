package NP.NPKolokvium2.zad23;

import java.util.*;
import java.util.stream.Collectors;

class Student {
    String index;
    List<Integer> points;
    String sign;
    double avg;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
        if (points.size() <= 7) {
            this.sign = "NO";
        } else {
            this.sign = "YES";
        }
        this.avg = points.stream().mapToInt(i -> i).sum() / 10.0;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getSign() {
        return sign;
    }

    public double getAvg() {
        return avg;
    }

    public int getYearOfStudies() {
        return 20 - Integer.parseInt(index.substring(0, 2));
    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f", index, sign, avg);
    }
}

class LabExercises {
    List<Student> students;

    public LabExercises() {
        this.students = new ArrayList<>();
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void printByAveragePoints(boolean ascending, int n) {
        if (ascending) {
            students.stream()
                    .sorted(Comparator.comparing(Student::getAvg).thenComparing(Student::getIndex))
                    .limit(n)
                    .forEach(student -> System.out.println(student));
        } else {
            students.stream()
                    .sorted(Comparator.comparing(Student::getAvg).thenComparing(Student::getIndex).reversed())
                    .limit(n)
                    .forEach(student -> System.out.println(student));
        }
    }

    public List<Student> failedStudents() {
        return students.stream()
                .filter(student -> student.getSign().equals("NO"))
                .sorted(Comparator.comparing(Student::getIndex).thenComparing(Student::getAvg))
                .collect(Collectors.toList());
    }

    public Map<Integer, Double> getStatisticsByYear() {
        return students.stream()
                .filter(student -> student.getSign().equals("YES"))
                .collect(Collectors.groupingBy(
                        Student::getYearOfStudies,
                        Collectors.averagingDouble(Student::getAvg)
                ));

    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}