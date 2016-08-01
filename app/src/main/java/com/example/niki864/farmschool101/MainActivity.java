package com.example.niki864.farmschool101;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity implements gConstants {


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This is the onClick called from the XML to set a new notification
     */
    String freq;
    public void onRadioButtonClicked(View v) {
        boolean checked = ((RadioButton) v).isChecked();
        String cropType;

        // Check which radio button was clicked
        switch (v.getId()) {
            case R.id.Maize:
                if (checked) {
                    // Maize Selected
                    freq = freqMaize;
                    cropType = cropMaize;
                    Toast.makeText(this, "You have chosen " + cropType, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.Corn:
                if (checked) {
                    // Corn Selected
                    freq = freqCorn;
                    cropType = cropCorn;
                    Toast.makeText(this, "You have chosen " + cropType, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.Rice:
                if (checked) {
                    // Rice Selected
                    freq = freqRice;
                    cropType = cropRice;
                    Toast.makeText(this, "You have chosen " + cropType, Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.Tomato:
                if (checked) {
                    // Tomato Selected
                    freq = freqTomato;
                    cropType = cropTomato;
                    Toast.makeText(this, "You have chosen " + cropType, Toast.LENGTH_SHORT).show();

                }
                break;
        }

    }

    public void infoCropConfirmed(View v) {
        /** this method will start the second activity to set the reminder on calendar by fixing in the reminder interval */
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        /** a nifty little bundle to pass data from this activity to the secondActivity */
        //Create the bundle
        Bundle b = new Bundle();
        //Add your data to bundle
        b.putString("WaterFreq", freq);
        intent.putExtras(b);
        startActivity(intent);

    }


}