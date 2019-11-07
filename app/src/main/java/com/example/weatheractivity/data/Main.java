package com.example.weatheractivity.data;

import org.json.JSONObject;

public class Main implements JSONPopulator {
    private Units units;
    private Weather weather;

    public Units getUnits() {
        return units;
    }

    @Override
    public void populate(JSONObject data) {


            units = new Units();
            units.populate(data);
            weather = new Weather();
    }
}
