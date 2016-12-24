package com.junaid.simpleweather;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.junaid.simpleweather.Data.CitySelection;
import com.junaid.simpleweather.Data.JSONParser;
import com.junaid.simpleweather.Data.WeatherHTTPClient;
import com.junaid.simpleweather.Model.Weather;
import com.junaid.simpleweather.Utils.Utils;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView mCityName,mTemperature,mWind,mCondition,mPressure,mHumidity,mMaxTemp,mMinTemp,mUpdated;
    private ImageView mIconView;
    CitySelection mCitySelection;
    Weather mWeather;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCityName = (TextView) findViewById(R.id.city_text);
        mTemperature = (TextView) findViewById(R.id.temperature_text);
        mWind = (TextView) findViewById(R.id.wind_text);
        mCondition = (TextView) findViewById(R.id.condition_text);
        mPressure = (TextView) findViewById(R.id.pressure_text);
       // mHumidity = (TextView) findViewById(R.id.humidity_text);
        mMaxTemp = (TextView) findViewById(R.id.sunrise_text);
        mMinTemp = (TextView) findViewById(R.id.sunset_text);

        mUpdated = (TextView) findViewById(R.id.update_text);

        mIconView = (ImageView) findViewById(R.id.thumbnail_icon);

        mCitySelection  = new CitySelection(MainActivity.this);

        getData(mCitySelection.getCity());

    }

    private void getData(String city){
        new BackgroundTasks().execute(city);
    }


    private class BackgroundTasks extends AsyncTask<String, Void, Weather> {

        @Override
        protected Weather doInBackground(String... params) {

            String data = new WeatherHTTPClient().getWeatherData(params[0]);
            if(data == null) {
                Looper.prepare();
                Toast.makeText(MainActivity.this, "City Not Found", Toast.LENGTH_SHORT).show();
            }


            mWeather = JSONParser.getWeather(data);
//            Log.v("Data: ", mWeather.mPlace.getCountry());
            // Log.d("Main_temperature",mWeather.mTemperature.getTemp() + "");

            return mWeather;
        }

        @Override
        protected void onPostExecute(Weather weather) {
            super.onPostExecute(weather);

            if(weather == null)
                return;

            DecimalFormat df = new DecimalFormat("#.#");

            mWeather.iconData = mWeather.mCurrentCondition.getIcon();

            mWind.setText("Wind: " + mWeather.mWind.getSpeed() + " mps");
            mCityName.setText(mWeather.mPlace.getCity() + ", " + mWeather.mPlace.getCountry());
            mTemperature.setText("" + df.format(mWeather.mCurrentCondition.getTemperature() - 273.15) + " C");

          //  mHumidity.setText("Humidity: " + mWeather.mCurrentCondition.getHumidity() + " %");
            mPressure.setText("Pressure: " + mWeather.mCurrentCondition.getPressure() + "hPA");
            mMaxTemp.setText("Maximum : " + df.format(mWeather.mCurrentCondition.getMaxTemp() - 273.15) + "");
            mMinTemp.setText("Minimum : " + df.format(mWeather.mCurrentCondition.getMinTemp() - 273.15) + "" );
            mUpdated.setText("Last Updated " + getDate(new Date(mWeather.mPlace.getLastupdate())));
            mCondition.setText("Condition: " + mWeather.mCurrentCondition.getCondition() +  "");

            new DownloadImage().execute(mWeather.iconData);
        }
    }

        private class DownloadImage extends AsyncTask<String, Void, Bitmap> {
            @Override
            protected Bitmap doInBackground(String... params) {
                String imageURL = Utils.ICON_URL + params[0] + ".png";
                Bitmap bitmap = null;
                try {
                    // Download Image from URL
                    InputStream input = new java.net.URL(imageURL).openStream();
                    // Decode Bitmap
                    bitmap = BitmapFactory.decodeStream(input);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return bitmap;
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                mIconView.setImageBitmap(bitmap);
            }
        }

    private void openDialog(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle("Change City");

        final EditText text = new EditText(MainActivity.this);
        text.setInputType(InputType.TYPE_CLASS_TEXT);
        text.setHint("Eg: Vadodara");
        dialog.setView(text);
        dialog.setPositiveButton("Select", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // CitySelection citySelection = new CitySelection(MainActivity.this);
                String s = text.getText().toString();

                if(s.isEmpty()){
                    Toast.makeText(MainActivity.this,"No Selection",Toast.LENGTH_LONG).show();
                    return;
                }

                mCitySelection.setCity(s);



                getData(mCitySelection.getCity());
            }
        });
        dialog.show();

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if(id == R.id.action_change_city){
            openDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getDate(Date date){
        DateFormat df = DateFormat.getTimeInstance();
        return df.format(date);
    }
}
