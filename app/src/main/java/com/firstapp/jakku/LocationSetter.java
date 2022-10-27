package com.firstapp.jakku;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.firstapp.jakku.databinding.ActivityLocationSetBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class LocationSetter extends AppCompatActivity {


    String[] results;

    private ActivityLocationSetBinding binding;

    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.locationSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setLocation();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

/*    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if(id == R.id.trainingspref){
            Intent intent = new Intent(LocationSetter.this, TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.notification_workout){
            Intent intent = new Intent(LocationSetter.this,NotificationWorkout.class);
            startActivity(intent);
            finish();
            return true;

        }

        if(id == R.id.studypref){
            Intent intent = new Intent(LocationSetter.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.home){
            Intent intent = new Intent(LocationSetter.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.water_intake){
            Intent intent = new Intent(LocationSetter.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.location_setter){
            
            Intent intent = new Intent(LocationSetter.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;


        }

        return super.onOptionsItemSelected(item);
    }
    */

    //takes a while to run, is done when toast appears
    //if toast is "Failed to find location", double-check if location is set in emulator
    public void setLocation() {

        if (ActivityCompat.checkSelfPermission(LocationSetter.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationSetter.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //Ask for permissions
            ActivityCompat.requestPermissions(LocationSetter.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        fusedLocationClient.getCurrentLocation(100, null)
                .addOnSuccessListener(LocationSetter.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // If null, check emulator settings
                        if (location != null) {

                            String longitude = String.valueOf(location.getLongitude());
                            String latitude = String.valueOf(location.getLatitude());
                            String[] res = {latitude,longitude};
                            savePref(res);
                            String loc = "latitude set to: " + res[0] + "longitude set to: " + res[1];
                            Toast.makeText(LocationSetter.this, loc, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LocationSetter.this, "Failed to find location", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
    //Saves current location
    private void savePref(String[] inData){
        SharedPreferences sharedPreferences = getSharedPreferences("locationshare", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("latitude",inData[0]);
        editor.putString("longitude",inData[1]);
        editor.apply();
    }
    //Returns saved location as an array, testing purposes
    public String[] getLocation(){
        SharedPreferences sharedPreferences = getSharedPreferences("locationshare", MODE_PRIVATE);

        String lat = sharedPreferences.getString("latitude","");
        String lon = sharedPreferences.getString("longitude","");

        String[] res = {lat,lon};

        return res;
    }

/*    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        menu.add("Home");
        menu.add("Hydrate Notifications");
        menu.add("Study Preferences");
        menu.add("Training Preferences");
        menu.add("Exercises");
        menu.add("Workout Settings");
        menu.add("Water Intake");
        return super.onCreateOptionsMenu(menu);
    }

 */

    /**
     *
     * @param item the item in the menu
     * @return true if the item was selected in the menu
     * @author Liam Mattsson
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getTitle().equals("Home")){
            Intent i = new Intent(LocationSetter.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Hydrate Notifications")){
            Intent i = new Intent(LocationSetter.this, Notifications.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Study Preferences")){
            Intent i = new Intent(LocationSetter.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Training Preferences")){
            Intent i = new Intent(LocationSetter.this, TrainingActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Exercises")){
            Intent i = new Intent(LocationSetter.this, Exercise.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Workout Settings")){
            Intent i = new Intent(LocationSetter.this, NotificationWorkout.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Water Intake")){
            Intent i = new Intent(LocationSetter.this, WaterIntake.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
