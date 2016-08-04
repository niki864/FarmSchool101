package com.example.niki864.farmschool101;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import android.os.Handler;
import org.json.JSONObject;

public class CitySelectActivity extends AppCompatActivity implements OnItemSelectedListener {
    Handler handler;

    String SpinnerItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_select);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.city_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        SpinnerItem=item;

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        updateWeatherData(item);
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    private void updateWeatherData(final String city) {
        handler = new Handler();
        new Thread() {
            @Override
            public void run() {
                final JSONObject json = RemoteFetch.getJSON(getApplicationContext(), city);
                if(json == null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), getApplicationContext().getString(R.string.place_not_found), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            alertWeather(json);
                        }
                    });
                }
            }
        }.start();
    }

    private void alertWeather(JSONObject json) {

        double temp=0;
        try {

            JSONObject main = json.getJSONObject("main");

            temp= main.getDouble("temp");

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "The current temperature in your city is: " + temp +"degrees Celsius", Toast.LENGTH_SHORT).show();

    }

    public void onOptionConfirmed(View v) {
        Bundle bundle = getIntent().getExtras();
        String waterFreq= bundle.getString("WaterFreq");
        /** this method will start the  SecondActivity to set the reminder on calendar by fixing in the reminder interval */
        Intent intent = new Intent(CitySelectActivity.this, SecondActivity.class);
        /** a nifty little bundle to pass data from this activity to the secondActivity */
        //Create the bundle
        Bundle b = new Bundle();
        //Add your data to bundle
        b.putString("WaterFreq", waterFreq);
        b.putString("City",SpinnerItem);
        intent.putExtras(b);
        startActivity(intent);

    }

}
