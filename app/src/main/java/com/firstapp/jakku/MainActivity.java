package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    TextView info;
//    Button settings;
    //Weather weather;

    public void updateInfo(String string){
        info.setText(string);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info=(TextView) findViewById(R.id.textView2);
//        settings=(Button) findViewById(R.id.b_settings);
        //JSONArray jsonArray = new JSONArray();
        //final String[] string = {"fail"};
        //final String[] extra = {"Slow"};
        //TalkToServer varName = new TalkToServer(); //pass parameters if you need to the constructor
        //varName.execute();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here
                String temp = Weather.currentTemp();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //UI Thread work here
                        updateInfo(temp + " ℃");
                    }
                });
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Test1");
                info.setText("fail");
                //weather = new Weather();
                //JSONArray jsonArray  = weather.getJsonArray();
                //String  string = weather.temperature();
                // After getting the result
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Post the result to the main thread
                        System.out.println("Test2");
                       // info.setText(string);
                        //extra[0] = string;
                    }
                });
            }
        }).start();
        */
        /*try {
            //String string = ((JSONArray) ((JSONObject) jsonArray.get(0)).get("parameters"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
        //info.setText(extra[0]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id= item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if(id == R.id.trainingspref){
            Intent intent = new Intent(MainActivity.this, TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.studypref){
            Intent intent = new Intent(MainActivity.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if(id == R.id.home){
 //           Intent intent = new Intent(MainActivity.this, MainActivity.class);
 //           startActivity(intent);
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}

