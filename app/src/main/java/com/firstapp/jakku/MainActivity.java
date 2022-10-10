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
import android.widget.TextView;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView info;
//    Button settings;
    //Weather weather;
    private String temp;

    public void updateInfo(String string){
        info.setText(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info=(TextView) findViewById(R.id.textView2);

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
                String finalTemp = temp;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateInfo(finalTemp + " ℃");
                    }
                });
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Notifications");
        menu.add("Study Preferences");
        menu.add("Practice Preferences");
        menu.add("Travel Planning");
        return super.onCreateOptionsMenu(menu);
    }

    public String getTemp(){
        return temp;
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
        } else if(menuItem.getTitle().equals("Travel Planning")){
            Intent i = new Intent(MainActivity.this, Travel.class);
            startActivity(i);
            finish();
            return true;
        }
        int id = menuItem.getItemId();
        return super.onOptionsItemSelected(menuItem);
    }
}

