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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }



    /**
     * onOptionsItemSelected switches to other activities based on what user choose in menubar.
     * @param item
     * @author Liam Mattsson
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if (id == R.id.trainingspref) {
            Intent intent = new Intent(Exercise.this, TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.studypref) {
            Intent intent = new Intent(Exercise.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(Exercise.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.water_intake) {
            Intent intent = new Intent(Exercise.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.exercise) {
            return true;
        }

        if (id == R.id.location_setter) {
            Intent intent = new Intent(Exercise.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id ==R.id.water_notification){
            Intent intent = new Intent(Exercise.this, Notifications.class);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}