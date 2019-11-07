package com.example.weatheractivity.service;

import com.example.weatheractivity.data.Condition;
import com.example.weatheractivity.data.Main;

public interface WeatherServiceCallback {
    void serviceSuccess(Main main, Condition condition);
    void serviceFailure(Exception exception);
}
