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

public class WaterIntake extends AppCompatActivity {

    private SeekBar sb_water;
    private TextView waterTotal;
    public static final String WATERGLASSES = "sb_watertotal";
    public static final String SHARED_SPREFS = "sharedPref";

    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_intake);
        sb_water = (SeekBar) findViewById(R.id.sb_water);
        waterTotal = (TextView) findViewById(R.id.waterTotal);

        sb_water.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean Studentinput) {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if (id == R.id.trainingspref) {
            Intent intent = new Intent(WaterIntake.this,TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.studypref) {
            Intent intent = new Intent(WaterIntake.this, StudyActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(WaterIntake.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.water_intake) {
            //   Intent intent = new Intent(WaterIntake.this, WaterIntake.class);
            //   startActivity(intent);
            //   finish();
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