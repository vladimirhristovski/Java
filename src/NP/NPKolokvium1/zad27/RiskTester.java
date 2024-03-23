package NP.NPKolokvium1.zad27;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Game{
    List<Integer>attack;
    List<Integer>defence;
    Game(String input){
        String[]words=input.split(";");
        this.attack=give(words[0]);
        this.defence=give((words[1]));
    }

    private List<Integer> give(String input){
        return Arrays.stream(input.split("\\s+"))
                .map(word->Integer.parseInt(word))
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    int hasAttackedWin(){
        int wins=0;
        for (int i = 0; i < attack.size(); i++) {
            if(attack.get(i)>defence.get(i)){
                wins++;
            }
        }
        return wins;
    }
    int hasDefenceWin(){
        int wins=0;
        for (int i = 0; i < attack.size(); i++) {
            if(attack.get(i)<=defence.get(i)){
                wins++;
            }
        }
        return wins;
    }

    @Override
    public String toString() {
        return hasAttackedWin()+" "+hasDefenceWin();
    }
}

class Risk{
    void processAttacksData(InputStream is) throws IOException {
        BufferedReader br=new BufferedReader(new InputStreamReader(is));

        List<Game>games=br.lines()
                .map(line->new Game(line))
                .collect(Collectors.toList());

        for (Game game : games) {
            System.out.println(game);
        }

        br.close();
    }
}

public class RiskTester {
    public static void main(String[] args) {
        Risk risk = new Risk();
        try {
            risk.processAttacksData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}