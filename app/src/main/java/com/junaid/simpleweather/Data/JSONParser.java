package com.junaid.simpleweather.Data;

import android.util.Log;

import com.junaid.simpleweather.Model.Clouds;
import com.junaid.simpleweather.Model.CurrentCondition;
import com.junaid.simpleweather.Model.Place;
import com.junaid.simpleweather.Model.Weather;
import com.junaid.simpleweather.Model.Wind;
import com.junaid.simpleweather.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Junaid on 23-12-2016.
 */

public class JSONParser {
    public static Weather getWeather(String data){

//        Log.d("JSONDATA",data);
        if(data == null)
            return null;

        Weather weather = new Weather();

        try {
            JSONObject jsonObject = new JSONObject(data);

            /*--------parse the place object----------*/

            Place place =  new Place();

            //Coord Object
            JSONObject coordObj = Utils.getObject("coord",jsonObject);
            place.setLat(Utils.getFloat("lat",coordObj));
            place.setLon(Utils.getFloat("lon",coordObj));

            //sys object
            JSONObject sysObj = Utils.getObject("sys",jsonObject);
            place.setCountry(Utils.getString("country",sysObj));
            place.setCity(Utils.getString("name",jsonObject));
            place.setLastupdate(Utils.getInt("dt",jsonObject));
            place.setSunrise(Utils.getInt("sunrise",sysObj));
            place.setSunset(Utils.getInt("sunset",sysObj));
            weather.mPlace = place;



            /*-----------parse the current condition object ----------*/

            CurrentCondition currentCondition = new CurrentCondition();


            //weather array
            JSONArray jsonArray = jsonObject.getJSONArray("weather");
            JSONObject weatherObj = jsonArray.getJSONObject(0);
            currentCondition.setWeatherId(Utils.getInt("id",weatherObj));
            currentCondition.setCondition(Utils.getString("main",weatherObj));
            currentCondition.setDescription(Utils.getString("description",weatherObj));
            currentCondition.setIcon(Utils.getString("icon",weatherObj));

            //main object
            JSONObject mainObj = Utils.getObject("main",jsonObject);
            currentCondition.setHumidity(Utils.getFloat("humidity",mainObj));
            currentCondition.setPressure(Utils.getFloat("pressure",mainObj));
            currentCondition.setTemperature(Utils.getDouble("temp",mainObj));
            currentCondition.setMaxTemp(Utils.getFloat("temp_max",mainObj));
            currentCondition.setMinTemp(Utils.getFloat("temp_min",mainObj));



            weather.mCurrentCondition = currentCondition;
            Log.d("TEMPERATURE ",weather.mCurrentCondition.getTemperature() + "");

            /*--------------parse cloud object----------------------*/

            Clouds clouds = new Clouds();
            clouds.setPrecipitation(jsonObject.getJSONObject("clouds").getInt("all"));
            weather.mClouds = clouds;

            /*-----------------parse wind object----------------*/
            Wind wind = new Wind();
            wind.setDeg(((float) jsonObject.getJSONObject("wind").getDouble("deg")));
            wind.setSpeed(((float) jsonObject.getJSONObject("wind").getDouble("speed")));
            weather.mWind = wind;

            Log.d("Main_temperature",weather.mTemperature.getTemp() + "");


            return weather;

        } catch (JSONException e) {
            e.printStackTrace();

            return null;
        }

    }
}
