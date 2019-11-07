package com.example.weatheractivity.service;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.weatheractivity.data.Condition;
import com.example.weatheractivity.data.Main;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class YahooWeatherService {

    private WeatherServiceCallback callback;
    private String location;
    private Exception error;

    public YahooWeatherService(WeatherServiceCallback callback) {
        this.callback = callback;
    }

    public String getLocation() {
        return location;
    }

    @SuppressLint("StaticFieldLeak")
    public void refreshWeather(final String l) {
        this.location = l;
    new AsyncTask<String, Void, String>() {

            @Override
            protected String doInBackground(String... strings) {

                String endpoint = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s&units=imperial&appid=2a11b91eb7d89d7fa6e7ae15f9e857cc", location);

                try {
                    URL url = new URL(endpoint);

                    URLConnection connection = url.openConnection();

                    InputStream inputStream = connection.getInputStream();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder result = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return result.toString();
                } catch (Exception e) {
                    error = e;
                }

                return null;
            }

        @Override
        protected void onPostExecute(String s) {

            if (s == null && error != null) {
                callback.serviceFailure(error);
                return;
            }

            try {
                JSONObject data = new JSONObject(s);
                JSONArray array = new JSONArray(data.optString("weather"));
                String codResults = data.optString("cod");
                int count;
                try {
                    count = Integer.parseInt(codResults);
                }
                catch (NumberFormatException e) {
                    count = 404;
                }
                if (count == 404) {
                    callback.serviceFailure(new LocationWeatherException("No weather information found for" + location));
                    return;
                }

                Main main = new Main();
                main.populate(data.optJSONObject("main"));
                Condition condition = new Condition();
                condition.populate(array);

                callback.serviceSuccess(main, condition);

            } catch (JSONException e) {
                callback.serviceFailure(e);
            }
        }
    }.execute(location);
    }

    public class LocationWeatherException extends Exception {
        public LocationWeatherException(String detailMessage) {
            super(detailMessage);
        }
    }
}
