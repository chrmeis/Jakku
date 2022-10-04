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
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingActivity extends AppCompatActivity {
    private Button back;

    private SeekBar sb_c_frequency;
    private SeekBar sb_c_duration;

    private TextView c_freqQ;
    private TextView c_durQ;
    private TextView c_freq_ans;
    private TextView c_dur_ans;


    private Button saveButtonTrain;

    public static final String SHARED_TPREFS = "sharedPref";
    private static final String  C_FREQ = "c-freq";
    private static final String C_DUR = "c-dur";
    private static final String S_FREQ = "s_freq";
    private static final String S_DUR = "s-dur";
    private static final String C_SWITCH = "c-switch";
    private static final String S_SWITCH = "s-switch";

    private int cFreq;
    private int cDur;
    private int sFreq;
    private int sDur;
    private boolean cSwitch;
    private boolean sSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        sb_c_frequency = findViewById(R.id.sb_cfreq);
        sb_c_duration = findViewById(R.id.sb_cdur);
        c_freqQ = findViewById(R.id.tv_cfrequence);
        c_durQ = findViewById(R.id.tv_cduration);
        c_freq_ans = findViewById(R.id.tv_cfreq_ans2);
        c_dur_ans = findViewById(R.id.tv_cdur_ans2);


        saveButtonTrain = (Button) findViewById(R.id.b_save_training);

        saveButtonTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             savePrefTrain();
         }
        });

        sb_c_frequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String str;
                str= String.valueOf(progress) + " times";
                c_freq_ans.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        sb_c_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                String str;
                str= String.valueOf(progress*30) + " minutes";
                c_dur_ans.setText(str);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });



        loadPrefTrain();
        updateTrainViews();
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
//            Intent intent = new Intent (SettingsActivity.this, SettingsActivity.class);
//            startActivity(intent);
            return true;
        }

        if(id == R.id.studypref){
            Intent intent = new Intent(TrainingActivity.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.home){
            Intent intent = new Intent(TrainingActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePrefTrain(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(C_FREQ, sb_c_frequency.getProgress());
        editor.putInt(C_DUR, sb_c_duration.getProgress());

        editor.apply();

        Schema.set_trainingpref(sb_c_frequency.getProgress(), sb_c_duration.getProgress());
        MainActivity.updateShedule();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPrefTrain() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        cFreq = sharedPreferences.getInt(C_FREQ, 1);
        cDur = sharedPreferences.getInt(C_DUR, 1);
    }

    public void updateTrainViews(){
        sb_c_frequency.setProgress(cFreq);
        sb_c_duration.setProgress(cDur);

    }
}