package com.firstapp.jakku;

import android.os.AsyncTask;

public class TalkToServer extends AsyncTask<String, String, String> {
    //String text;
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
        //String string = Weather.currentTemp();
//do your work here
        return Weather.currentTemp();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        // do something with data here-display it or send to MainActivity
        //MainActivity.updateInfo(result + " ℃");
        //System.out.println(result + " C");

    }
}