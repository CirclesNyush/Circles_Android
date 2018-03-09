package com.example.anpu.circles.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.anpu.circles.R;
import com.example.anpu.circles.WelcomeActivity;

/**
 * Created by anpu on 2018/3/9.
 */

public class NotificationUtil {
    /*
      refer to http://blog.csdn.net/dsc114/article/details/51721472
     */

    private Context context;
    private NotificationManager notificationManager;

    public NotificationUtil(Context context) {
        this.context = context;
        this.notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
    }

    public void sendNotification(String message) {
        Intent intent = new Intent(context, WelcomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder builder = new Notification.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.project_detail_cir)
                .setContentTitle("Title")
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setTicker("New Message")
                .setOngoing(true)
                .setNumber(20);

        Notification notification = builder.build();
        notification.flags = Notification.FLAG_NO_CLEAR;
        notificationManager.notify(0, notification);

    }
}
