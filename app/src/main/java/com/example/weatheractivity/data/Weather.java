package com.example.weatheractivity.data;

import org.json.JSONObject;

public class Weather implements JSONPopulator {
    
    private Condition condition;
    @Override
    public void populate(JSONObject data) {
        condition = new Condition();
    }
}
