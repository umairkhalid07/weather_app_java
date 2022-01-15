package com.example.weatherapp;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class Settings extends AppCompatActivity {
    private Button back_button;
    private Button ok_button;
    RadioGroup group01;
    RadioButton radio01;
    private EditText city_text;
    private String city;
    private double temp_data;
    private double min_temp_data;
    private double max_temp_data;
    private double humidity_data;
    private double pressure_data;
    private double feelsLike_data;
    String rounded_new_temp_data = "0.0'C";
    String rounded_new_min_temp_data = "0.0'C";
    String rounded_new_max_temp_data = "0.0'C";
    String rounded_feelsLike_data = "0.0'C";
    String rounded_humidity_data = "0.0";
    String rounded_pressure_data = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        city_text = (EditText) findViewById(R.id.city_button);
        group01 = findViewById(R.id.RadioGroup);
        city = city_text.getText().toString();
        Log.d("TERMINAL_OUTPUT", city);
        ok_button = (Button) findViewById(R.id.OK);
        ok_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String API_URL = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid=e049cc99f987f79fa3cc589df6dc1135";
                int radioID = group01.getCheckedRadioButtonId();
                radio01 = findViewById(radioID);
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            URL web_data = new URL(API_URL);
                            BufferedReader in = new BufferedReader(new InputStreamReader(web_data.openStream()));
                            String response = "";
                            Log.d("TERMINAL_OUTPUT ", response);
                            String line;
                            while((line = in.readLine() )!= null)
                            {
                                Log.d("TERMINAL_OUTPUT", line);
                                response += line;
                            }
                            final String res = response;
                            Log.d("TERMINAL_OUTPUT", res);
                            JSONObject stuff = new JSONObject(res);
                            temp_data = stuff.getJSONObject("main").getDouble("temp");
//                            temp_data = temp_data - 273.15;
                            min_temp_data = stuff.getJSONObject("main").getDouble("temp_min");
//                            min_temp_data = temp_data - 273.15;
                            max_temp_data = stuff.getJSONObject("main").getDouble("temp_max");
//                            max_temp_data = temp_data - 273.15;
                            feelsLike_data = stuff.getJSONObject("main").getDouble("feels_like");
//                            feelsLike_data = temp_data - 273.15;
                            humidity_data = stuff.getJSONObject("main").getDouble("humidity");
                            pressure_data = stuff.getJSONObject("main").getDouble("pressure");

                            int selectedId = group01.getCheckedRadioButtonId();
                            Log.d("********ID", String.valueOf(selectedId));

                            if(radioID == 2131231205)
                            {
                                temp_data = ((temp_data - 273.15) * 1.8) + 32;
                                min_temp_data = ((min_temp_data - 273.15) * 1.8) + 32;
                                max_temp_data = ((max_temp_data - 273.15) * 1.8) + 32;
                                feelsLike_data = ((feelsLike_data - 273.15) * 1.8) + 32;
                            }
                            else if(radioID == 2131231204)
                            {
                                temp_data = temp_data - 273.15;
                                min_temp_data = min_temp_data - 273.15;
                                max_temp_data = max_temp_data - 273.15;
                                feelsLike_data = feelsLike_data - 273.15;
                            }

                            final Double new_temp_data = temp_data;
                            final Double new_min_temp_data = min_temp_data;
                            final Double new_max_temp_data = max_temp_data;
                            final Double new_feelsLike_data = feelsLike_data;
                            final Double new_humidity_data = humidity_data;
                            final Double new_pressure_data = feelsLike_data;

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    rounded_new_temp_data = String.valueOf(new_temp_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_new_temp_data);
                                    rounded_new_min_temp_data = String.valueOf(new_min_temp_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_new_min_temp_data);
                                    rounded_new_max_temp_data = String.valueOf(new_max_temp_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_new_max_temp_data);
                                    rounded_feelsLike_data = String.valueOf(new_feelsLike_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_feelsLike_data);
                                    rounded_humidity_data = String.valueOf(new_humidity_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_humidity_data);
                                    rounded_pressure_data = String.valueOf(new_pressure_data);
                                    Log.d("TERMINAL_OUTPUT", rounded_pressure_data);
                                }
                            });
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Thread thread = new Thread(r);
                thread.start();
            }
        });
        back_button = (Button) findViewById(R.id.b_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                open_main();
            }
        });
    }

    public void open_main() {
        Intent intent = new Intent(this, MainActivity.class);
        Bundle data = new Bundle();
        data.putString("Temperature",rounded_new_temp_data);
        data.putString("Minimum",rounded_new_min_temp_data);
        data.putString("Maximum",rounded_new_max_temp_data);
        data.putString("FeelsLike",rounded_feelsLike_data);
        data.putString("Humidity",rounded_humidity_data);
        data.putString("Pressure",rounded_pressure_data);
        intent.putExtras(data);
        startActivity(intent);
    }
}