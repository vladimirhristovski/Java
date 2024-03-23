package NP.NPKolokvium1.zad23;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

class InvalidOperationException extends Exception{
    public InvalidOperationException(String message) {
        super(message);
    }
}

abstract class Question{
    String text;
    int points;

    public Question(String text, int points) {
        this.text = text;
        this.points = points;
    }

    static List<String>VALID_ANSWER= Arrays.asList("A","B","C","D","E");
    static Question create(String input) throws InvalidOperationException {
        String[]words=input.split(";");
        String type=words[0];
        String text=words[1];
        int points=Integer.parseInt(words[2]);
        String answer=words[3];
        if(type.equals("MC")){
            if(!VALID_ANSWER.contains(answer)){
                throw new InvalidOperationException(String.format("%s is not allowed option for this question",answer));
            }else {
                return new MCQuestion(text,points,answer);
            }
        }else {
            return new TFQuestion(text,points,Boolean.parseBoolean(answer));
        }
    }

    public int getPoints() {
        return points;
    }
    abstract double answerPoints(String answer);
}

class TFQuestion extends Question{
    boolean answer;

    public TFQuestion(String text, int points, boolean answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //True/False Question: Question3 Points: 2 Answer: false
        return String.format("True/False Question: %s Points: %d Answer: %s",text,points,answer);
    }

    @Override
    double answerPoints(String answer) {
        return (this.answer==Boolean.parseBoolean(answer)) ? this.points : 0.0f;
    }
}

class MCQuestion extends Question{
    String answer;

    public MCQuestion(String text, int points, String answer) {
        super(text, points);
        this.answer = answer;
    }

    @Override
    public String toString() {
        //Multiple Choice Question: Question2 Points 4 Answer: E
        return String.format("Multiple Choice Question: %s Points %d Answer: %s",text,points,answer);
    }

    @Override
    double answerPoints(String answer) {
        return this.answer.equals(answer) ? this.points : this.points*-0.2f;
    }
}
class Quiz{
    List<Question>questions;

    public Quiz() {
        this.questions = new ArrayList<>();
    }
    void addQuestion(String questionData) throws InvalidOperationException {
        questions.add(Question.create(questionData));
    }
    void printQuiz(OutputStream os){
        PrintWriter pw=new PrintWriter(os);
        questions.stream()
                .sorted(Comparator.comparing(Question::getPoints).reversed())
                .forEach(question -> pw.println(question));
        pw.flush();
    }
    void answerQuiz (List<String> answers, OutputStream os) throws InvalidOperationException {
        if(answers.size()!= questions.size()){
            throw new InvalidOperationException("Answers and questions must be of same length!");
        }
        PrintWriter pw =new PrintWriter(os);
        double sum=0;
        for (int i = 0; i < answers.size(); i++) {
            pw.println(String.format("%d. %.2f",i+1,questions.get(i).answerPoints(answers.get(i))));
            sum+=questions.get(i).answerPoints(answers.get(i));
        }
        pw.println(String.format("Total points: %.2f",sum));
        pw.flush();
    }
}

public class QuizTest {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Quiz quiz = new Quiz();

        int questions = Integer.parseInt(sc.nextLine());

        for (int i=0;i<questions;i++) {
            try {
                quiz.addQuestion(sc.nextLine());
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        }

        List<String> answers = new ArrayList<>();

        int answersCount =  Integer.parseInt(sc.nextLine());

        for (int i=0;i<answersCount;i++) {
            answers.add(sc.nextLine());
        }

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase==1) {
            quiz.printQuiz(System.out);
        } else if (testCase==2) {
            try {
                quiz.answerQuiz(answers, System.out);
            } catch (InvalidOperationException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Invalid test case");
        }
    }
}