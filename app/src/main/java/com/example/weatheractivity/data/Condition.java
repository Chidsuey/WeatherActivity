package com.example.weatheractivity.data;

import org.json.JSONArray;


public class Condition implements JSONPopulatorA {

    private String icon;
    private String description;

    public String getIcon() {
        return icon;
    }

    public String getDescription() {
        return description;
    }

    public void populate(JSONArray array){


        String arrayString = array.optString(0); //put JSONArray into string
        String[] newArray = (arrayString.split("\"")); //Split up the string
        icon = newArray[13];
        description = newArray[9];
        description = description.substring(0,1).toUpperCase() + description.substring(1).toLowerCase();

    }
}
