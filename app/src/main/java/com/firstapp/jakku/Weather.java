package com.firstapp.jakku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    private static JSONArray jsonArray = requestShortWeather();

    public JSONArray getJsonArray() {
        return jsonArray;
    }

    public Weather(){}

    private static String readStringFromUrl(String url) throws IOException {

        InputStream inputStream = new URL(url).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            return stringBuilder.toString();
        } finally {
            inputStream.close();
        }
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }

    public static JSONArray requestWeather() {

        //Smhi returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {

            //Read Json from the API, currently uses Gothenburgs Coordinates
            jsonObject = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/11.974560/lat/57.708870/data.json");
            //Add the weather forecast to the array
            jsonArray = (JSONArray) jsonObject.get("timeSeries");

        } catch (java.io.IOException e) {
            //TODO fix this exception
            e.printStackTrace();
            //throw new IOException("IOException");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Returns the forecast
        return jsonArray;
    }

    public static JSONArray requestWeather(String latitude, String longitude) {

        //Smhi returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {

            //Read Json from the API, currently uses Coordinates of the users choice
            jsonObject = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + longitude + "/lat/" + latitude + "/data.json");
            //Add the weather forecast to the array
            jsonArray = (JSONArray) jsonObject.get("timeSeries");

        } catch (IOException | JSONException e) {
            //TODO fix this exception
            e.printStackTrace();
            //throw new IOException("IOException");
        }

        //Returns the forecast
        return jsonArray;
    }

    /**
     * This returns a JSONArray with only 5 parameters in the following order: Precipitation category, temperature, wind speed, relative humidity and weather symbol.
     *
     * @return JSONArray
     */
    public static JSONArray requestShortWeather() {
        //JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        //JSONArray jsonArray1 = (JSONArray) jsonObject.get("parameters");

        JSONArray jsonArray = requestWeather();
        int[] intArray = new int[]{17, 16, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3, 2, 0}; //These are the parameters we do not care about
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int delete : intArray) {
                try {
                    ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).remove(delete);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonArray;

    }

    /**
     * This returns a JSONArray with only 5 parameters in the following order: Precipitation category, temperature, wind speed, relative humidity and weather symbol.
     *
     * @return JSONArray
     */
    public static JSONArray requestShortWeather(String latitude, String longitude) {
        //JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        //JSONArray jsonArray1 = (JSONArray) jsonObject.get("parameters");

        JSONArray jsonArray = requestWeather(latitude, longitude);
        int[] intArray = new int[]{17, 16, 13, 12, 11, 9, 8, 7, 6, 5, 4, 3, 2, 0}; //These are the parameters we do not care about
        for (int i = 0; i < jsonArray.length(); i++) {
            for (int delete : intArray) {
                try {
                    ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).remove(delete);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }

        return jsonArray;

    }

    public static String temperature() {
        //JSONArray jsonArray = null;
        String string = "Error getting temperature";
        try {
            string = ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(0)).get("parameters")).get(1)).get("values")).get(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return string;
    }
}