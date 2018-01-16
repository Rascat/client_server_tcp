package net.schons.server;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;

/**
 * Handles client input the server passes on and sends requests to the OpenWeatherMap Weather API.
 */
class WeatherProtocol {

    /**
     * Constructor
     */
    WeatherProtocol() {

    }

    /**
     * Handles the input it receives via the input variable.
     * If the input string is not bye, it will assume it is a valid name of a city in Germany and send a request to the
     * OpenWeatherMap API.
     * The response from the OWM Weather API transformed to a JSONObject. The method will try to receive the values
     * of the keys 'name' and 'temp'. If that fails, the method will return a prompt to try again.
     * If the values can be retrieved, they are then used to construct a reply.
     *
     * @param input Input string from WeatherServer
     * @param apiKey
     * @return Either a positive response containing the requested city name and temperature or a prompt to try again.
     */
    String handleInput(String input, String apiKey) {
        // lets assume input is a valid city name

        if (input.equals("bye")) {
            return "bye";
        }
        String cityName = input;
        String countryId = "de";
        JSONObject result = openWeatherApiRequest(cityName, countryId, apiKey);
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
        // OWM responds with the temperature in kelvin. The temperature is being translated to celsius for convenience.
        String temp = String.format("%.2f", convertKelvinToCelsius((Double) tempData));

        return "Die Temperatur in " + cityData.toString() + " betraegt " + temp + "C°";
    }


    /**
     * Sends a request to the OpenWeatherMap API and return a JSONObject containing the data of the response
     * @param cityName the name of the city of which the weather data is requested
     * @param countryId the country id of the country in which the city is located
     * @param apiKey a valid OWM api key
     * @return
     */
    private JSONObject openWeatherApiRequest(String cityName, String countryId, String apiKey) {
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

    /**
     * Creates a greeting message that is being send to the client when it connects to the server for the first time.
     *
     * @return String with a greeting message.
     */
    String greeter()
    {
        return "Welcome to my weather service!%n" +
                "This service is based on the client - server paradigm as specified in the TCP.%n" +
                "You can type the name of most german cities in the prompt and the service%n" +
                "will return either the current temperature in celsius or an error message.%n" +
                "If you want to exit the program, just type \"bye\"";
    }

    /**
     * Converts kelvin to celsius
     *
     * @param kelvin The value in kelvin
     * @return The value in celsius
     */
    private float convertKelvinToCelsius(double kelvin) {
        return (float) kelvin - 273.15f;
    }
}
