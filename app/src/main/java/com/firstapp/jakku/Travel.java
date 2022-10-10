package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class Travel extends AppCompatActivity {

    private TextView info;
    private MainActivity main;
    private String tempString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel);

        main = new MainActivity();
        tempString = main.getTemp().substring(0,4);
        info = (TextView) findViewById(R.id.travelInfo);
        if(Integer.parseInt(tempString) < 10){
            info.setText("It's cold outside, we recommend bringing a jacket!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Home");
        menu.add("Notifications");
        menu.add("Study Preferences");
        menu.add("Training Preferences");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem menuItem){
        if(menuItem.getTitle().equals("Home")){
            Intent i = new Intent(Travel.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(menuItem.getTitle().equals("Notifications")){
            Intent i = new Intent(Travel.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(menuItem.getTitle().equals("Study Preferences")){
            Intent i = new Intent(Travel.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(menuItem.getTitle().equals("Training Preferences")){
            Intent i = new Intent(Travel.this, practice_preferences.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }
}