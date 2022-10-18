package com.firstapp.jakku;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather {
    //private static JSONArray jsonArray = requestShortWeather();

    //public JSONArray getJsonArray() {return jsonArray;}

    private static String longitude = "11.974560";
    private static String latitude = "57.708870";

    /**
     * Saves the chosen coordinates and cuts them down to 6 decimals if necessary.
     * @param new_longitude The new longitude coordinate
     * @param new_latitude The new latitude coordinate
     */
    public static void saveCoords(String new_longitude, String new_latitude){
        longitude = new_longitude;
        latitude = new_latitude;
        //SMHI can't use coordinates with more than 6 decimals.
        if (9 < longitude.length()){
            longitude = longitude.substring(0, 9);
        }
        if (9 < latitude.length()){
            latitude = latitude.substring(0, 9);
        }
    }

    /**
     * Reads a string as an URL and tries to returns any data in a String
     * @param url The URL the method tries to get to.
     * @return Any data that the server returns.
     * @throws IOException If an INPUT/OUTPUT Exception occurs then the method throws a IOException.
     *                     The URL may be invalid.
     */
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

    /**
     * Creates a JSONObject with the information sent from the URL.
     * @param url The URL used to get the data.
     * @return JSONObject with data from the URL
     * @throws IOException INPUT/OUTPUT error, if the URL is invalid
     * @throws JSONException If the data returned is not valid to create a JSONObject with.
     */
    private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }

    /**
     * Requests a ten day weather forecast from the SMHI API and returns it as a JSONArray
     * @return JSONArray with 10 day weather forecast of currently saved coordinates.
     */
    public static JSONArray requestWeather() {

        //SMHI returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {

            //Read Json from the API, currently uses Gothenburg Coordinates
            jsonObject = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/"+longitude+"/lat/"+latitude+"/data.json");
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
     * @return JSONArray with ten day weather forecast of currently saved coordinates.
     */
    public static JSONArray requestShortWeather(){

        //Uses the normal function to get the full forecast
        JSONArray jsonArray = requestWeather();
        //These are the parameters we do not want in our short forecast
        String[] strings = new String[]{"msl", "vis", "wd", "tstm", "tcc_mean", "lcc_mean", "mcc_mean", "hcc_mean", "gust", "pmin", "pmax", "spp", "pmean", "pmedian"};

        //Loops through the parameters and removes all the unwanted parameters.
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
        //Returns the JSONArray with the removed parameters.
        return  jsonArray;
    }

    /**
     * Looks at the current temperature according to the devices time.
     * @return String with the current temperature
     * @throws JSONException If an error occurs with the SMHI request.
     */
    public static String currentTemp() throws JSONException {
        JSONArray jsonArray = requestShortWeather();

        //Looks at the current time according to the device it is running on.
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();

        //Loops through the forecast to find one for the current time.
        for (int i = 0; i < 34 ; i++) {
            String validTime = ((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11, 13);
            if (validTime.equals(hour.format(now))){
                for (int j = 0; j < 5; j++) {
                    try {
                        if (((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("t")) {
                             return ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0).toString();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        //If the for loop fails to find a time then it returns this string instead.
        return "Error getting temperature";
    }

    /**
     * Looks at the current precipitation according to the devices time.
     * @return Int with the precipitation. 0 for no precipitation and above 0 for precipitation.
     * @throws JSONException If an error has occurred with the SMHI request
     */
    /*public static int currentRain() throws JSONException {
        JSONArray jsonArray = requestShortWeather();

        //Looks at the current time according to the device it is running on.
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();

        //Loops through the forecast to find one for the current time.
        for (int i = 0; i < 34 ; i++) {
            String validTime = ((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11, 13);
            if (validTime.equals(hour.format(now))){
                for (int j = 0; j < 5; j++) {
                    try {
                        if (((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")) {
                            return (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //If the loop fails to find a matching time then it returns this instead.
        return -1;
    }
*/
    /**
     * Looks to see if there's any precipitation within the next 24 hours at 07:00, 13:00 and 18:00 and returns a HashMap with the data.
     * @return Returns a HashMap with the times and boolean for precipitation.
     */
    /*public static HashMap<Integer, Boolean> rainToday() throws JSONException {

        HashMap<Integer, Boolean> hashMap = new HashMap<>();
        JSONArray jsonArray = requestWeather();

        //Loops over 24 hours
        for (int i = 0; i < 24 ; i++){
            String validTime =((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11,13);
            if (validTime.equals("07") || validTime.equals("13") || validTime.equals("18")){    //Only looks at the weather forecast at 07:00, 13:00 and 18:00
                for (int j = 0 ; j < 19 ; j++) {
                    if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                        //If the value is above 0 then theres precipitation.
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
    */
    /**
     * Checks if there is going to be any rain at 18:00.
     * If the clock is before 19:00 it returns based on the weather today and after 19:00 it returns for tomorrow.
     * @return String "Outdoor" or "Indoor" depending on the weather
     */
    public static String rain18() throws JSONException {
        //Looks at the current time according to the devices time.
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();

        JSONArray jsonArray = requestWeather();

        //Loops 34 times looking for a value for the current time.
        for (int i = 0; i < 34 ; i++){
            String validTime =((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11,13);
            if (validTime.equals("18")){    //Only looks at the weather forecast at 18:00
                if(18 < Integer.parseInt(hour.format(now))){
                    if (!day.format(now).equals(((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(8,10))){
                        for (int j = 0 ; j < 19 ; j++) {
                            if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                                if(0 < (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0)){ //If true, there is precipitation, otherwise there is no precipitation.
                                    return "Indoor";
                                }
                                else{
                                    return "Outdoor";
                                }
                            }
                        }
                    }
                }
                else{
                    for (int j = 0 ; j < 19 ; j++) {
                        if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                            if(0 < (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0)){ //If true, there is precipitation, otherwise there is no precipitation.
                                return "Indoor";
                            }
                            else{
                                return "Outdoor";
                            }
                        }
                    }
                }
            }
        }

        return "Error";
    }
    // Rätt metod
    public static int currentRain() throws JSONException{
        JSONArray jsonArray = requestShortWeather();

        //Looks at the current time according to the device it is running on.
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        LocalDateTime now = LocalDateTime.now();

        //Loops through the forecast to find one for the current time.
        for (int i = 0; i < 34 ; i++) {
            String validTime = ((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11, 13);
            if (validTime.equals(hour.format(now))){
                for (int j = 0; j < 5; j++) {
                    try {
                        if (((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")) {
                            return (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        //If the loop fails to find a matching time then it returns this instead.
        return -1;
    }

    /**
     * Looks to see if there's any precipitation within the next 24 hours at 07:00, 13:00 and 18:00 and returns a HashMap with the data.
     * @return
     */
    // rätt
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
    /**
     *
     * @return String "Outdoor" or "Indoor" depending on the weather
     */
   /* public static String rain18() throws JSONException {
        DateTimeFormatter hour = DateTimeFormatter.ofPattern("HH");
        DateTimeFormatter day = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();

        JSONArray jsonArray = requestWeather();

        for (int i = 0; i < 34 ; i++){
            String validTime =((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(11,13);
            if (validTime.equals("18")){    //Only looks at the weather forecast at 18:00
                if(18 < Integer.parseInt(hour.format(now))){
                    if (!day.format(now).equals(((String) ((JSONObject) jsonArray.get(i)).get("validTime")).substring(8,10))){
                        for (int j = 0 ; j < 19 ; j++) {
                            if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                                if(0 < (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0)){ //If true, there is precipitation, otherwise there is no precipitation.
                                    return "Indoor";
                                }
                                else{
                                    return "Outdoor";
                                }
                            }
                        }
                    }
                }
                else{
                    for (int j = 0 ; j < 19 ; j++) {
                        if(((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("name").equals("pcat")){   //pcat is a category that has the number 0 if there is no precipitation and a number above 0 depending on the type of precipitation
                            if(0 < (int) ((JSONArray)((JSONObject) ((JSONArray) ((JSONObject) jsonArray.get(i)).get("parameters")).get(j)).get("values")).get(0)){ //If true, there is precipitation, otherwise there is no precipitation.
                                return "Indoor";
                            }
                            else{
                                return "Outdoor";
                            }
                        }
                    }
                }
            }
        }

        return "Error";
    }
    */
}