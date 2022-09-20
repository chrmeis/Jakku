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

    private String readStringFromUrl(String url) throws IOException {

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
    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }

    public JSONArray requestWeather() {

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
        }

        //Returns the forecast
        return jsonArray;
    }

    public JSONArray requestWeather(String latitude, String longitude){

        //Smhi returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {

            //Read Json from the API, currently uses Gothenburgs Coordinates
            jsonObject = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/" + longitude + "/lat/" + latitude + "/data.json");
            //Add the weather forecast to the array
            jsonArray = (JSONArray) jsonObject.get("timeSeries");

        } catch (java.io.IOException e) {
            //TODO fix this exception
            e.printStackTrace();
            //throw new IOException("IOException");
        }

        //Returns the forecast
        return jsonArray;
    }

    public JSONArray requestShortWeather(){
        JSONArray jsonArray = requestWeather();


        return  jsonArray;

    }
}