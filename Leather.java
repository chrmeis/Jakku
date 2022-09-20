import org.json.JSONString;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Leather {

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

    public JSONArray requestSmhi() throws IOException {
        //https://opendata-download-metobs-utv.smhi.se/api/version/{version}/parameter/{parameter}/station/{station}/period/{period}/data.csv.
        //Göteborg 227
        //"https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/parameter/t/point/lon/14.0038/lat/55.6049/data.json"
        //HttpRequest requesttest;
        //requesttest.BodyPublishers.noBody()).build();

        //Smhi returns a JSON
        JSONObject jsonObject;

        //The JSON it returns contains an array with the forecast for the next 10 days
        JSONArray jsonArray = null;

        try {
            //JSONObject periodsObject = readJsonFromUrl("https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/1/station/71420/period/latest-hour/data.json");
            //System.out.println(periodsObject.get("owner"));

            //Read Json from the API, currently uses Gothenburgs Coordinates
            jsonObject = readJsonFromUrl("https://opendata-download-metfcst.smhi.se/api/category/pmp3g/version/2/geotype/point/lon/11.974560/lat/57.708870/data.json");
            //Add the weather forecast to the array
            jsonArray = (JSONArray) jsonObject.get("timeSeries");
            //System.out.println(jsonObject.get("timeSeries"));
            //JSONObject periodsObject4 = readJsonFromUrl("https://opendata-download-metobs.smhi.se/api/category/pm3g/version/2/parameter.json");
            //JSONObject periodsObject3 = readJsonFromUrl("https://opendata-download-metobs.smhi.se/api/version/1.0/parameter/1/station.json");

        } catch (java.io.IOException e) {
            //TODO fix this exception
            e.printStackTrace();
            //throw new IOException("IOException");
        }
        //jsObject = response.body();
        //JSONString h = response.body();
        //String l = response.body();

        //Returns the forecast
        return jsonArray;
    }
}