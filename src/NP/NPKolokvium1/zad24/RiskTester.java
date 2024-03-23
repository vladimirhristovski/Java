package NP.NPKolokvium1.zad24;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Game{
    List<Integer>attacks;
    List<Integer>defendings;

    public Game(List<Integer> attacks, List<Integer> defendings) {
        this.attacks = attacks;
        this.defendings = defendings;
    }

    Game(String string){
        String[]sides=string.split(";");
       this.attacks= parseDice(sides[0]);
       this.defendings= parseDice(sides[1]);
    }

    private List<Integer> parseDice(String input){
        return Arrays.stream(input.split("\\s+"))
                .map(dice->Integer.parseInt(dice))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }
    boolean hasAttackedWin(){
       return IntStream.range(0,attacks.size())
                .allMatch(i->attacks.get(i)>defendings.get(i));
    }
}

class Risk{
    int processAttacksData (InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int result=(int)br.lines()
                .map(line->new Game(line))
                .filter(round-> round.hasAttackedWin())
                .count();

        br.close();

        return result;
    }
}

public class RiskTester {
    public static void main(String[] args) {

        Risk risk = new Risk();

        try {
            System.out.println(risk.processAttacksData(System.in));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}