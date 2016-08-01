package com.example.niki864.farmschool101.service;

/**
 * Created by Niki864 on 7/26/2016.
 */
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.R;

import com.example.niki864.farmschool101.SecondActivity;

/**
 * This service is started when an Alarm has been raised
 *
 * We pop a notification into the status bar for the user to click on
 * When the user clicks the notification a new activity is opened
 *
 */
public class NotifyService extends Service {

    /**
     * Class for clients to access
     */
    public class ServiceBinder extends Binder {
        NotifyService getService() {
            return NotifyService.this;
        }
    }

    // Unique id to identify the notification.
    private static final int NOTIFICATION = 123;
    // Name of an intent extra we can use to identify if this service was started to create a notification
    public static final String INTENT_NOTIFY = "com.example.niki864.farmschool.service.INTENT_NOTIFY";
    // The system notification manager
    private NotificationManager mNM;

    @Override
    public void onCreate() {
        Log.i("NotifyService", "onCreate()");
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("LocalService", "Received start id " + startId + ": " + intent);

        // If this service was started by out AlarmTask intent then we want to show our notification
        if(intent.getBooleanExtra(INTENT_NOTIFY, false))
            //Set broadcast message from this app
            showNotification();

        // We don't care if this service is stopped as we have already delivered our notification
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    // This is the object that receives interactions from clients
    private final IBinder mBinder = new ServiceBinder();

    /**
     * Creates a notification and shows it in the OS drag-down status bar
     */
    private void showNotification() {
        // This is the 'title' of the notification
        CharSequence title = "Alarm!!";
        // This is the icon to use on the notification
        int icon = R.drawable.ic_dialog_alert;
        // This is the scrolling text of the notification
        CharSequence text = " Its time to water your crop!";
        // What time to show on the notification
        long time = System.currentTimeMillis();
        //Broadcast Message for listeners from other third party apps
        Intent intentBCM=new Intent();
        intentBCM.setAction("com.example.niki864.farmschool101.WATER_BROADCAST"); //Unique Broadcast ID
        sendBroadcast(intentBCM);

        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, new Intent(this, SecondActivity.class), 0);
        // Stop the service when we are finished
        stopSelf();



        Notification.Builder mBuilder =
                new Notification.Builder(this)
                        .setTicker(title)
                        .setSmallIcon(icon)
                        .setContentTitle(title)
                        .setContentText(text)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(false);

        mBuilder.setContentIntent(contentIntent);

        Notification notification = mBuilder.build();
        notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_ONGOING_EVENT;
        // Send the notification to the system.
        mNM.notify(NOTIFICATION, notification);



    }
}