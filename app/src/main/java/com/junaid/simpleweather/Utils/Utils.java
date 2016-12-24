package com.junaid.simpleweather.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.jar.JarException;

/**
 * Created by Junaid on 23-12-2016.
 */

public class Utils {

    public static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
    public static final String APPI_KEY = "&appid=12ca4d191fb3f588d614b07f8a979e63";
    public static final String ICON_URL = "http://api.openweathermap.org/img/w/";


    /*Helper Methods*/

    public static JSONObject getObject(String tag, JSONObject object) throws JSONException{
        return object.getJSONObject(tag);
    }

    public static String getString(String tag, JSONObject object) throws JSONException{
        return object.getString(tag);
    }

    public static float getFloat(String tag, JSONObject object) throws JSONException{
        return (float) object.getDouble(tag);
    }
    public static double getDouble(String tag, JSONObject object) throws JSONException{
        return object.getDouble(tag);
    }
    public static int getInt(String tag, JSONObject object) throws JSONException{
        return object.getInt(tag);
    }

}
