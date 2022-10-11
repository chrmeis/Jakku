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
import android.widget.TextView;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    /* Varning från commit
    Warning: Do not place Android context classes in static fields; this is a memory leak
     */
    static TextView info;
    static TextView Todo;
    static TextView date;
    private LocalDateTime tododate = LocalDateTime.now();
    private static DayOfWeek day;
    private Button monButton;
    private Button tueButton;
    private Button wenButton;
    private Button thuButton;
    private Button friButton;
    private static Schema schedule = new Schema();
//    Button settings;
    //Weather weather;


    public static void updateInfo(String string){
        info.setText(string);
    }

    public static void updateShedule(){
//        System.out.println("\n\n!!!!!!!!!!!!!!!!!!updateShedule!!!!!!!!!!!!!\n\n");

        schedule.make_schedule();

        //String str=Schema.make_schedule();
        StringBuilder str = schedule.get_scheduel(day);

     //   System.out.println("str: "+str);
        Todo.setText(str.toString());
        date.setText(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));
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
        TalkToServer varName = new TalkToServer(); //pass parameters if you need to the constructor
        varName.execute();

        monButton = findViewById(R.id.buttonMon);
        tueButton =  findViewById(R.id.buttonTue);
        wenButton =  findViewById(R.id.buttonWen);
        thuButton =  findViewById(R.id.buttonThu);
        friButton =  findViewById(R.id.buttonFre);

        monButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = DayOfWeek.MONDAY;
                updateShedule();
            }
        });
        tueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = DayOfWeek.TUESDAY;
                updateShedule();
            }
        });
        wenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = DayOfWeek.WEDNESDAY;
                updateShedule();
            }
        });
        thuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = DayOfWeek.THURSDAY;
                updateShedule();
            }
        });
        friButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                day = DayOfWeek.FRIDAY;
                updateShedule();
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


         /* tododate is a LocalDateTime. getDayofWeek returns a DayofWeek object
         DayOfWeek can display current date in many ways by using different textstyles
         Locale is just how the date should be displayed according to the local convention.
         (ex. same time could be 10 pm or 22:00 depending on country) */
        date = (TextView) findViewById(R.id.textViewdate);
        day = tododate.getDayOfWeek();
        date.setText(day.getDisplayName(TextStyle.FULL, Locale.ENGLISH));


        //putting schema into homescreen
        Schema.get_study_amount();
        Schema.get_study_duration();
         Todo= findViewById(R.id.textView3);
        schedule.make_schedule();
        // Todo.setText(schedule.get_scheduel(day));
        System.out.println("!!!!!!!!!!!!!!!!!!!!!after set text make shedule call!!!!!!!!!!!!!!!!!!!");




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



