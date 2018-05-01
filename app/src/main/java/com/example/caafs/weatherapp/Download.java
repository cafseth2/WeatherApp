package com.example.caafs.weatherapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.Double.parseDouble;

public class Download extends AsyncTask<String,Void,String> {
    @Override
    protected String doInBackground(String... URL) {
        String endResult = "";
        URL url;
        HttpURLConnection connection = null;
        try {
            url = new URL(URL[0]);
            connection = (HttpURLConnection) url.openConnection();
            InputStream x = connection.getInputStream();
            InputStreamReader read = new InputStreamReader(x);
            int result = read.read();
            while (result != -1) {
                char curr = (char) result;
                endResult += curr;
                result = read.read();
            }
            return endResult;
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    void execute(String endResult) {
        super.onPostExecute(endResult);
        try {
            JSONObject object = new JSONObject(endResult);
            JSONObject weatherData = new JSONObject((object.getString("main")));
            double temp = parseDouble(weatherData.getString("temp"));
            int fTemp = (int) (temp * 1.8 - 459.67);
            MainActivity.tempText.setText(String.valueOf(fTemp));
            String place = object.getString("name");
            MainActivity.placeText.setText(place);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
