package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {
    Button back;
    Switch cardiosettings;
    Switch strengthsettings;
    SeekBar c_frequency;
    SeekBar c_duration;
    SeekBar s_frequency;
    SeekBar s_duration;
    TextView c_freqQ;
    TextView c_durQ;
    TextView c_freq_ans;
    TextView c_dur_ans;
    TextView s_freqQ;
    TextView s_durQ;
    TextView s_freq_ans;
    TextView s_dur_ans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        cardiosettings = findViewById(R.id.s_Cardio);
        c_frequency = findViewById(R.id.sb_cfreq);
        c_duration = findViewById(R.id.sb_cdur);
        c_freqQ = findViewById(R.id.tv_cfrequence);
        c_durQ = findViewById(R.id.tv_cduration);
        c_freq_ans = findViewById(R.id.tv_cfreq_ans2);
        c_dur_ans = findViewById(R.id.tv_cdur_ans2);

        s_frequency = findViewById(R.id.sb_sfreq);
        s_duration = findViewById(R.id.sb_sdur);
        s_freqQ = findViewById(R.id.tv_sfrequence);
        s_durQ = findViewById(R.id.tv_sduration);
        s_freq_ans = findViewById(R.id.tv_sfreq_ans);
        s_dur_ans = findViewById(R.id.tv_sdur_ans);

        cardiosettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (!isChecked) {
                    // The toggle is disabled
                    System.out.println("Cardio toggle disabled");
                    c_frequency.setVisibility(View.INVISIBLE);
                    c_freqQ.setVisibility(View.INVISIBLE);
                    c_freq_ans.setVisibility(View.INVISIBLE);
                    c_duration.setVisibility(View.INVISIBLE);
                    c_durQ.setVisibility(View.INVISIBLE);
                    c_dur_ans.setVisibility(View.INVISIBLE);
                } else {
                    // The toggle is enabled
                    System.out.println("Cardio toggle enabled");
                    c_frequency.setVisibility(View.VISIBLE);
                    c_freqQ.setVisibility(View.VISIBLE);
                    c_freq_ans.setVisibility(View.VISIBLE);
                    c_duration.setVisibility(View.VISIBLE);
                    c_durQ.setVisibility(View.VISIBLE);
                    c_dur_ans.setVisibility(View.VISIBLE);
                }
            }
        });


        strengthsettings = findViewById(R.id.s_Strength);
        strengthsettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    System.out.println("Strength toggle enabled");
                    s_frequency.setVisibility(View.VISIBLE);
                    s_freqQ.setVisibility(View.VISIBLE);
                    s_freq_ans.setVisibility(View.VISIBLE);
                    s_duration.setVisibility(View.VISIBLE);
                    s_durQ.setVisibility(View.VISIBLE);
                    s_dur_ans.setVisibility(View.VISIBLE);
                } else {
                    // The toggle is disabled
                    System.out.println("Strength toggle disabled");
                    s_frequency.setVisibility(View.INVISIBLE);
                    s_freqQ.setVisibility(View.INVISIBLE);
                    s_freq_ans.setVisibility(View.INVISIBLE);
                    s_duration.setVisibility(View.INVISIBLE);
                    s_durQ.setVisibility(View.INVISIBLE);
                    s_dur_ans.setVisibility(View.INVISIBLE);
                }
            }
        });
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
            Intent intent = new Intent (SettingsActivity.this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.studypref){
            Intent intent = new Intent(SettingsActivity.this, StudyActivity.class);
            startActivity(intent);
            return true;
        }

        if(id == R.id.home){
            Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}