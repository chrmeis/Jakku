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

import java.time.DayOfWeek;

public class StudyActivity extends AppCompatActivity {

    private Button saveButton;

    private SeekBar sb_total;
    private SeekBar sb_session;
    private TextView ansTotal;
    private TextView ansSession;

    public static final String STUDY_TOTAL = "sb_total";
    public static final String STUDY_SESSIONS = "sb_sessions";
    public static final String SHARED_SPREFS = "sharedPref";

    private int total;
    private int session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study);
        saveButton = (Button) findViewById(R.id.b_save_study);
        sb_total = (SeekBar) findViewById(R.id.sb_total);
        sb_session = (SeekBar) findViewById(R.id.sb_session);
        ansTotal = (TextView) findViewById(R.id.tv_ans_total);
        ansSession = (TextView) findViewById(R.id.tv_ans_session);


        sb_total.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean Studentinput) {
                String str;
                str= String.valueOf(progress) + " times";
                ansTotal.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb_session.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean Studentinput) {
                String str;
                str= String.valueOf(progress*15) + " minutes";
                ansSession.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePref();
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
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(StudyActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePref(){
        SharedPreferences sharedPreferences =getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(STUDY_TOTAL, sb_total.getProgress());
        editor.putInt(STUDY_SESSIONS, sb_session.getProgress());

        editor.apply();

        Schema.set_studypref(sb_total.getProgress(), sb_session.getProgress());
        MainActivity.updateShedule();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPref() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_SPREFS, MODE_PRIVATE);
        total = sharedPreferences.getInt(STUDY_TOTAL, 5);
        session = sharedPreferences.getInt(STUDY_SESSIONS, 1);
    }

    public void updateStudyViews(){
        sb_total.setProgress(total);
        sb_session.setProgress(session);
        MainActivity.updateShedule();
    }
}