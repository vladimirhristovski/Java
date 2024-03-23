package NP.NPKolokvium1.zad14;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

class Forecast{
    float temperature;
    float wind;
    float humidity;
    float visibility;
    LocalDateTime date;

    public Forecast(float temperature, float wind, float humidity, float visibility, LocalDateTime date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    @Override
    public String toString() {

        //22.1 18.9 km/h 1.3% 24.6 km Tue Dec 17 23:30:15 GMT 2013
        return String.format("%.1f %.1f km/h %.1f%% %.1f km %s GMT %s",temperature,wind,humidity,visibility,
                date.format(DateTimeFormatter.ofPattern("EEE MMM d HH:mm:ss")),
                date.format(DateTimeFormatter.ofPattern("yyyy")));
    }
}
class WeatherStation{
    int days;
    List<Forecast>forecasts;

    public WeatherStation(int days) {
        this.days = days;
        this.forecasts=new ArrayList<>();
    }
    boolean checkWeather(LocalDateTime date){
        for (Forecast forecast : forecasts) {
            if(date.isBefore(forecast.date.plusMinutes(2).plusSeconds(30))){
                return true;
            }
        }
        return false;
    }
    void reDo(LocalDateTime date){
        forecasts=forecasts.stream()
                .filter(forecast -> forecast.date.isAfter(date.minusDays(days)))
                .collect(Collectors.toList());
    }
    public void addMeasurment(float temperature, float wind, float humidity, float visibility, LocalDateTime date){
        if(!forecasts.isEmpty()) {
            if (checkWeather(date)) {
                return;
            }
        }
        reDo(date);
        forecasts.add(new Forecast(temperature,wind,humidity,visibility,date));
    }
    public int total(){
        return forecasts.size();
    }
    public void status(LocalDateTime from, LocalDateTime to){
        List<Forecast>statusForcasts = forecasts.stream()
                .filter(forecast -> forecast.date.isAfter(from.minusSeconds(1)))
                .filter(forecast -> forecast.date.isBefore(to.plusSeconds(1)))
                .collect(Collectors.toList());
        if(statusForcasts.isEmpty()){
            throw new RuntimeException();
        }
        statusForcasts.stream().forEach(forecast -> System.out.println(forecast.toString()));
        System.out.println(String.format("Average temperature: %.2f",statusForcasts.stream().mapToDouble(forecasts->forecasts.temperature).average().getAsDouble()));
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            LocalDateTime date = LocalDateTime.parse(line,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        LocalDateTime from = LocalDateTime.parse(line,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        line = scanner.nextLine();
        LocalDateTime to = LocalDateTime.parse(line,DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"));
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}