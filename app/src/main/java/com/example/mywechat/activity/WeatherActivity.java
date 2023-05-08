package com.example.mywechat.activity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mywechat.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class WeatherActivity extends AppCompatActivity {

    private static final String TAG = "TAG-All";

    //查询北京预报的url
    private static final String ALL_URL = "https://restapi.amap.com/v3/weather/weatherInfo?city=110105&key=ea3de24f90648448f4b5a69150e57645&extensions=all";

    // 天气控件
    private TextView
            cityNameTextView,
            reportTimeValueTextView,
            dayWeatherTextView,
            nightWeatherTextView,
            dayTempTextView,
            nightTempTextView,
            dayWindTextView,
            nightWindTextView,
            dayPowerTextView,
            nightPowerTextView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        // 获取控件
        cityNameTextView = findViewById(R.id.cityNameTextView);
        reportTimeValueTextView = findViewById(R.id.reportTimeValueTextView);
        dayWeatherTextView = findViewById(R.id.dayWeatherTextView);
        nightWeatherTextView = findViewById(R.id.nightWeatherTextView);
        dayTempTextView = findViewById(R.id.dayTempTextView);
        nightTempTextView = findViewById(R.id.nightTempTextView);
        dayWindTextView = findViewById(R.id.dayWindTextView);
        nightWindTextView = findViewById(R.id.nightWindTextView);
        dayPowerTextView = findViewById(R.id.dayPowerTextView);
        nightPowerTextView = findViewById(R.id.nightPowerTextView);
        // 发起异步网络请求获取天气信息
        new WeatherAsyncTask().execute();
    }

    @SuppressLint("StaticFieldLeak")
    private class WeatherAsyncTask extends AsyncTask<Void, Void, Map<String, String>> {

        @Override
        protected Map<String, String> doInBackground(Void... voids) {
            try {
                URL url = new URL(ALL_URL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);
                connection.connect();
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    bufferedReader.close();
                    inputStream.close();
                    connection.disconnect();
                    Log.d(TAG, "weather: " + responseBuilder);
                    return parseWeatherJson(responseBuilder.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onPostExecute(Map<String, String> weatherMap) {
            super.onPostExecute(weatherMap);
            if (weatherMap != null) {
                // 将天气信息填充到对应控件
                cityNameTextView.setText("地区：" + weatherMap.get("provinceName") + weatherMap.get("cityName"));
                reportTimeValueTextView.setText("更新时间：" + weatherMap.get("reportTime"));
                dayWeatherTextView.setText("白天天气：" + weatherMap.get("dayWeather"));
                nightWeatherTextView.setText("夜晚天气：" + weatherMap.get("nightWeather"));
                dayTempTextView.setText("白天温度：" + weatherMap.get("dayTemp") + "℃");
                nightTempTextView.setText("夜晚温度：" + weatherMap.get("nightTemp") + "℃");
                dayWindTextView.setText("白天风向：" + weatherMap.get("dayWind"));
                nightWindTextView.setText("夜晚风向：" + weatherMap.get("nightWind"));
                dayPowerTextView.setText("白天风级：" + weatherMap.get("dayPower"));
                nightPowerTextView.setText("夜晚风级：" + weatherMap.get("nightPower"));

            } else {
                // 请求失败提示
                Log.d(TAG, "获取天气信息失败");
            }
        }

        private Map<String, String> parseWeatherJson(String jsonStr) {
            Map<String, String> weatherMap = new HashMap<>();
            try {
                JSONObject json = new JSONObject(jsonStr);
                JSONArray forecasts = json.getJSONArray("forecasts");
                JSONObject forecast = forecasts.getJSONObject(0);
                JSONArray casts = forecast.getJSONArray("casts");
                JSONObject todayWeather = casts.getJSONObject(0);

                String cityName = forecast.getString("city");
                String provinceName = forecast.getString("province");
                String reportTime = forecast.getString("reporttime");
                String dayWeather = todayWeather.getString("dayweather");
                String nightWeather = todayWeather.getString("nightweather");
                String dayTemp = todayWeather.getString("daytemp");
                String nightTemp = todayWeather.getString("nighttemp");
                String dayWind = todayWeather.getString("daywind");
                String nightWind = todayWeather.getString("nightwind");
                String dayPower = todayWeather.getString("daypower");
                String nightPower = todayWeather.getString("nightpower");
                weatherMap.put("cityName", cityName);
                weatherMap.put("provinceName", provinceName);
                weatherMap.put("reportTime", reportTime);
                weatherMap.put("dayWeather", dayWeather);
                weatherMap.put("nightWeather", nightWeather);
                weatherMap.put("dayTemp", dayTemp);
                weatherMap.put("nightTemp", nightTemp);
                weatherMap.put("dayWind", dayWind);
                weatherMap.put("nightWind", nightWind);
                weatherMap.put("dayPower", dayPower);
                weatherMap.put("nightPower", nightPower);
            } catch (JSONException e) {
                Log.e(TAG, "parseWeatherJson: " + e.getMessage());
            }
            return weatherMap;
        }

    }
}