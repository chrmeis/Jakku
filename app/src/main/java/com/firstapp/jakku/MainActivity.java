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
    Button tue;
    Button wed;
    Button thu;
    Button fri;
    Button sat;
    Button sun;

//    Button settings;
    //Weather weather;


    public static void updateInfo(String string){
        info.setText(string);
    }

    public static void updateShedule(){
        System.out.println("---Main updateShedule---");
       String str=Schema.make_schedule();
        System.out.println("updateSchedule is: \n"+str);
        Todo.setText(str);
        System.out.println("---end Main updateShedule---");
    }

    public static void updateSchedule_by_button(String today){
        System.out.println("---Main updateSchedule_by_button---");
        String str=Schema.make_schedule_for_any_day(today);
        System.out.println("updateSchedule is: \n"+str);
        Todo.setText(str);
        System.out.println("---end Main updateSchedule_by_button---");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("---Main onCreate---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mon= (Button)findViewById(R.id.b_monday);
        tue = (Button)findViewById(R.id.b_tuesday);
        wed = (Button)findViewById(R.id.b_wednesday);
        thu = (Button)findViewById(R.id.b_thursday);
        fri = (Button)findViewById(R.id.b_friday);
        sat = (Button)findViewById(R.id.b_saturday);
        sun = (Button)findViewById(R.id.b_sunday);

        info=(TextView) findViewById(R.id.textView2);
//        settings=(Button) findViewById(R.id.b_settings);




        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
        //JSONArray jsonArray = new JSONArray();
        //final String[] string = {"fail"};
        //final String[] extra = {"Slow"};
        TalkToServer varName = new TalkToServer(); //pass parameters if you need to the constructor
        varName.execute();
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Test1");
                info.setText("fail");
                //weather = new Weather();
                //JSONArray jsonArray  = weather.getJsonArray();
                //String  string = weather.temperature();
                // After getting the result
                runOnUiThread(new Runnable() {
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
                        // Post the result to the main thread
                        System.out.println("Test2");
                       // info.setText(string);
                        //extra[0] = string;
                        //UI Thread work here
                        updateInfo(finalTemp + " ℃");
                    }
                });
            }
        }).start();
        */
        /*try {
            //String string = ((JSONArray) ((JSONObject) jsonArray.get(0)).get("parameters"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //info.setText(extra[0]);

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
//        Schema.get_study_amount();
//        Schema.get_study_duration();

        SharedPreferences theOldOnes = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        int study_total= theOldOnes.getInt("total", 0);
        int study_session= theOldOnes.getInt("session", 0);
        System.out.println("---sp hämtat i main från study---");
        System.out.println("study_total: "+study_total);
        System.out.println("study_session: "+study_session);
        System.out.println("---the end---");

         Todo=(TextView) findViewById(R.id.textView3);
         Todo.setText(Schema.make_schedule());
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



