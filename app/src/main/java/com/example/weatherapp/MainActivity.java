package com.example.weatherapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private Button settings_button;
    TextView tv1;
    TextView tv2;

    String temp = "0.0'C";
    String min = "0.0'C";
    String max = "0.0'C";
    String feels = "0.0'C";
    String humidity = "0.0";
    String pressure = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        temp = intent.getStringExtra("Temperature");
        min = intent.getStringExtra("Minimum");
        max = intent.getStringExtra("Maximum");
        feels = intent.getStringExtra("FeelsLike");
        humidity = intent.getStringExtra("Humidity");
        pressure = intent.getStringExtra("Pressure");

        tv1 = findViewById(R.id.temp);
        tv1.setText("Current Temperature " + temp);

        tv2 = findViewById(R.id.details);
        tv2.setText("Minimum Temperature " + min +"\n"+
                   "Maximum Temperature " + max+"\n"+
                   "Feels Like Temperature " + feels +"\n"+
                   "Humidity " + humidity +" \n" +
                   "Pressure " + pressure +" \n");

        settings_button = (Button) findViewById(R.id.s_button);
        settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_settings();
            }
        });
    }

    public void open_settings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

}