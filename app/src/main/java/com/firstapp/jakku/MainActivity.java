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
 * MainActivity shows the home-screen.
 * It gets the weather from an API and displays the current weather,
 * shows the schedule created by Schema that is closable for every day of the week by button and
 * displays travel recommendations based on weather.
 * @author liam Mattsson, Ludvig Andersson, Erik Gustavsson, Oliver Brottare, Chrisitna Meisoll
 */
public class MainActivity extends AppCompatActivity {

    private TextView travelTextView;
    private TextView planner;
    static TextView ScheduleToday;
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

    TextView weatherText;
    ImageView weatherSymbol;
    private String temp;
    private String tempFinal;
    private int finalRain;


    /**
     * Updates the temperature on the main page
     * @param string The new text
     */
    public void updateInfo(String string){
        weatherText.setText(string);
    }

    /**
     * Shows a small travel recommendation text based on the weather.
     */
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


    /**
     * Updates the schedule the todays with data according to today's date.
     */
    public static void updateShedule(){
       String str=Schema.schedulemaker(Schema.getToday());
        ScheduleToday.setText(str);
    }

    /**
     * Updates the schedule the ScheduleToday with data according to the chosen date
     * @param today The chosen day.
     */
    public static void updateSchedule_by_button(String today){
        String str=Schema.schedulemaker(today);
        ScheduleToday.setText(str);
    }

    /**
     * Is run on creation, runs an additional thread that checks the weather and sets up action listeners
     * @param savedInstanceState The shared instance of the class
     */
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

        //Runs and additional branch that checks the weather and then does things based on the weather.
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
                        updateInfo(tempFinal + " ???");
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

        //sets up OnClickListeners for the days buttons.
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

        ScheduleToday= findViewById(R.id.textView3);
        ScheduleToday.setText(Schema.schedulemaker(Schema.getToday()));
    }

    /**
     * Getter method for temperature from SMHI
     * @return the temperature from SMHI, will be null if the temperature hasn't been gathered from SMHI yet.
     */
    public String getTemp(){
        return tempFinal;
    }

    /**
     * Getter method for rain from SMHI
     * @return the precipitation category from SMHI, will be null if the precipitation hasn't been gathered from SMHI yet.
     */
    public int getRain(){
        return finalRain;
    }

    /**
     * Inflates the menu on creation
     * @param menu The menu instance
     * @return true if success.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

    /**
     * This handles the menu buttons in the top right.
     * @param item The MenuItem instance that should be handled
     * @return true if successful.
     */
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
        if(id == R.id.location_setter){
            Intent intent = new Intent(MainActivity.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id ==R.id.water_notification){
            Intent intent = new Intent(MainActivity.this, Notifications.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}



