package com.firstapp.jakku;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.firstapp.jakku.databinding.ActivityMainBinding;
import com.firstapp.jakku.databinding.ActivityNotificationWorkoutBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.Date;

public class NotificationWorkout extends AppCompatActivity {


    private ActivityNotificationWorkoutBinding binding;
    //part of test UI
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;

    //Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotificationWorkoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        createNotificationChannel();

        binding.selectTimeBtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

                showTimePicker();

                //notificationSetup();

            }
        });


        binding.setAlarmbtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

                setAlarm();

            }
        });

        binding.cancelAlarmbtn.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){

                cancelAlarm();

            }
        });
    }

    //Instant notification test

    private void notificationSetup(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //if Android 8 or newer
            NotificationChannel channel = new NotificationChannel("Test", "Testing", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(NotificationWorkout.this,"Test");
        Date currentTime = Calendar.getInstance().getTime();
        builder.setContentTitle("Test_title");
        builder.setContentText(currentTime.toString());
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(NotificationWorkout.this);
        managerCompat.notify(1,builder.build());
    }





    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //if Android 8 or newer, so always since we're using Android 9 or greater
            //Importance high to have notifications show up on locked screen
            NotificationChannel channel = new NotificationChannel("Notification", "tRAINing notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for tRAINing notification");


            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
    //part of test UI
    private void showTimePicker(){
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12)
                .setMinute(0)
                .setTitleText("Select Notification Time")
                .build();

        picker.show(getSupportFragmentManager(),"Notification");

        picker.addOnPositiveButtonClickListener(new View.OnClickListener(){
            @Override
            public  void onClick(View v){
                binding.selectedTime.setText(picker.getHour()+" : " + picker.getMinute());


                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
            }

        });


    }

    private void setAlarm(){

        alarmManager = (AlarmManager) getSystemService((Context.ALARM_SERVICE));
        //MUST MATCH INTENT/PENDING INTENT FOR CANCELALARM
        Intent intent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        //Potential hardcode alarm
        /*
        if(calendar == null){
            calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY,12);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.SECOND,0);
            calendar.set(Calendar.MILLISECOND,0);
        }
        */

        //setInexactRepeating sets alarm +- 1 minute, we don't need more precision
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);

        Toast.makeText(this, "Reminder set Successfully", Toast.LENGTH_SHORT).show();

    }

    private void cancelAlarm(){
        //MUST MATCH INTENT/PENDING INTENT FOR SETALARM
        Intent intent = new Intent(this, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Reminder Cancelled", Toast.LENGTH_SHORT).show();

    }
}
