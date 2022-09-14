package com.firstapp.jakku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingsActivity extends AppCompatActivity {
    Button back;
    Switch sportssettings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

/* //manuel "back" button
        back=(Button) findViewById(R.id.b_backFromSettings);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Back button pressed");
               finish();
            }
        });
 */
        sportssettings = findViewById(R.id.sw_sports);
        sportssettings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    // The toggle is enabled
                    System.out.println("Toggle enabled");
                } else {
                    // The toggle is disabled
                    System.out.println("Toggle disabled");
                }
            }
        });

    }
}