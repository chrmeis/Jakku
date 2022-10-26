package com.firstapp.jakku;

import static android.content.Context.MODE_PRIVATE;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.widget.Toast;

import java.util.Calendar;


/**
 * @author Ludvig
 * NotificationSetup is used to set up notifications based on data saved in shared preference
 * called "sharedPref".
 */
public class NotificationSetup {
    /**
     * Finds the next notification that should be set based on data in shared preference
     * called "sharedPref" and sets it.
     * If an old notification was set by an activity calling this method, remove it first.
     * @param context Context variable required to get app information.
     */
    static public void nextNotification(Context context){
        AlarmManager manager = (AlarmManager) context.getSystemService((Context.ALARM_SERVICE));
        Intent intent = new Intent(context, AlarmReceiver.class);


        //if previous alarm exists, remove
        intent.setAction("Study");
        PendingIntent cancelStudy = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        manager.cancel(cancelStudy);
        intent.setAction("Training");
        PendingIntent cancelTraining = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        manager.cancel(cancelTraining);

        //notificationchannel required past Android 8
        notificationChannelWorkout(context);

        Calendar cal = Calendar.getInstance();

        int alarmHour = nextHour(context);

        //no studying scheduled, workout might still be scheduled
        if(alarmHour == -1){
            int nextDay = nextWorkout(cal.get(Calendar.DAY_OF_WEEK), context);
            //if no workout scheduled either, don't make an alarm
            if(nextDay == -1){
                return;
            }

            int offset = (nextDay - cal.get(Calendar.DAY_OF_WEEK));
            //offset <= 0 if next workout is during next week
            if(offset <= 0){
                cal.set(Calendar.DAY_OF_YEAR,cal.get(Calendar.DAY_OF_YEAR) + 7 + offset);
            }else{
                cal.set(Calendar.DAY_OF_YEAR,cal.get(Calendar.DAY_OF_YEAR) + offset);
            }
            //hour set to 18 to set alarm for training
            alarmHour = 18;
        }else{
            //if the hour for the next alarm has happened, alarm is next day
            if(alarmHour < cal.get(Calendar.HOUR_OF_DAY)){
                cal.set(Calendar.DAY_OF_YEAR,cal.get(Calendar.DAY_OF_YEAR)+1);
            }
        }
        //Set time for alarm
        cal.set(Calendar.HOUR_OF_DAY,alarmHour);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        cal.set(Calendar.MILLISECOND,0);

        //Set alarm
        if(alarmHour == 18){
            intent.setAction("Training");
        }else{
            intent.setAction("Study");
        }
        PendingIntent pintent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        manager.set(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pintent);
    }

    /**
     * Sets up a notificationChannel
     * @param context Context variable required to get app information.
     */
    static private void notificationChannelWorkout(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){ //if Android 8 or newer, so always since we're using Android 9 or greater
            //Importance high to have notifications show up on locked screen
            NotificationChannel channel = new NotificationChannel("Notification", "tRAINing notifications", NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Channel for tRAINing notification");


            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    /**
     * Finds the integer representing the day of the week when the next workout is scheduled.
     * @param currentDay The integer representing the current day.
     * @param context Context variable required to get app information.
     * @return Integer representing the day of the week for the next workout.
     * Returns -1 if no workout is scheduled.
     */
    private static int nextWorkout(int currentDay, Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("sharedPref", MODE_PRIVATE);
        int trainingSession = sharedPref.getInt("t_how_often",1);
        //prepared for 7 days, Sunday not used yet
        int[] schedule = {0,0,0,0,0,0,0};

        switch(trainingSession){
            case 0:
                break;
            case 1:
                schedule[6] = 18;
                break;
            case 2:
                schedule[2] = 18;
                schedule[4] = 18;
                break;
            case 3:
                schedule[1] = 18;
                schedule[3] = 18;
                schedule[5] = 18;
                break;
        }

        int checkDays = currentDay;
        for(int i = 1;i<7;i++){
            if(schedule[checkDays] != 0){
                //days count from 1, not 0
                return checkDays + 1;
            }
            checkDays++;
            //After saturday is checked
            if(checkDays == 7){
                checkDays = 0;
            }
        }
        return -1;
    }

    /**
     * Finds the hour of the next notification to be set.
     * @param context Context variable required to get app information.
     * @return Integer for the hour of the next alarm in 24 hour clock format.
     * Returns -1 if nothing is scheduled, or if only training is scheduled but not for today.
     */
    private static int nextHour(Context context){
        SharedPreferences sharedPref = context.getSharedPreferences("sharedPref", MODE_PRIVATE);
        int studySession = sharedPref.getInt("sb_total",4);
        int trainingSession = sharedPref.getInt("t_how_often",1);

        //Could be boolean, is int for readability (also because it works now and I don't want to touch)
        int[] timeSlotsDay = {0,0,0,0,0};

        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_WEEK);
        switch(trainingSession){
            case 0:
                break;
            case 1:
                if(day == Calendar.SATURDAY){
                    timeSlotsDay[4] = 18;
                }
                break;
            case 2:
                if(day == Calendar.TUESDAY || day == Calendar.THURSDAY){
                    timeSlotsDay[4] = 18;
                }
                break;
            case 3:
                if(day == Calendar.MONDAY || day == Calendar.WEDNESDAY || day == Calendar.FRIDAY){
                    timeSlotsDay[4] = 18;
                }
                break;
        }
        if(studySession >= 1){
            timeSlotsDay[0] = 8;
        }
        if(studySession >= 2){
            timeSlotsDay[2] = 13;
        }
        if(studySession >= 3){
            timeSlotsDay[1] = 10;
        }
        if(studySession == 4){
            timeSlotsDay[3] = 15;
        }
        int currentHour = cal.get(Calendar.HOUR_OF_DAY);

        if(currentHour < 8){
            if(timeSlotsDay[0] != 0){
                return 8;
            }
        }
        if(currentHour < 10){
            if(timeSlotsDay[1] != 0){
                return 10;
            }
        }
        if(currentHour < 13){
            if(timeSlotsDay[2] != 0){
                return 13;
            }
        }
        if(currentHour < 15){
            if(timeSlotsDay[3] != 0){
                return 15;
            }
        }
        if(currentHour < 18){
            if(timeSlotsDay[4] != 0){
                return 18;
            }
        }
        for(int i = 0; i<timeSlotsDay.length; i++){
            if(timeSlotsDay[i] != 0){
                return timeSlotsDay[i];
            }
        }

        //If there is nothing scheduled today or tomorrow
        return -1;
    }
}
