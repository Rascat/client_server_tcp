package net.lulu.server;


import org.json.JSONArray;
import org.json.JSONObject;
import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.*;

public class WeatherProtocol {

    WeatherProtocol() {

    }

    String handleInput(String input) {
        // lets assume input is a valid city name
        String cityName = input;
        String countryId = "de";
        String apiKey = "#";
        JSONObject result = sendOpenWeatherApiRequest(cityName, countryId, apiKey);
        JSONObject mainData = result.getJSONObject("main");

        return Float.toString(convertKelvinToCelsius((Double) mainData.get("temp")));
    }

    public JSONArray getCityList(String filename, String encoding) {
        Charset charset = Charset.forName(encoding);
        File file = new File(filename);
        StringBuilder json = new StringBuilder();
        String line;
        try (
                BufferedReader br = Files.newBufferedReader(file.toPath(), charset)
        ) {
            while((line = br.readLine()) != null) {
                json.append(line);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return new JSONArray(json.toString());
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
        }
        return new JSONObject(json.toString());
    }

    private float convertKelvinToCelsius(double kelvin) {
        return (float) kelvin - 273.15f;
    }
}
