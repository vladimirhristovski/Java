package NP.NPKolokvium2.zad29;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

class Team {
    String name;
    int matchesPlayed;
    int wins;
    int draws;
    int losses;
    int points;
    int goalsScored;
    int goalsConceded;

    public Team(String name) {
        this.name = name;
        this.matchesPlayed = 0;
        this.wins = 0;
        this.draws = 0;
        this.losses = 0;
        this.points = 0;
        this.goalsScored = 0;
        this.goalsConceded = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(int matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int getGoalDifference() {
        return goalsScored - goalsConceded;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name, matchesPlayed, wins, draws, losses, points);
    }
}

class FootballTable {
    HashMap<String, Team> teams;

    public FootballTable() {
        this.teams = new HashMap<>();
    }

    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        teams.putIfAbsent(homeTeam, new Team(homeTeam));
        teams.putIfAbsent(awayTeam, new Team(awayTeam));
        teams.get(homeTeam).setGoalsScored(teams.get(homeTeam).getGoalsScored() + homeGoals);
        teams.get(awayTeam).setGoalsScored(teams.get(awayTeam).getGoalsScored() + awayGoals);
        teams.get(homeTeam).setGoalsConceded(teams.get(homeTeam).getGoalsConceded() + awayGoals);
        teams.get(awayTeam).setGoalsConceded(teams.get(awayTeam).getGoalsConceded() + homeGoals);
        teams.get(homeTeam).setMatchesPlayed(teams.get(homeTeam).getMatchesPlayed() + 1);
        teams.get(awayTeam).setMatchesPlayed(teams.get(awayTeam).getMatchesPlayed() + 1);
        if (homeGoals == awayGoals) {
            teams.get(homeTeam).setDraws(teams.get(homeTeam).getDraws() + 1);
            teams.get(awayTeam).setDraws(teams.get(awayTeam).getDraws() + 1);
            teams.get(homeTeam).setPoints(teams.get(homeTeam).getPoints() + 1);
            teams.get(awayTeam).setPoints(teams.get(awayTeam).getPoints() + 1);
        } else if (homeGoals > awayGoals) {
            teams.get(homeTeam).setWins(teams.get(homeTeam).getWins() + 1);
            teams.get(homeTeam).setPoints(teams.get(homeTeam).getPoints() + 3);
            teams.get(awayTeam).setLosses(teams.get(awayTeam).getLosses() + 1);
        } else {
            teams.get(awayTeam).setWins(teams.get(awayTeam).getWins() + 1);
            teams.get(awayTeam).setPoints(teams.get(awayTeam).getPoints() + 3);
            teams.get(homeTeam).setLosses(teams.get(homeTeam).getLosses() + 1);
        }
    }

    public void printTable() {
        AtomicInteger i = new AtomicInteger(1);
        teams.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.comparing(Team::getPoints).thenComparing(Team::getGoalDifference).reversed().thenComparing(Team::getName)))
                .forEach(stringTeamEntry -> {
                    StringBuilder sb = new StringBuilder();
                    if (i.get() >= 10) {
                        sb.append(i.get()).append(". ").append(stringTeamEntry.getValue());
                    } else {
                        sb.append(" ").append(i.get()).append(". ").append(stringTeamEntry.getValue());
                    }
                    System.out.println(sb);
                    i.getAndIncrement();
                });
    }
}

public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}