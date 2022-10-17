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
import android.view.View;
import android.widget.Button;
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

//    Button settings;
    //Weather weather;


    public static void updateInfo(String string){
        info.setText(string);
    }

    public static void updateShedule(){
        System.out.println("---Main updateShedule---");
       String str=Schema.schedulemaker(Schema.getToday());
        System.out.println("updateSchedule is: \n" + str);
        Todo.setText(str);
        System.out.println("---end Main updateShedule---");
    }

    public static void updateSchedule_by_button(String today){
        System.out.println("---Main updateSchedule_by_button---");
        String str=Schema.schedulemaker(today);
//        System.out.println("updateSchedule is: \n"+str);
        Todo.setText(str);
        System.out.println("---end Main updateSchedule_by_button---");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("---Main onCreate---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mon = findViewById(R.id.b_monday);
        tue = findViewById(R.id.b_tuesday);
        wed = findViewById(R.id.b_wednesday);
        thu = findViewById(R.id.b_thursday);
        fri = findViewById(R.id.b_friday);
        sat = findViewById(R.id.b_saturday);
        sun = findViewById(R.id.b_sunday);

        info=(TextView) findViewById(R.id.textView2);
//        settings=(Button) findViewById(R.id.b_settings);

        SharedPreferences SP_Study = getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        int s_frequency = SP_Study.getInt(STUDY_FREQUENCY, 4);
        int s_duration = SP_Study.getInt(STUDY_DURATION, 1);
        Schema.set_studypref(s_frequency,s_duration);

        System.out.println("\n\nshared study in main:");
        System.out.println("s_frequency: "+s_frequency);
        System.out.println("s_duration: "+s_duration+"\n\n");


        SharedPreferences SP_train = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        int t_frequency = SP_train.getInt(T_FREQUENCY, 1);
        int t_duration = SP_train.getInt(T_DURATION, 1);

        Schema.set_trainingpref(t_frequency, t_duration);

        System.out.println("\n\nshared training in main:");
        System.out.println("t_frequency: "+t_frequency);
        System.out.println("t_duration: "+t_duration+"\n\n");


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

                try {
                    System.out.println("where_to_train1: " +where_to_train);
                    where_to_train = Weather.rain18();
                    System.out.println("where_to_train2: " +where_to_train);
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

        Todo=(TextView) findViewById(R.id.textView3);
        Todo.setText(Schema.schedulemaker(Schema.getToday()));
        System.out.println("---end main onCreate---");
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



