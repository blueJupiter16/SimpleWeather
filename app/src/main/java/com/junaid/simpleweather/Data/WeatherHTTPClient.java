package com.junaid.simpleweather.Data;

import android.util.Log;
import android.widget.Toast;

import com.junaid.simpleweather.MainActivity;
import com.junaid.simpleweather.Utils.Utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Junaid on 23-12-2016.
 */

public class WeatherHTTPClient {

    public String getWeatherData(String place){

        HttpURLConnection connection = null;
        InputStream inputStream = null;
        String line = null;
        StringBuffer stringBuffer = null;
        BufferedReader bufferedReader = null;

        try {
            connection = (HttpURLConnection) new URL(Utils.BASE_URL + place + Utils.APPI_KEY).openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.connect();


            //Read data from URL
            stringBuffer = new StringBuffer();
            try {
                inputStream = connection.getInputStream();
            }catch(FileNotFoundException e){

                return null;
            }
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((line = bufferedReader.readLine()) != null){
                stringBuffer.append(line + "\r\n");

            }
            inputStream.close();
            connection.disconnect();

            return stringBuffer.toString();

        } catch (IOException e) {
            e.printStackTrace();

        };


        return null;



    }
}
