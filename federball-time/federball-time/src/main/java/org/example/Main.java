package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Iterator;

public class Main {
    private static final String URL = "http://api.weatherapi.com/v1/forecast.json?key=5e993da5c0634910958192425221406&q=London&days=1&aqi=no&alerts=no";

    public static void main(String[] args) {

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .build();
        try {


            HttpResponse<String> responce = client.send(request, HttpResponse.BodyHandlers.ofString());
            String responceBody = responce.body();
            System.out.println(responceBody);
             int poriv;
            JSONObject obj = new JSONObject(responceBody);
            JSONObject focastday = new JSONObject();
            JSONArray forcastday = obj.getJSONObject("forecast").getJSONArray("forecastday");
            Iterator<Object> iterator = forcastday.iterator();
            while (iterator.hasNext()) {
                JSONObject dayObj = (JSONObject) iterator.next();
                JSONObject astro = dayObj.getJSONObject("astro");
                String sunrise = astro.getString("sunrise");

                JSONArray hourarray =dayObj.getJSONArray("hour");
                Iterator<Object> iterator1=hourarray.iterator();
                while (iterator1.hasNext()){
                    JSONObject hourObj = (JSONObject) iterator.next();
                     poriv= hourarray.getInt("");

                }


                System.out.println("------" + sunrise);

            }
            // String [] myArray = {"JavaFX", "HBase", "JOGL", "WebGL"};
            // JSONArray jsArray = new JSONArray();
            // for (int i = 0; i < myArray.length; i++) {
            //    jsArray.put(myArray[i]);
            // }
            //System.out.println(jsArray);
            // String[] array = new String[focastday.length()];
            //for (int i = 0; i < focastday.length(); i++) {
            //   array[i] = (String)focastday.getString("astro");
            // }

            // List<String> list = new ArrayList<String>();
            // for (int i = 0; i < forcastday.length(); i++) {
            //  list.add(forcastday.getString(i));
            //}

            // String[] stringArray = list.toArray(new String[list.size()]);
            //for (int i = 0; i < forcastday.length(); i++) {
            //   Array forcastday1 = forcastday.getString(i);
            // System.out.println(forcastday1);
            // }
            // final LocalDate localDate = LocalDate.parse("2022-06-14", DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            // final LocalTime localTime = LocalTime.parse("04:53 AM", DateTimeFormatter.ofPattern("hh:mm a"));
            //LocalDateTime localDateTime = LocalDateTime.parse("2022-06-15 00:00",
            //DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm"));


        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

}
