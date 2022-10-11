package com.firstapp.jakku;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class pass1 extends AppCompatActivity {


    private Button nextButton;
    private Button previousButton;
    private ImageView trainingImg;
    private TextView trainingText;
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
                trainingText.setText("Time for some pushups!!");
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                trainingImg.setImageResource(R.drawable.ic_baseline_directions_run_24);
                trainingText.setText("Run for three years, two months, 14 days and 16 hours. Run Forest Run!!");
            }
        });


    }
}