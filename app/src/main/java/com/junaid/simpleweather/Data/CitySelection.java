package com.junaid.simpleweather.Data;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Junaid on 24-12-2016.
 */

public class CitySelection {

    public static final String SHARED_KEY = "city";

    private SharedPreferences prefs;

    public CitySelection(Activity a){
        prefs = a.getPreferences(Activity.MODE_PRIVATE);
    }
    public String getCity(){
        return prefs.getString(SHARED_KEY,"Vadodara");
    }
    public void setCity(String s){
        prefs.edit().putString(SHARED_KEY,s).apply();
    }
}
