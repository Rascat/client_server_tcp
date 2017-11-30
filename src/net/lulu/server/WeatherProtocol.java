package net.lulu.server;


import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;

class WeatherProtocol {

    WeatherProtocol() {

    }

    String handleInput(String input, String apiKey) {
        // lets assume input is a valid city name

        if (input.equals("bye")) {
            return "bye";
        }
        String cityName = input;
        String countryId = "de";
        JSONObject result = sendOpenWeatherApiRequest(cityName, countryId, apiKey);
        JSONObject mainData;
        Object tempData;
        Object cityData;
        try {
            mainData = result.getJSONObject("main");
            tempData = mainData.get("temp");
            cityData = result.get("name");
        } catch (JSONException je) {
            je.printStackTrace();
            return "Something went wrong please try again.";
        }
        String temp = String.format("%.2f", convertKelvinToCelsius((Double) tempData));

        return "In " + cityData.toString() + " ist es gerade " + temp + "C°";
    }


    private JSONObject sendOpenWeatherApiRequest(String cityName, String countryId, String apiKey) {
        StringBuilder json = new StringBuilder();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + cityName + "," + countryId + "&appid=" + apiKey;
        String line;
        try {
            URL weatherUrl = new URL(url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(weatherUrl.openStream())
            );

            while((line = in.readLine()) != null) {
                json.append(line);
            }
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            json.append("{ \"stat\" : \"error\"}");
        }
        System.out.println(json.toString());
        return new JSONObject(json.toString());
    }

    String sendGreeter()
    {
        return "Welcome to my weather service!%n" +
                "This service is based on the client - server paradigm as specified in the TCP.%n" +
                "You can type the name of most German cities in the prompt and the service%n" +
                "will return either the current temperature in celsius or an error message.%n" +
                "If you want to exit the program, just type \"bye\"";
    }

    private float convertKelvinToCelsius(double kelvin) {
        return (float) kelvin - 273.15f;
    }
}
