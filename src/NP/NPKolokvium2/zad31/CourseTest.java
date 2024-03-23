package NP.NPKolokvium2.zad31;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Student {
    String index;
    String name;
    int pointsFirstMid;
    int pointsSecondMid;
    int pointsLabs;
    int grade;
    boolean passed;

    public Student(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPointsFirstMid(int pointsFirstMid) {
        this.pointsFirstMid = pointsFirstMid;
    }

    public void setPointsSecondMid(int pointsSecondMid) {
        this.pointsSecondMid = pointsSecondMid;
    }

    public void setPointsLabs(int pointsLabs) {
        this.pointsLabs = pointsLabs;
    }

    public double getPointsSummary() {
        return pointsFirstMid * 0.45 + pointsSecondMid * 0.45 + pointsLabs;
    }

    public int getGrade() {
        double check = getPointsSummary();
        if (check >= 50.0 && check <= 59.9) {
            this.grade = 6;
        } else if (check >= 60.0 && check <= 69.9) {
            this.grade = 7;
        } else if (check >= 70.0 && check <= 79.9) {
            this.grade = 8;
        } else if (check >= 80.0 && check <= 89.9) {
            this.grade = 9;
        } else if (check >= 90.0 && check <= 100.0) {
            this.grade = 10;
        } else {
            this.grade = 5;
        }
        return grade;
    }

    public boolean isPassed() {
        this.passed = getGrade() > 5;
        return passed;
    }

    @Override
    public String toString() {
        return String.format("ID: %s Name: %s First midterm: %d Second midterm %d Labs: %d Summary points: %.2f Grade: %d", index, name, pointsFirstMid, pointsSecondMid, pointsLabs, getPointsSummary(), getGrade());
    }
}

class AdvancedProgrammingCourse {
    Map<String, Student> students;
    List<Student> listStudents;

    public AdvancedProgrammingCourse() {
        this.students = new HashMap<>();
        this.listStudents = new ArrayList<>();
    }


    public void addStudent(Student s) {
        students.put(s.getIndex(), s);
        listStudents.add(s);
    }


    public void updateStudent(String idNumber, String activity, int points) {
        if (activity.equals("midterm1")) {
            students.get(idNumber).setPointsFirstMid(points);
        } else if (activity.equals("midterm2")) {
            students.get(idNumber).setPointsSecondMid(points);
        } else if (activity.equals("labs")) {
            students.get(idNumber).setPointsLabs(points);
        }
    }

    public List<Student> getFirstNStudents(int n) {
        return listStudents.stream()
                .sorted(Comparator.comparing(Student::getPointsSummary).reversed())
                .limit(n)
                .collect(Collectors.toList());
    }

    public Map<Integer, Integer> getGradeDistribution() {
        Map<Integer, Integer> result = new TreeMap<>();
        IntStream.range(5, 11).forEach(i -> result.put(i, 0));
        students.values().forEach(x -> result.computeIfPresent(x.getGrade(), (k, v) -> v + 1));
        return result;
    }

    public void printStatistics() {
        DoubleSummaryStatistics ds = listStudents.stream()
                .filter(Student::isPassed)
                .mapToDouble(Student::getPointsSummary).summaryStatistics();
        System.out.printf("Count: %d Min: %.2f Average: %.2f Max: %.2f\n", ds.getCount(), ds.getMin(), ds.getAverage(), ds.getMax());
    }
}

public class CourseTest {

    public static void printStudents(List<Student> students) {
        students.forEach(System.out::println);
    }

    public static void printMap(Map<Integer, Integer> map) {
        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
    }

    public static void main(String[] args) {
        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String command = parts[0];

            if (command.equals("addStudent")) {
                String id = parts[1];
                String name = parts[2];
                advancedProgrammingCourse.addStudent(new Student(id, name));
            } else if (command.equals("updateStudent")) {
                String idNumber = parts[1];
                String activity = parts[2];
                int points = Integer.parseInt(parts[3]);
                advancedProgrammingCourse.updateStudent(idNumber, activity, points);
            } else if (command.equals("getFirstNStudents")) {
                int n = Integer.parseInt(parts[1]);
                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
            } else if (command.equals("getGradeDistribution")) {
                printMap(advancedProgrammingCourse.getGradeDistribution());
            } else {
                advancedProgrammingCourse.printStatistics();
            }
        }
    }
}