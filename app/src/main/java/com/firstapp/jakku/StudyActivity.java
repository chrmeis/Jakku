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
import android.widget.Toast;

public class StudyActivity extends AppCompatActivity {

    private Button saveButton;

    private SeekBar sb_total;
    private SeekBar sb_session;

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
        menu.add("Home");
        menu.add("Notifications");
        menu.add("Training Preferences");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getTitle().equals("Home")){
            Intent i = new Intent(StudyActivity.this,MainActivity.class);
            startActivity(i);
            finish();
            return true;

        } else if(item.getTitle().equals("Notifications")){
            Intent i = new Intent(StudyActivity.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Training Preferences")){
            Intent i = new Intent(StudyActivity.this, practice_preferences.class);
            startActivity(i);
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

    }
}