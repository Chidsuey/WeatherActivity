package com.example.weatheractivity.data;

import org.json.JSONObject;

public class Units implements JSONPopulator {
    public String getTemperature() {
        return temperature;
    }

    private String temperature;
    private Integer tempInt;

    @Override
    public void populate(JSONObject data) {

        temperature = data.optString("temp");
        tempInt = (Math.round(Float.parseFloat(temperature)));
        temperature = tempInt.toString();
        System.out.println(temperature);
    }
}
