package NP.NPKolokvium2.zad38;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class AnswersNumberMismatch extends Exception {
    public AnswersNumberMismatch() {
        super("A quiz must have same number of correct and selected answers");
    }
}

class Quiz {
    private String studentId;
    private List<String> correctAnswers;
    private List<String> studentAnswers;
    private double studentPoints;

    public Quiz(String studentId, List<String> correctAnswers, List<String> studentAnswers) throws AnswersNumberMismatch {
        if (correctAnswers.size() != studentAnswers.size()) {
            throw new AnswersNumberMismatch();
        }
        this.studentId = studentId;
        this.correctAnswers = correctAnswers;
        this.studentAnswers = studentAnswers;
        IntStream.range(0, correctAnswers.size())
                .forEach(i -> {
                    if (correctAnswers.get(i).equals(studentAnswers.get(i))) {
                        this.studentPoints++;
                    } else {
                        this.studentPoints -= 0.25;
                    }
                });
    }

    public static Quiz generateQuiz(String line) throws AnswersNumberMismatch {
        //200000;C, D, D, D, A, C, B, D, D;C, D, D, D, D, B, C, D, A
        String[] words = line.split(";");
        String id = words[0];
        List<String> corr = new ArrayList<>(Arrays.asList(words[1].split(",")));
        List<String> check = new ArrayList<>(Arrays.asList(words[2].split(",")));
        return new Quiz(id, corr, check);
    }

    public String getStudentId() {
        return studentId;
    }

    public double getStudentPoints() {
        return studentPoints;
    }
}

class QuizProcessor {

    public static Map<String, Double> processAnswers(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        return br.lines()
                .map(line -> {
                    try {
                        return Quiz.generateQuiz(line);
                    } catch (AnswersNumberMismatch e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Quiz::getStudentId, Quiz::getStudentPoints, Double::sum, TreeMap::new));
    }
}

public class QuizProcessorTest {
    public static void main(String[] args) {
        QuizProcessor.processAnswers(System.in).forEach((k, v) -> System.out.printf("%s -> %.2f%n", k, v));
    }
}