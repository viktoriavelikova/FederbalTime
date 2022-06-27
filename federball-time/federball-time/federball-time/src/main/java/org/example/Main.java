package org.example;

import com.google.gson.Gson;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Main {
    private static final String URL = "http://api.weatherapi.com/v1/forecast.json?key=b2258c81904f49b3b65163405222006&q=Sofia&days=2&aqi=yes&alerts=no";

    public static void main(String[] args) {
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        try {
            HttpResponse<String> responce = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responceBody = responce.body();

            JSONObject obj = new JSONObject(responceBody);
            JSONObject forecast = obj.getJSONObject("forecast");
            JSONArray forecastDay = forecast.getJSONArray("forecastday");
            JSONArray forecastHour = forecastDay.getJSONObject(0).getJSONArray("hour");

            Gson gs = new Gson();
            Astro as = gs.fromJson(
                    forecastDay.getJSONObject(0).getJSONObject("astro").toString(),
                    Astro.class
            );

            String result="[\n ";
            for (int i = 0; i < forecastDay.length() ; i++) {
                String date = forecastDay.getJSONObject(i).get("date").toString();
                result = result.concat("{\n\"date\": \"" + date + "\",\n");
                ArrayList<String> hours = new ArrayList<>();
                int hour;
                for (int j = 0; j < forecastHour.length() -1 ; j++) {
                    Hour hourOne = gs.fromJson(
                            forecastDay.getJSONObject(i).getJSONArray("hour").getJSONObject(j).toString(),
                            Hour.class
                    );

                    Hour hourTwo = gs.fromJson(
                            forecastDay.getJSONObject(i).getJSONArray("hour").getJSONObject(j + 1).toString(),
                            Hour.class
                    );

                    SimpleDateFormat formater = new SimpleDateFormat("hh:mm a", Locale.US);
                    final LocalDateTime localDate = LocalDateTime.parse(hourOne.getTime(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

                    Date sunriseTime = formater.parse(as.getSunrise());
                    Date sunsetTime = formater.parse(as.getSunset());

                    Date hourOneDate = formater.parse(formater.format(hourOne.getTimeEpoch()));
                    Date hourTwoDate = formater.parse(formater.format(hourTwo.getTimeEpoch()));

                    Boolean isTimeBetweenSunriseAndSunset = hourOneDate.after(sunriseTime) && hourOneDate.before(sunsetTime) &&
                            hourTwoDate.after(sunriseTime) && hourTwoDate.before(sunsetTime);
                    Boolean isTemperatureAppropriate = hourOne.getTempC() > 10.0 && hourOne.getTempC() < 30.0 &&
                            hourTwo.getTempC() > 10.0 && hourTwo.getTempC() < 30.0;
                    Boolean isWeatherAppropriate = hourOne.getCloud() > 0 && hourTwo.getCloud() > 0;
                    Boolean isThereChanceOfRain = hourOne.getChanceOfRain() < 50 && hourTwo.getChanceOfRain() < 50;
                    Boolean isGustAppropriate = hourOne.getGustKph() < 5.0 && hourTwo.getGustKph() < 5.0;

                    if (isTimeBetweenSunriseAndSunset && isTemperatureAppropriate && isWeatherAppropriate
                            && isThereChanceOfRain && isGustAppropriate) {

                        hour=localDate.getHour();
                        hours.add("\"" + hour + "-" + (hour + 2) + "\"");

                    }
                }
                result=result.concat("\"hours\": "+hours.toString()+"\n}\n");
                hour=0;
            }
            result=result+"]";
            System.out.println(result);

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        } catch (ParseException p) {
            System.out.println("Error: " + p);
        }
    }

    public static boolean isWeekend(final LocalDate ld) {
        DayOfWeek day = DayOfWeek.of(ld.get(ChronoField.DAY_OF_WEEK));
        return day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY;
    }
}