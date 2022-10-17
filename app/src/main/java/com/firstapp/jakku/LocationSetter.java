package com.firstapp.jakku;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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

        //Testing purposes only, also change xml to use
        /*
        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] test = getLocation();

                String checker = "latitude: " + test[0] + " longitude: " + test[1];

                Toast.makeText(LocationSetter.this, checker, Toast.LENGTH_SHORT).show();
            }
        });
*/
    }

    //takes a while to run, is done when toast appears
    // if toast is "Failed to find location", double-check if location is set in emulator
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
                            //setResults(res);
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
    public void savePref(String[] inData){
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


}
