package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import org.json.JSONException;

public class StudyActivity extends AppCompatActivity {

    private Button saveButton;

    private SeekBar sb_study_frequency;
    private SeekBar sb_study_duration;
    private TextView ans_study_frequency;
    private TextView ans_study_duration;
    SharedPreferences sharedPreferences;

    public static final String STUDY_FREQUENCY = "sb_sfrequency";
    public static final String STUDY_DURATION = "sb_sduration";
    public static final String SHARED_SPREFS = "sharedPref";

    private static int s_frequency;
    private static int s_duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        saveButton = findViewById(R.id.b_save_study);
        sb_study_frequency = findViewById(R.id.sb_study_frequency);
        sb_study_duration = findViewById(R.id.sb_study_duration);
        ans_study_frequency = findViewById(R.id.tv_ans_study_frequency);
        ans_study_duration = findViewById(R.id.tv_ans_study_duration);


        sb_study_frequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean Studentinput) {
                ans_study_frequency.setText(progress + " times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb_study_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean Studentinput) {
                ans_study_duration.setText(progress*15 + " minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    savePref();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
            Intent intent = new Intent(StudyActivity.this,TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.studypref) {
//            Intent intent = new Intent(StudyActivity.this, StudyActivity.class);
//            startActivity(intent);
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(StudyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.water_intake) {
               Intent intent = new Intent(StudyActivity.this, WaterIntake.class);
               startActivity(intent);
               finish();
            return true;
        }

        if(id == R.id.exercise){
            Intent intent = new Intent(StudyActivity.this, Exercise.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePref() throws JSONException {
        sharedPreferences =getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(STUDY_FREQUENCY, sb_study_frequency.getProgress());
        editor.putInt(STUDY_DURATION, sb_study_duration.getProgress());

        editor.apply();

        Schema.set_studypref(sb_study_frequency.getProgress(), sb_study_duration.getProgress());
        MainActivity.updateShedule();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        s_frequency = sharedPreferences.getInt(STUDY_FREQUENCY, 4);
        s_duration = sharedPreferences.getInt(STUDY_DURATION, 1);

    }

    public void updateStudyViews(){
        sb_study_frequency.setProgress(s_frequency);
        sb_study_duration.setProgress(s_duration);
        ans_study_frequency.setText(sb_study_frequency.getProgress() + " times");
        ans_study_duration.setText(sb_study_duration.getProgress()*15 + " minutes");

        MainActivity.updateShedule();
    }

}