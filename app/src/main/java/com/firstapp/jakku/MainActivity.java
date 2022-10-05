package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /* Varning från commit
    Warning: Do not place Android context classes in static fields; this is a memory leak
     */
    static TextView info;
    static TextView Todo;
//    Button settings;
    //Weather weather;


    public static void updateInfo(String string){
        info.setText(string);
    }

    public static void updateShedule(){
        System.out.println("---Main updateShedule---");
       String str=Schema.make_schedule();
        System.out.println("updateSchedule is: \n"+str);
        Todo.setText(str);
        System.out.println("---end Main updateShedule---");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("---Main onCreate---");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        info=(TextView) findViewById(R.id.textView2);
//        settings=(Button) findViewById(R.id.b_settings);
        //JSONArray jsonArray = new JSONArray();
        //final String[] string = {"fail"};
        //final String[] extra = {"Slow"};
        TalkToServer varName = new TalkToServer(); //pass parameters if you need to the constructor
        varName.execute();
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



        //putting schema into homescreen
        Schema.set_weekday();
//        Schema.get_study_amount();
//        Schema.get_study_duration();

        SharedPreferences theOldOnes = getApplicationContext().getSharedPreferences("sharedPref", Context.MODE_PRIVATE);
        int study_total= theOldOnes.getInt("total", 0);
        int study_session= theOldOnes.getInt("session", 0);
        System.out.println("---sp hämtat i main från study---");
        System.out.println("study_total: "+study_total);
        System.out.println("study_session: "+study_session);
        System.out.println("---the end---");

         Todo=(TextView) findViewById(R.id.textView3);
         Todo.setText(Schema.make_schedule());
        System.out.println("---end main onCreate---");
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
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}



