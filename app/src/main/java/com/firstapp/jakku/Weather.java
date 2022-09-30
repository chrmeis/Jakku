package com.firstapp.jakku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    //private static JSONArray jsonArray = requestShortWeather();

    //public JSONArray getJsonArray() {return jsonArray;}

    public Weather(){}

    private static String readStringFromUrl(String url) throws IOException {

        try (InputStream inputStream = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            return stringBuilder.toString();
        }
    }

    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }

    public static JSONArray requestWeather() {

        //SMHI returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {

            //Read Json from the API, currently uses Gothenburg Coordinates
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

        //SMHI returns a JSON
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
     * This returns a JSONArray with the following 5 parameters (NOT IN THIS ORDER!!!!): Precipitation category, temperature, wind speed, relative humidity and weather symbol.
     *
     * @return JSONArray
     */
    public static JSONArray requestShortWeather(){
        //JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        //JSONArray jsonArray1 = (JSONArray) jsonObject.get("parameters");

        JSONArray jsonArray = requestWeather();
        //int[] intArray = new int[]{17,16,13,12,11,9,8,7,6,5,4,3,2,0}; //These are the parameters we do not care about
        String[] strings = new String[]{"msl", "vis", "wd", "tstm", "tcc_mean", "lcc_mean", "mcc_mean", "hcc_mean", "gust", "pmin", "pmax", "spp", "pmean", "pmedian"};
        for (int i = 0; i < jsonArray.length(); i++) {
            for (String delete : strings) {
                for (int j = 0; j < 18; j++) {
                    try{
                        if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals(delete)) {
                            ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).remove(j);
                            break;
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return  jsonArray;
    }

    /**
     * This returns a JSONArray with the following 5 parameters (NOT IN THIS ORDER!!!!): Precipitation category, temperature, wind speed, relative humidity and weather symbol.
     *
     * @return JSONArray
     */
    public static JSONArray requestShortWeather(String latitude, String longitude){
        //JSONObject jsonObject = (JSONObject) jsonArray.get(0);
        //JSONArray jsonArray1 = (JSONArray) jsonObject.get("parameters");

        JSONArray jsonArray = requestWeather(latitude, longitude);
        //int[] intArray = new int[]{17,16,13,12,11,9,8,7,6,5,4,3,2,0}; //These are the parameters we do not care about
        String[] strings = new String[]{"msl", "vis", "wd", "tstm", "tcc_mean", "lcc_mean", "mcc_mean", "hcc_mean", "gust", "pmin", "pmax", "spp", "pmean", "pmedian"};
        for (int i = 0; i < jsonArray.length(); i++) {
            for (String delete : strings) {
                for (int j = 0; j < 18; j++) {
                    try{
                        if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals(delete)) {
                            ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).remove(j);
                            break;
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return  jsonArray;
    }

    public static String currentTemp() {
        JSONArray jsonArray = requestShortWeather();
        String string = "Error getting temperature";
        try {
            string = ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(0)).get("parameters")).get(1)).get("values")).get(0).toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return string;
    }

    /**
     * Looks to see if there's any precipitation within the next 24 hours at 07:00, 13:00 and 18:00 and returns a HashMap with the data.
     * @return
     */
    public static HashMap<Integer, Boolean> rainToday() throws JSONException {

        HashMap<Integer, Boolean> hashMap = new HashMap<Integer, Boolean>();
        JSONArray jsonArray = requestWeather();

        //Loops over 24 hours
        for (int i = 0; i < 24 ; i++){
            String validTime =((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11,13);
            if (validTime.equals("07") || validTime.equals("13") || validTime.equals("18")){    //Only looks at the weather forecast at 07:00, 13:00 and 18:00
                for (int j = 0 ; j < 19 ; j++) {
                    if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                        if(0 < (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0)){ //If true, there is precipitation, otherwise there is no precipitation.
                            hashMap.put(Integer.parseInt(validTime), true);
                        }
                        else{
                            hashMap.put(Integer.parseInt(validTime), false);
                        }
                        break;
                    }
                }
            }
        }
        return hashMap;
    }
}