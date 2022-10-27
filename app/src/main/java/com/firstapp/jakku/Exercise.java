package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.WorkSource;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Exercise is a simple class working as a page for different workouts.
 * @author Erik Gustavsson, Oliver Brottare
 */
public class Exercise extends AppCompatActivity {

    private Button passButton;

    /**
     * onCreate sets current view to matching activity and initializes components from the activity.
     * @param savedInstanceState - Is a reference to a Bundle object that stores data. Activity can use this
     * data to restore itself to a previous state in some situations.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        passButton = (Button) findViewById(R.id.passbutton);

        passButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Exercise.this, Pass1.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * onCreateOptionsMenu sets the menu bar according to settingsmenu.xml file
     * @param menu
     * @author Liam Mattsson
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Home");
        menu.add("Hydrate Notifications");
        menu.add("Study Preferences");
        menu.add("Training Preferences");
        menu.add("Set Location");
        menu.add("Workout Settings");
        menu.add("Water Intake");
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
            Intent i = new Intent(Exercise.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Hydrate Notifications")){
            Intent i = new Intent(Exercise.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Study Preferences")){
            Intent i = new Intent(Exercise.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Training Preferences")){
            Intent i = new Intent(Exercise.this, TrainingActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Set Location")){
            Intent i = new Intent(Exercise.this, LocationSetter.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Workout Settings")){
            Intent i = new Intent(Exercise.this, NotificationWorkout.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Water Intake")){
            Intent i = new Intent(Exercise.this, WaterIntake.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}