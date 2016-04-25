package com.example.asus.bdcricketteam.async;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.interfaceui.UIRefreshCallBack;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.example.asus.bdcricketteam.security.SecureProcessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ASUS on 3/5/2016.
 */
public class GetSchedule extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private UIRefreshCallBack mUiRefreshCallBack;
    private String fileSchedule = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";

    public GetSchedule(Context context, UIRefreshCallBack uiRefreshCallBack) {
        mContext = context;
        mUiRefreshCallBack = uiRefreshCallBack;
    }

    protected String doInBackground(Void... arg0) {
        try {
            // Create a URL for the desired page
            URL url = new URL(fileSchedule);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            Database.init(mContext.getApplicationContext());

            StringBuilder total = new StringBuilder();
            while ((str = in.readLine()) != null) {
                //stringDivideDataEntry(str.trim());
                total.append(str);
                Log.e("string server", str);
            }
            parseJSONGetData(total.toString());
            in.close();
            return total.toString().trim();

        } catch (MalformedURLException e) {
        } catch (IOException e) {
        } catch (Exception e) {
        }
        return "";
    }

    public void parseJSONGetData(String total) {
        try {
            JSONObject reader = new JSONObject(total);
            JSONObject info = reader.getJSONObject("info");
            int update = info.getInt("update");
            if (update == OnPreferenceManager.getInstance(mContext).getScheduleUpdate()) {
                return;
            }
            OnPreferenceManager.getInstance(mContext).setScheduleUpdate(update);
            int numberOfNews = info.getInt("numberofmatch");
            if (numberOfNews < 1) {
                return;
            }

            Database.init(mContext);
            Database.deleteAll(Database.NATIONAL_TEAM_FIXTURE_TABLE);
            for (int i = 1; i <= numberOfNews; i++) {
                JSONObject matches = reader.getJSONObject("match" + i);
                parseJSONInsertDatabase(matches);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJSONInsertDatabase(JSONObject news) {
        String date, between, venue, time, result, tournament;
        FixtureDataModel model = new FixtureDataModel();

        Database.init(mContext);
        //Database.deleteAll(Database.NATIONAL_TEAM_FIXTURE_TABLE);
        try {
            //model.setId(id);
            //model.setPlayerNumber(String.valueOf(id));
            date = news.getString("date");
            model.setDate(SecureProcessor.onEncrypt(date.trim()));
            between = news.getString("between");
            model.setBetween(SecureProcessor.onEncrypt(between.trim()));
            venue = news.getString("venue");
            model.setVenue(SecureProcessor.onEncrypt(venue.trim()));
            time = news.getString("time");
            model.setTime(SecureProcessor.onEncrypt(time.trim()));
            tournament = news.getString("tournament");
            model.setTournament(SecureProcessor.onEncrypt(tournament.trim()));
            result = news.getString("result");
            if (result == null || result.equalsIgnoreCase("")) {
                model.setResult(null);
            } else {
                model.setResult(SecureProcessor.onEncrypt(result.trim()));
            }
            Database.insertFixtureValues(model, Database.NATIONAL_TEAM_FIXTURE_TABLE);
            //career = news.getString("Career");
            // careerParser(career, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    protected void onPostExecute(String result) {
        // setRecyclerView();
       /* mProgressBar.setProgress(50);
        new GetSquad().execute();*/
        if (mUiRefreshCallBack != null) {
            mUiRefreshCallBack.onProgress(30);
        }
        Log.e("final ", result);
    }
}
