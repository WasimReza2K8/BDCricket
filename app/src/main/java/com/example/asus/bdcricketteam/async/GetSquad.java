package com.example.asus.bdcricketteam.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.CareerDataModel;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
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
public class GetSquad extends AsyncTask<Void, Void, String> {
    private Context mContext;
    private UIRefreshCallBack mUiRefreshCallBack;
    private String squadFileLink = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwOEk3VUx3LTIxZHc";

    public GetSquad(Context context, UIRefreshCallBack uiRefreshCallBack) {
        mContext = context;
        mUiRefreshCallBack = uiRefreshCallBack;
    }

    protected String doInBackground(Void... arg0) {
        try {
            // Create a URL for the desired page
            URL url = new URL(squadFileLink);
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
               // Log.e("string server", str);
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
            if (update == OnPreferenceManager.getInstance(mContext).getSquadUpdate()) {
                return;
            }
            OnPreferenceManager.getInstance(mContext).setSquadUpdate(update);
            int numberOfNews = info.getInt("numberofplayer");
            if (numberOfNews < 1) {
                return;
            }

            Database.init(mContext);
            Database.deleteAll(Database.SQUAD_TABLE);
            for (int i = 1; i <= numberOfNews; i++) {
                JSONObject player = reader.getJSONObject("player" + i);
                parseJSONInsertDatabase(player, i);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void parseJSONInsertDatabase(JSONObject news, int id) {
        String name, age, role, style, imageLink, career;
        SquadModel model = new SquadModel();

        Database.init(mContext);
        //Database.deleteAll(Database.SQUAD_TABLE);
        try {
            //model.setId(id);
            model.setPlayerNumber(String.valueOf(id));
            name = news.getString("name");
            model.setPlayerName(SecureProcessor.onEncrypt(name.trim()));
            age = news.getString("age");
            model.setAge(SecureProcessor.onEncrypt(age.trim()));
            role = news.getString("role");
            model.setRole(SecureProcessor.onEncrypt(role.trim()));
            style = news.getString("style");
            model.setStyle(SecureProcessor.onEncrypt(style.trim()));
            imageLink = news.getString("imageLink");
            model.setImageLink(imageLink);
            Database.insertSquadValues(model, Database.SQUAD_TABLE);
            career = news.getString("Career");
            careerParser(career, id);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void careerParser(String career, int id) {
        JSONObject reader = null;
        try {
            reader = new JSONObject(career);
            JSONObject test = reader.getJSONObject("test");
            Database.init(mContext);
            //Database.deleteAll(Database.TABLE_TEST);
            JSONCareerObjectParse(test, Database.TABLE_TEST, id);
            //Database.deleteAll(Database.TABLE_TEST);
            JSONObject ODI = reader.getJSONObject("ODI");
            JSONCareerObjectParse(ODI, Database.TABLE_ODI, id);
            JSONObject t20 = reader.getJSONObject("t20");
            JSONCareerObjectParse(t20, Database.TABLE_T20, id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void JSONCareerObjectParse(JSONObject career, String tableName, int id) {
        CareerDataModel model = new CareerDataModel();

        Database.init(mContext);
        try {
            // model.setId(id);
            model.setMatches(career.getInt("matchnumber"));
            model.setBattingInns(career.getInt("batInns"));
            model.setBattingRuns(career.getInt("batRuns"));
            model.setBattingHighestScore(career.getInt("batHighestScore"));
            model.setBattingAvg(career.getString("batAvg"));
            model.setBattingStrikeRate(career.getString("batStrikeRate"));
            model.setBattingHundreds(career.getInt("batHundreds"));
            model.setBattingFifties(career.getInt("batFifties"));
            model.setBowlingInns(career.getInt("ballInns"));
            model.setBowlingRuns(career.getInt("ballRuns"));
            model.setBowlingWickets(career.getInt("ballWkts"));
            model.setBowlingBestScore(career.getString("ballBestScore"));
            model.setBowlingAvg(career.getString("ballAvg"));
            model.setBowlingEcon(career.getString("ballEcon"));
            model.setBowlingStrikeRate(career.getString("ballStrikeRate"));
            model.setBowlingNumberOfFiveWickets(career.getInt("numberOfFiveWickets"));


            Database.insertCareerValues(model, tableName);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    protected void onPostExecute(String result) {
        //  setRecyclerView();
       /* mProgressBar.setProgress(70);
        new GetUpcomingTournament().execute();*/
        if (mUiRefreshCallBack != null) {
            mUiRefreshCallBack.onProgress(70);
        }
     //  Log.e("final ", result);
    }
}
