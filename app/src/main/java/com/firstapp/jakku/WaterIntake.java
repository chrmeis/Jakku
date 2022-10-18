package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;
import android.content.SharedPreferences;

/**
 * WaterIntake - simple class to enable user to track how many glasses of
 * water that has been consumed during the day.
 *
 * @author Erik Gustavsson
 */
public class WaterIntake extends AppCompatActivity {

    private SeekBar sb_water;
    private TextView waterTotal;
    public static final String WATERGLASSES = "sb_watertotal";
    public static final String SHARED_SPREFS = "sharedPref";

    private int total;

    /**
     * onCreate sets current view to matching activity and initializes components from the activity.
     * @param savedInstanceState - Is a reference to a Bundle object that stores data. Activity can use this
     * data to restore itself to a previous state in some situations.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);
        sb_water = (SeekBar) findViewById(R.id.sb_water);
        waterTotal = (TextView) findViewById(R.id.waterTotal);

        sb_water.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            /**
             * Method to set up what happens when user change the bar.
             * @param seekBar - The seekbar whose progress has changed
             * @param progress - The current progress level
             * @param fromUser - boolean true if progress change was initialized by user
             */
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String str;
                str= String.valueOf(progress) + " Glasses (2dl/glass)";
                waterTotal.setText(str);
                savePref();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        loadPref();
        updateStudyViews();
    }

    /**
     * onCreateOptionsMenu sets the menu bar according to settingsmenu.xml file
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Home");
        menu.add("Hydrate Notifications");
        menu.add("Study Preferences");
        menu.add("Training Preferences");
        menu.add("Exercises");
        menu.add("Location Settings");
        menu.add("Workout Notifications");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * onOptionsItemSelected switches to other activities based on what user choose in menubar.
     * @param item
     * @author Liam Mattsson
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Home")){
            Intent i = new Intent(WaterIntake.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Hydrate Notifications")){
            Intent i = new Intent(WaterIntake.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Study Preferences")){
            Intent i = new Intent(WaterIntake.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Training Preferences")){
            Intent i = new Intent(WaterIntake.this, TrainingActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Exercises")){
            Intent i = new Intent(WaterIntake.this, Exercise.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Location Settings")){
            Intent i = new Intent(WaterIntake.this, LocationSetter.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Workout Notifications")){
            Intent i = new Intent(WaterIntake.this, NotificationWorkout.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePref(){
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(WATERGLASSES, sb_water.getProgress());

        editor.apply();

        //Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
         total = sharedPreferences.getInt(WATERGLASSES, 1);
    }

    public void updateStudyViews(){
        sb_water.setProgress(total);
    }





}