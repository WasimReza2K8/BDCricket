package com.example.asus.bdcricketteam.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ASUS on 3/12/2016.
 */
public class GetLiveStreamingLink extends AsyncTask<Void, Void, String> {
    private String streamingLink = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwMUYwWXlJNlByQTA";
    private Context mContext;

    public GetLiveStreamingLink(Context context) {
        mContext = context;
        //mUiRefreshCallBack = uiRefreshCallBack;
    }

    //  https://drive.google.com/file/d/0B85b1FRNOEQwMUYwWXlJNlByQTA/view?usp=sharing
    @Override
    protected String doInBackground(Void... params) {
        try {
            URL url = new URL(streamingLink);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            StringBuilder total = new StringBuilder();

            while ((str = in.readLine()) != null) {
                total.append(str);
            }
           // Log.e("livestreaming", total.toString());
            parseJSONGetData(total.toString());
            in.close();
        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return null;
    }

    public void parseJSONGetData(String total) {
        try {
            JSONObject reader = new JSONObject(total);
            JSONObject info = reader.getJSONObject("livestreaming");
            String link = info.getString("link");
            if (OnPreferenceManager.getInstance(mContext).getLiveStreamLink().equalsIgnoreCase(link)) {
                return;
            } else {
                OnPreferenceManager.getInstance(mContext).setLiveStreamLink(link);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
