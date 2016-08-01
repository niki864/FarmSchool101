package com.example.niki864.farmschool101;

/**
 * Created by Niki864 on 7/26/2016.
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.niki864.farmschool101.service.ScheduleClient;


import java.util.Calendar;

/**
 * This is the activity that is started when the user presses the option in the crop option field
 *
 */
public class SecondActivity extends AppCompatActivity {

    // This is a handle so that we can call methods on our service
    private ScheduleClient scheduleClient;
    // This is the date picker used to select the date for our notification
    private DatePicker picker;


    @Override
    protected void onCreate(Bundle intentBundle) {
        super.onCreate(intentBundle);
        setContentView(R.layout.activity_second);



        // Create a new service client and bind our activity to this service
        scheduleClient = new ScheduleClient(this);


        scheduleClient.doBindService();

        // Get a reference to our date picker
        picker = (DatePicker) findViewById(R.id.scheduleTimePicker);

    }

    public void onDateSelectedButtonClick(View v){
        // Get the date from our datepicker
        int day = picker.getDayOfMonth();
        int month = picker.getMonth();
        int year = picker.getYear();
        Bundle bundle = getIntent().getExtras();
        String waterFreq= bundle.getString("WaterFreq");
        int freq=Integer.parseInt(waterFreq);
        // Create a new calendar set to the date chosen
        // we set the time to midnight (i.e. the first minute of that day)
        Calendar c0 = Calendar.getInstance();
        c0.set(year, month, (day+freq));
        c0.set(Calendar.HOUR_OF_DAY, 7);
        c0.set(Calendar.MINUTE, 0);
        c0.set(Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c0);
        Calendar c1 = Calendar.getInstance();
        c1.set(year, month, (day+freq+freq));
        c1.set(Calendar.HOUR_OF_DAY, 7);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c1);
        Calendar c2 = Calendar.getInstance();
        c2.set(year, month, (day+freq+freq+freq));
        c2.set(Calendar.HOUR_OF_DAY, 7);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c2);
        Calendar c3 = Calendar.getInstance();
        c3.set(year, month, (day+freq+freq+freq+freq));
        c3.set(Calendar.HOUR_OF_DAY, 7);
        c3.set(Calendar.MINUTE, 0);
        c3.set(Calendar.SECOND, 0);
        // Ask our service to set an alarm for that date, this activity talks to the client that talks to the service
        scheduleClient.setAlarmForNotification(c3);
        // Notify the user what they just did
        Toast.makeText(this, "Configured System-wide BCM to Water Crops at "+freq+" intervals for this month", Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onStop() {
        // When our activity is stopped ensure we also stop the connection to the service
        // this stops us leaking our activity into the system *bad*
        if (scheduleClient != null)
            scheduleClient.doUnbindService();
        super.onStop();
    }

}