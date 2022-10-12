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

public class TrainingActivity extends AppCompatActivity {
    private Button back;

    private SeekBar sb_Training_HowOften;
    private SeekBar sb_Training_HowLong;

    private TextView c_freqQ;
    private TextView c_durQ;
    private TextView c_freq_ans;
    private TextView c_dur_ans;


    private Button saveButtonTrain;

    public static final String SHARED_TPREFS = "sharedPref";
    private static final String T_HOW_OFTEN = "t_how_often";
    private static final String T_HOW_LONG = "t_how_long";


    private int t_frequency;
    private int t_duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        sb_Training_HowOften = findViewById(R.id.sb_Training_how_often);
        sb_Training_HowLong = findViewById(R.id.sb_Training_How_long);
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

        sb_Training_HowOften.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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



        sb_Training_HowLong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        editor.putInt(T_HOW_OFTEN, sb_Training_HowOften.getProgress());
        editor.putInt(T_HOW_LONG, (sb_Training_HowLong.getProgress()));

        editor.apply();

        Schema.set_trainingpref(sb_Training_HowOften.getProgress(), (sb_Training_HowLong.getProgress()));
        MainActivity.updateShedule();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPrefTrain() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        t_frequency = sharedPreferences.getInt(T_HOW_OFTEN, 1);
        t_duration = sharedPreferences.getInt(T_HOW_LONG, 1);
        System.out.println("\n\nLoad t_duration: "+t_duration);

    }

    public void updateTrainViews(){
        sb_Training_HowOften.setProgress(t_frequency);
        sb_Training_HowLong.setProgress(t_duration);

    }
}