package com.junaid.simpleweather.Model;

/**
 * Created by Junaid on 23-12-2016.
 */

public class Weather {
    public Place mPlace;
    public String iconData;
    public CurrentCondition mCurrentCondition = new CurrentCondition();
    public Temperature mTemperature = new Temperature();
    public Wind mWind  = new Wind();
    public Snow mSnow = new Snow();
    public Clouds mClouds = new Clouds();
}
