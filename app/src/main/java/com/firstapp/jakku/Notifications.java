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

/**
 * This class represents the notifications sent to the user every
 * 4 hours, reminding them to drink a glass of water
 * @author Liam Mattsson
 * @version 1.0
 */

public class Notifications extends AppCompatActivity {

    private Switch switch1;
    private boolean clicked = false;
    private final static int INTERVAL = 14400000; //4 hours in ms
    private Handler mHandler = new Handler();

    /**
     * Gets the current hour
     * @return the current hour on the clock
     */
    public int getHour(){
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY); //Current Hour
        return currentHour;
    }

    /**
     * This is a task that runs the notification code,
     * and repeats it after a certain interval. The interval
     * here is 4 hours.
     */
    private Runnable mHandlerTask = new Runnable() {
        @Override
        public void run() {

            createNotif();
            mHandler.postDelayed(mHandlerTask,INTERVAL);
        }
    };

    /**
     * Runs the code inside this method when creating the page
     * @param savedInstanceState previous stored data
     */
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

    /**
     * This function makes sure that the user won't receive
     * notifications during 00.00 and 06.00
     */
    public void startRepeatingTask(){
        if(!(getHour() > 0 && getHour() < 6)) {
            mHandlerTask.run();
        }
    }

    /**
     * This function can stop the notification task from running
     */
    public void stopRepeatingTask(){
        mHandler.removeCallbacks(mHandlerTask);
    }

    /**
     * Private helper function which includes all the code
     * for the notification
     */
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
     * This function creates the side menu
     * @param item a list of items in the menu
     * @return true if the menu was created
     */
    @Override
    public boolean onCreateOptionsMenu(Menu item){
        item.add("Home");
        item.add("Study Preferences");
        item.add("Training Preferences");
        item.add("Exercises");
        item.add("Set Location");
        item.add("Workout Notifications");
        item.add("Water Intake");
        return super.onCreateOptionsMenu(item);
    }

    /**
     * This function redirects you to the correct
     * page after clicking on it in the menu
     * @param item item in the menu
     * @return true if you were redirected
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getTitle().equals("Home")){
            Intent i = new Intent(Notifications.this, MainActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Study Preferences")){
            Intent i = new Intent(Notifications.this, StudyActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Training Preferences")){
            Intent i = new Intent(Notifications.this,TrainingActivity.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Exercises")){
            Intent i = new Intent(Notifications.this, Exercise.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Set Location")){
            Intent i = new Intent(Notifications.this, LocationSetter.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Workout Notifications")){
            Intent i = new Intent(Notifications.this, Exercise.class);
            startActivity(i);
            finish();
            return true;
        } else if(item.getTitle().equals("Water Intake")){
            Intent i = new Intent(Notifications.this, WaterIntake.class);
            startActivity(i);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}