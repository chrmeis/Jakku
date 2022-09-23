package com.firstapp.jakku;

import android.os.AsyncTask;

import com.firstapp.jakku.Weather;

import org.json.JSONArray;

public class TalkToServer extends AsyncTask<String, String, String> {
    String text;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(String... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected String doInBackground(String... params) {
        //Weather weather = new Weather();
        String string = Weather.temperature();
//do your work here
        return string;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // do something with data here-display it or send to mainactivity
        MainActivity.updater(result);
        System.out.println(result + " C");

    }
}