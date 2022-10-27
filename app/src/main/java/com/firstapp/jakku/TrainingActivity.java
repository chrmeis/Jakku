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

    private SeekBar sb_training_frequency;
    private SeekBar sb_training_duration;
    private TextView t_freq_ans;
    private TextView t_dur_ans;


    private Button saveButtonTrain;

    public static final String SHARED_TPREFS = "sharedPref";
    private static final String T_FREQUENCY = "t_how_often";
    private static final String T_DURATION = "t_how_long";


    private int t_frequency;
    private int t_duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        sb_training_frequency = findViewById(R.id.sb_training_frequency);
        sb_training_duration = findViewById(R.id.sb_training_duration);
        t_freq_ans = findViewById(R.id.tv_ans_train_freq);
        t_dur_ans = findViewById(R.id.tv_ans_train_dur);


        saveButtonTrain = findViewById(R.id.b_save_training);
        saveButtonTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             savePrefTrain();
         }
        });

        sb_training_frequency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                t_freq_ans.setText(progress + " times");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });


        sb_training_duration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                t_dur_ans.setText(progress*30 + " minutes");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        loadPrefTrain();
        updateTrainViews();
    }

    /**
     *
     * @param menu the menu
     * @return true if the menu was created
     * @author Liam Mattsson
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Home");
        menu.add("Hydrate Notifications");
        menu.add("Study Preferences");
        menu.add("Exercises");
        menu.add("Set Location");
        menu.add("Workout Settings");
        menu.add("Water Intake");
        return super.onCreateOptionsMenu(menu);
    }

    /**
     *
     * @param item an item in the menu
     * @return true if that item was selected
     * @author Liam Mattsson
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getTitle().equals("Home")){
            Intent i = new Intent(TrainingActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Hydrate Notifications")){
            Intent i = new Intent(TrainingActivity.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Study Preferences")){
            Intent i = new Intent(TrainingActivity.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Exercises")){
            Intent i = new Intent(TrainingActivity.this, Exercise.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Set Location")){
            Intent i = new Intent(TrainingActivity.this, LocationSetter.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Workout Settings")){
            Intent i = new Intent(TrainingActivity.this, NotificationWorkout.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Water Intake")){
            Intent i = new Intent(TrainingActivity.this, WaterIntake.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void savePrefTrain(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(T_FREQUENCY, sb_training_frequency.getProgress());
        editor.putInt(T_DURATION, (sb_training_duration.getProgress()));

        editor.apply();

        Schema.set_trainingpref(sb_training_frequency.getProgress(), (sb_training_duration.getProgress()));
        MainActivity.updateShedule();

        Toast.makeText(this, "Data saved", Toast.LENGTH_SHORT).show();
    }

    public void loadPrefTrain() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_TPREFS, MODE_PRIVATE);
        t_frequency = sharedPreferences.getInt(T_FREQUENCY, 1);
        t_duration = sharedPreferences.getInt(T_DURATION, 1);
    }

    public void updateTrainViews(){
        sb_training_frequency.setProgress(t_frequency);
        sb_training_duration.setProgress(t_duration);
        t_freq_ans.setText((sb_training_frequency.getProgress()) + " times");
        t_dur_ans.setText((sb_training_duration.getProgress()*30) + " minutes");

    }
}