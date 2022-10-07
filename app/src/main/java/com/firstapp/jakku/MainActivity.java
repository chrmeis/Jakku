package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firstapp.jakku.databinding.ActivityMainBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Date;
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

    /* Varning från commit
    Warning: Do not place Android context classes in static fields; this is a memory leak
     */
    static TextView info;
    static TextView Todo;
//    Button settings;
    //Weather weather;


    public static void updateInfo(String string){
        info.setText(string);
    }

    public static void updateShedule(){
//        System.out.println("\n\n!!!!!!!!!!!!!!!!!!updateShedule!!!!!!!!!!!!!\n\n");
       String str=Schema.make_schedule();
        System.out.println("str: "+str);
        Todo.setText(str);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info=(TextView) findViewById(R.id.textView2);
//        settings=(Button) findViewById(R.id.b_settings);




        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                SharedPreferences sharedPreferences = getSharedPreferences("locationshare", MODE_PRIVATE);

                String lat = sharedPreferences.getString("latitude","57.708870");
                String lon = sharedPreferences.getString("longitude","11.974560");
                Weather.saveCoords(lon, lat);
                String temp = null;
                try {
                    temp = Weather.currentTemp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String finalTemp = temp;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        updateInfo(finalTemp + " ℃");
                    }
                });
            }
        });


        //putting schema into homescreen

        Schema.get_study_amount();
        Schema.get_study_duration();
         Todo=(TextView) findViewById(R.id.textView3);
         Todo.setText(Schema.make_schedule());
        System.out.println("!!!!!!!!!!!!!!!!!!!!!after set text make shedule call!!!!!!!!!!!!!!!!!!!");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if(id == R.id.trainingspref){
            Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.notification_workout){
            Intent intent = new Intent(MainActivity.this,NotificationWorkout.class);
            startActivity(intent);
            finish();
            return true;

        }

        if(id == R.id.studypref){
            Intent intent = new Intent(MainActivity.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.home){
            //           Intent intent = new Intent(MainActivity.this, MainActivity.class);
            //           startActivity(intent);
            return true;
        }

        if(id == R.id.water_intake){
            Intent intent = new Intent(MainActivity.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.location_setter){
            Intent intent = new Intent(MainActivity.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}



