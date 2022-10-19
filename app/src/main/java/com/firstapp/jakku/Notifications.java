package com.firstapp.jakku;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import java.security.spec.ECField;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class Notifications extends AppCompatActivity {

    private Switch switch1;
    private boolean clicked = false;
    private final static int INTERVAL = 14400000; //4 hours in ms
    private Handler mHandler = new Handler();

    public int getHour(){
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); //Current Hour
        return currentHour;
    }

    private Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {
            createNotif();
            mHandler.postDelayed(mHandlerTask,INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        switch1 = findViewById(R.id.switch1);
        switch1.setOnClickListener(view -> {
            if(switch1.isChecked()){
                startRepeatingTask();
            }
        });
    }

    public void startRepeatingTask(){
        if(!(getHour() > 0 && getHour() < 6)) {
            mHandlerTask.run();
        }
    }

    public void stopRepeatingTask(){
        mHandler.removeCallbacks(mHandlerTask);
    }

    private void createNotif(){
        String id = "my_channel_id_01";
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = manager.getNotificationChannel(id);
            if(channel == null){
                channel = new NotificationChannel(id, "Channel Title", NotificationManager.IMPORTANCE_HIGH);
                // config notification channel
                channel.setDescription("[Channel description]");
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100,1000,200,340});
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                manager.createNotificationChannel(channel);
            }
        }
        Intent notificationIntent = new Intent(this,Notification.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentIntent = PendingIntent.getActivity(this,0,notificationIntent,PendingIntent.FLAG_IMMUTABLE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,id)
                .setSmallIcon(R.drawable.ic_baseline_settings_24)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                .setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_background))
                        .bigLargeIcon(null))
                .setContentTitle("Hydrate!")
                .setContentText("Time to drink a glass of water")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[]{100,1000,200,340})
                .setAutoCancel(true)
                .setTicker("Notification");
        builder.setContentIntent(contentIntent);
        NotificationManagerCompat m = NotificationManagerCompat.from(getApplicationContext());
        m.notify(1, builder.build());
    }

    /**
     * onCreateOptionsMenu sets the menu bar according to settingsmenu.xml file
     * @param menu
     * @author Menu made by Liam Mattsson
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settingsmenu,menu);
        return true;
    }

    /**
     * onOptionsItemSelected switches to other activities based on what user choose in menubar.
     * @param item
     */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        //this handles tab on activity, duplicate for every new item in the menu
        if (id == R.id.trainingspref) {
            Intent intent = new Intent(Notifications.this, TrainingActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.studypref) {
            Intent intent = new Intent(Notifications.this, StudyActivity.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.home) {
            Intent intent = new Intent(Notifications.this, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == R.id.water_intake) {
            Intent intent = new Intent(Notifications.this, WaterIntake.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.exercise) {
            Intent intent = new Intent(Notifications.this, Exercise.class);
            startActivity(intent);
            finish();
            return true;
        }

        if (id == R.id.location_setter) {
            Intent intent = new Intent(Notifications.this, LocationSetter.class);
            startActivity(intent);
            finish();
            return true;
        }
        if(id ==R.id.water_notification){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}