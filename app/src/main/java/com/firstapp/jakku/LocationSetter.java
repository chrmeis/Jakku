package com.firstapp.jakku;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
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

public class LocationSetter extends AppCompatActivity {


    private ActivityLocationSetBinding binding;

    private FusedLocationProviderClient fusedLocationClient;


    private Task<Location> location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLocationSetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        binding.locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(LocationSetter.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(LocationSetter.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    //Ask for permissions
                    ActivityCompat.requestPermissions(LocationSetter.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},1);
                    return;
                }
                location = fusedLocationClient.getLastLocation()
                        .addOnSuccessListener(LocationSetter.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                // If null, check emulator settings
                                if (location != null) {
                                    String longitude = String.valueOf(location.getLongitude());
                                    String latitude = String.valueOf(location.getLatitude());
                                    //String total = "longitude = " + longitude + " latitude = " + latitude;

                                   Toast.makeText(LocationSetter.this, "Location set", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(LocationSetter.this, "Failed to find location", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


            }
        });
    }



}
