package com.firstapp.jakku;

import static com.firstapp.jakku.Weather.rainToday;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.json.JSONException;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AlarmReceiver extends BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {
        Intent i = new Intent(context,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, i, PendingIntent.FLAG_IMMUTABLE);
        HashMap<Integer, Boolean> raininfo = null;




        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"Notification");
        builder.setContentTitle("Notification Manager");
        builder.setContentText("Workout time! Tap to see what you should do!");
        builder.setSmallIcon(R.drawable.ic_launcher_background);
        builder.setAutoCancel(true);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        //High priority, should show up even if phone locked
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1,builder.build());




    }

}
