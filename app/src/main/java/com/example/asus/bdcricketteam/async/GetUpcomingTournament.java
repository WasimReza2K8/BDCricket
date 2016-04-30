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
public class GetUpcomingTournament extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private UIRefreshCallBack mUiRefreshCallBack;
    private String fileUpcomingTournament = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwaDZpLWJiZF9tY1E";

    public GetUpcomingTournament(Context context, UIRefreshCallBack uiRefreshCallBack) {
        mContext = context;
        mUiRefreshCallBack = uiRefreshCallBack;
    }

    protected String doInBackground(Void... arg0) {
        try {
            // Create a URL for the desired page
            URL url = new URL(fileUpcomingTournament);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String str;
            //Database.init(SplashScreen.this.getApplicationContext());

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
            if (update == OnPreferenceManager.getInstance(mContext).getTournamentUpdate()) {
                return;
            }
            OnPreferenceManager.getInstance(mContext).setTournamentUpdate(update);
            int numberOfNews = info.getInt("numberofmatch");
            String tournamentname = info.getString("tournamentname");

            if (tournamentname != null && !tournamentname.equalsIgnoreCase("")) {
                OnPreferenceManager.getInstance(mContext).setTournamentName(SecureProcessor.onEncrypt(tournamentname));
                Log.e("tournamentname", tournamentname);
            }
            if (numberOfNews < 1) {
                return;
            }
            Database.init(mContext);
            Database.deleteAll(Database.UPCOMING_TOURNAMENT_FIXTURE);
            for (int i = 1; i <= numberOfNews; i++) {
                JSONObject matches = reader.getJSONObject("match" + i);
                parseJSONInsertDatabase(matches, i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJSONInsertDatabase(JSONObject matches, int id) {
        String date, between, venue, time, result, tournament;
        FixtureDataModel model = new FixtureDataModel();

        Database.init(mContext);
        //     Database.deleteAll(Database.UPCOMING_TOURNAMENT_FIXTURE);
        try {
            //model.setId(id);
            model.setMatchNumber(SecureProcessor.onEncrypt(matches.getString("matchnumber").trim()));
            date = matches.getString("date");
            model.setDate(SecureProcessor.onEncrypt(date.trim()));
            between = matches.getString("between");
            model.setBetween(SecureProcessor.onEncrypt(between.trim()));
            venue = matches.getString("venue");
            model.setVenue(SecureProcessor.onEncrypt(venue.trim()));
            time = matches.getString("time");
            model.setTime(SecureProcessor.onEncrypt(time.trim()));
            // tournament = news.getString("tournament");
            //model.setTournament(SecureProcessor.onEncrypt(tournament.trim()));
            result = matches.getString("result");
            if (result == null || result.equalsIgnoreCase("")) {
                model.setResult(null);
            } else {
                model.setResult(SecureProcessor.onEncrypt(result.trim()));
            }

            Database.insertFixtureValues(model, Database.UPCOMING_TOURNAMENT_FIXTURE);
            //career = news.getString("Career");
            // careerParser(career, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    protected void onPostExecute(String result) {
        //setRecyclerView();
        /*mProgressBar.setProgress(100);
        //setRecyclerView();
        finish();
        startActivity(new Intent(SplashScreen.this, MainActivity.class));*/
        if (mUiRefreshCallBack != null) {
            mUiRefreshCallBack.onProgress(70);
        }
       // Log.e("final ", result);
    }
}
