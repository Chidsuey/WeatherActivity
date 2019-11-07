package com.example.weatheractivity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.weatheractivity.data.Condition;
import com.example.weatheractivity.data.IconGrabber;
import com.example.weatheractivity.data.Main;
import com.example.weatheractivity.service.WeatherServiceCallback;
import com.example.weatheractivity.service.YahooWeatherService;



public class MainActivity extends AppCompatActivity implements WeatherServiceCallback {

    private ImageView weatherIconImageView;
    private TextView temperatureTextView;
    private TextView conditionTextView;
    private TextView locationTextView;
    private String location = "London";
    private String iconImg;
    private String icon;

    private YahooWeatherService service;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        weatherIconImageView = findViewById(R.id.weatherIconImageView);
        temperatureTextView = findViewById(R.id.temperatureTextView);
        conditionTextView = findViewById(R.id.conditionTextView);
        locationTextView = findViewById(R.id.locationTextView);

        service = new YahooWeatherService(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        service.refreshWeather(location);
    }

    @Override
    public void serviceSuccess(Main main, Condition condition) {
        dialog.hide();
        icon = condition.getIcon();
        iconImg = String.format("https://openweathermap.org/img/wn/%s@2x.png",icon);

        new IconGrabber(iconImg, weatherIconImageView).execute();

        temperatureTextView.setText(main.getUnits().getTemperature() + "\u00B0");
        conditionTextView.setText(condition.getDescription());
        locationTextView.setText(service.getLocation());
    }

    @Override
    public void serviceFailure(Exception exception) {
        dialog.hide();
        Toast.makeText(this, exception.getMessage(), Toast.LENGTH_LONG).show();
    }
}
