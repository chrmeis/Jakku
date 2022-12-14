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
import android.widget.ImageView;
import android.widget.TextView;

/**
 *  Pass1 is a workout pass with different exercises and the ability to swap between them.
 * @author Erik Gustavsson, Oliver Brottare
 */
public class Pass1 extends AppCompatActivity {


    private Button nextButton;
    private Button previousButton;
    private ImageView trainingImg;
    private TextView trainingText;


    /**
     * onCreate sets current view to matching activity and initializes components from the activity.
     * @param savedInstanceState - Is a reference to a Bundle object that stores data. Activity can use this
     * data to restore itself to a previous state in some situations.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass1);

        previousButton = (Button) findViewById(R.id.prevButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        trainingImg = (ImageView) findViewById(R.id.trainingImg);
        trainingText = (TextView) findViewById(R.id.pass_instruction);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainingImg.setImageResource(R.drawable.pushup);
                trainingText.setText("Do 10 pushups x 3 \n Rest 30 sec inbetween");
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainingImg.setImageResource(R.drawable.situp);
                trainingText.setText("Do 10 situps x 3 \n Rest 30 sec.");
            }
        });


    }
    /**
     * onCreateOptionsMenu sets the menu bar according to settingsmenu.xml file
     * @param menu
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
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if (id == R.id.trainingspref) {
            Intent intent = new Intent(Pass1.this,TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.studypref) {
            Intent intent = new Intent(Pass1.this, StudyActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(Pass1.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.water_intake) {
            Intent intent = new Intent(Pass1.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.location_setter){
            Intent intent = new Intent(Pass1.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id ==R.id.water_notification){
            Intent intent = new Intent(Pass1.this, Notifications.class);
            startActivity(intent);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}