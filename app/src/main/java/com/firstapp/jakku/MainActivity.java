package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView travelTextView;
    TextView info;
    TextView planner;
//    Button settings;
    //Weather weather;
    private String temp;
    private String tempFinal;
    ImageView weatherSymbol;
    private int finalRain;

    public void updateInfo(String string){
        info.setText(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        travelTextView = (TextView) findViewById(R.id.travelText);
        info=(TextView) findViewById(R.id.textView2);
        weatherSymbol = (ImageView) findViewById(R.id.imageView2);
        planner = (TextView) findViewById(R.id.textView3);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("locationshare", MODE_PRIVATE);
                String lat = sharedPreferences.getString("latitude","57.708870");
                String lon = sharedPreferences.getString("longitude","11.974560");
                Weather.saveCoords(lon,lat);
                temp = null;
                try{
                    temp = Weather.currentTemp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int rain = 0;
                try{
                    rain = Weather.currentRain();
                } catch(JSONException e){
                    e.printStackTrace();
                }
                tempFinal = temp;
                finalRain = rain;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateInfo(tempFinal + " ℃");
                        if(finalRain == 0){
                            weatherSymbol.setImageResource(R.drawable.sunshine1);
                        } else {
                            weatherSymbol.setImageResource(R.drawable.rain);
                        }
                        if(getRain() > 0 && Double.parseDouble(getTemp()) < 10.0){
                            travelTextView.setText("It's recommended to take the bus today, be sure to bring a jacket!");
                        } else if(getRain() > 0){
                            travelTextView.setText("It's recommended to take the bus today!");
                        }
                        if(getRain() == 0 && Double.parseDouble(getTemp()) < 10.0){
                            travelTextView.setText("It's recommended to take a walk or go biking today, be sure to bring a jacket");
                        } else if(getRain() == 0){
                            travelTextView.setText("It's recommended to take a walk or go biking today!");
                        }
                    }
                });
            }
        });
    }

    public void updateTravelPlans(){
        if(getRain() > 0 && Double.parseDouble(getTemp()) < 10.0){
            travelTextView.setText("It's recommended to take the bus today, be sure to bring a jacket!");
        } else if(getRain() > 0){
            travelTextView.setText("It's recommended to take the bus today!");
        }
        if(getRain() == 0 && Double.parseDouble(getTemp()) < 10.0){
            travelTextView.setText("It's recommended to take a walk or go biking today, be sure to bring a jacket");
        } else if(getRain() == 0){
            travelTextView.setText("It's recommended to take a walk or go biking today!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Notifications");
        menu.add("Study Preferences");
        menu.add("Practice Preferences");
        return super.onCreateOptionsMenu(menu);
    }

    public String getTemp(){
        return tempFinal;
    }

    public int getRain(){
        return finalRain;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem) {
        if(menuItem.getTitle().equals("Notifications")){
            Intent i = new Intent(MainActivity.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(menuItem.getTitle().equals("Study Preferences")){
            Intent i = new Intent(MainActivity.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(menuItem.getTitle().equals("Practice Preferences")){
            Intent i = new Intent(MainActivity.this,practice_preferences.class);
            startActivity(i);
            finish();
            return true;
        }
        int id = menuItem.getItemId();
        return super.onOptionsItemSelected(menuItem);
    }
}

