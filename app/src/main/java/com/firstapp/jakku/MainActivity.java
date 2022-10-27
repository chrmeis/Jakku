package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Liam Mattsson
 */

public class MainActivity extends AppCompatActivity {

    private TextView travelTextView;
    private TextView planner;
    static TextView Todo;
    private Button mon;
    private Button tue;
    private Button wed;
    private Button thu;
    private Button fri;
    private Button sat;
    private Button sun;

    static String where_to_train;

    public static final String STUDY_FREQUENCY = "sb_sfrequency";
    public static final String STUDY_DURATION = "sb_sduration";
    public static final String SHARED_SPREFS = "sharedPref";

    public static final String SHARED_TPREFS = "sharedPref";
    private static final String T_FREQUENCY = "t_how_often";
    private static final String T_DURATION = "t_how_long";

    private TextView weatherText;
    private ImageView weatherSymbol;
    private String temp;
    private String tempFinal;
    private int finalRain;

    public void updateInfo(String string){
        weatherText.setText(string);
    }


        public void updateTravelPlans() {
        if (getRain() > 0 && Double.parseDouble(getTemp()) < 10.0) {
            travelTextView.setText("It's recommended to take the bus today, be sure to bring a jacket!");
        } else if (getRain() > 0) {
            travelTextView.setText("It's recommended to take the bus today!");
        }
        if (getRain() == 0 && Double.parseDouble(getTemp()) < 10.0) {
            travelTextView.setText("It's recommended to take a walk or go biking today, be sure to bring a jacket");
        } else if (getRain() == 0) {
            travelTextView.setText("It's recommended to take a walk or go biking today!");
        } else {
            travelTextView.setText("Error, could not make travel plans");
        }
    }



    public static void updateShedule(){
       String str=Schema.schedulemaker(Schema.getToday());
        Todo.setText(str);
    }

    public static void updateSchedule_by_button(String today){
        String str=Schema.schedulemaker(today);
        Todo.setText(str);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mon = findViewById(R.id.b_monday);
        tue = findViewById(R.id.b_tuesday);
        wed = findViewById(R.id.b_wednesday);
        thu = findViewById(R.id.b_thursday);
        fri = findViewById(R.id.b_friday);
        sat = findViewById(R.id.b_saturday);
        sun = findViewById(R.id.b_sunday);

        weatherText = findViewById(R.id.textView2);

        SharedPreferences SP_Study = getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        int s_frequency = SP_Study.getInt(STUDY_FREQUENCY, 4);
        int s_duration = SP_Study.getInt(STUDY_DURATION, 1);
        Schema.set_studypref(s_frequency,s_duration);


        SharedPreferences SP_train = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        int t_frequency = SP_train.getInt(T_FREQUENCY, 1);
        int t_duration = SP_train.getInt(T_DURATION, 1);
        weatherSymbol = (ImageView) findViewById(R.id.imageView2);

        travelTextView = (TextView) findViewById(R.id.travelText);
        weatherText=(TextView) findViewById(R.id.textView2);
        weatherSymbol = (ImageView) findViewById(R.id.imageView2);
        planner = (TextView) findViewById(R.id.textView3);

       // executor.execute(new Runnable() {
        Schema.set_trainingpref(t_frequency, t_duration);

        weatherSymbol.setImageResource(R.drawable.rain);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("locationshare", MODE_PRIVATE);

                String lat = sharedPreferences.getString("latitude", "57.708870");
                String lon = sharedPreferences.getString("longitude", "11.974560");
                Weather.saveCoords(lon, lat);
                temp = null;
                try {
                    temp = Weather.currentTemp();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                int rain = 0;
                try {
                    rain = Weather.currentRain();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    where_to_train = Weather.rain18();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                tempFinal = temp;
                finalRain = rain;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateInfo(tempFinal + " ℃");
                        if (finalRain == 0) {
                            weatherSymbol.setImageResource(R.drawable.sunshine1);
                        } else {
                            weatherSymbol.setImageResource(R.drawable.rain);
                        }
                        if (getRain() > 0 && Double.parseDouble(getTemp()) < 10.0) {
                            travelTextView.setText("It's recommended to take the bus today, be sure to bring a jacket!");
                        } else if (getRain() > 0) {
                            travelTextView.setText("It's recommended to take the bus today!");
                        }
                        if (getRain() == 0 && Double.parseDouble(getTemp()) < 10.0) {
                            travelTextView.setText("It's recommended to take a walk or go biking today, be sure to bring a jacket");
                        } else if (getRain() == 0) {
                            travelTextView.setText("It's recommended to take a walk or go biking today!");
                        }
                    }
                });
            }
        });










        mon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(mon.getText().toString());
            }
        });
        tue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(tue.getText().toString());
            }
        });
        wed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(wed.getText().toString());
            }
        });
        thu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(thu.getText().toString());
            }
        });
        fri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(fri.getText().toString());
            }
        });
        sat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(sat.getText().toString());
            }
        });
        sun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSchedule_by_button(sun.getText().toString());
            }
        });

        //putting schema into homescreen
        Schema.set_weekday();
        Schema.set_studypref(s_frequency,s_duration);
        Schema.set_trainingpref(t_frequency,t_duration);

        Todo= findViewById(R.id.textView3);
        Todo.setText(Schema.schedulemaker(Schema.getToday()));
    }

    public String getTemp(){
        return tempFinal;
    }

    public int getRain(){
        return finalRain;
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

        if(id == R.id.home){
            return true;
        }
        if(id == R.id.trainingspref){
            Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
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
        if(id == R.id.water_intake){
            Intent intent = new Intent(MainActivity.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id == R.id.exercise){
            Intent intent = new Intent(MainActivity.this, Exercise.class);
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
        if(id == R.id.location_setter){
            Intent intent = new Intent(MainActivity.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}



