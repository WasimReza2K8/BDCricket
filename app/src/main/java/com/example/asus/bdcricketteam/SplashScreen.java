package com.example.asus.bdcricketteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.audiofx.BassBoost;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.example.asus.bdcricketteam.async.GetHighlightsData;
import com.example.asus.bdcricketteam.async.GetLiveStreamingLink;
import com.example.asus.bdcricketteam.async.GetNews;
import com.example.asus.bdcricketteam.async.GetSchedule;
import com.example.asus.bdcricketteam.async.GetSquad;
import com.example.asus.bdcricketteam.async.GetUpcomingTournament;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.CareerDataModel;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
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
import java.util.concurrent.TimeUnit;

/**
 * Created by ASUS on 2/15/2016.
 */
public class SplashScreen extends AppCompatActivity {
    private ProgressBar mProgressBar;
    public UIRefreshCallBack uiRefreshCallBack;
    private String newsFileURL = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";
    public static String newsFileURL2 = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwTS1ZNGVENzdzTDA";
    private String fileSchedule = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";
    private String squadFileLink = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwOEk3VUx3LTIxZHc";
    private String fileUpcomingTournament = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwaDZpLWJiZF9tY1E";
    private static int SPLASH_TIME_OUT = 2000;

    //https://drive.google.com/file/d/0B85b1FRNOEQwTS1ZNGVENzdzTDA/view?usp=sharing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBarLoading);

        //getUpdate();
        //String result = (String) DateUtils.getRelativeTimeSpanString(1306767830, 1306767835, 0);
        // Log.e("time", result);


    }

    @Override
    protected void onResume() {
        super.onResume();
        uiRefreshCallBack = new UIRefreshCallBack() {
            @Override
            public void onProgress(int progress) {
                mProgressBar.setProgress(progress);
                if (progress == 100) {
                    finish();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }
        };
        getUpdate();
    }

    public void getUpdate() {

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
            int hour = 0;
            if (OnPreferenceManager.getInstance(this).getIsFirstTime()) {
                OnPreferenceManager.getInstance(this).setIsFirstTime(false);
                OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                //new GetNews().execute();
                loadData();
                return;
            } else {
                Long timeDifference = System.currentTimeMillis() - OnPreferenceManager.getInstance(this).getDate();
                hour = (int) (timeDifference / (60 * 1000));

                if (hour > 10) {
                    // new GetNews().execute();
                    loadData();
                    OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                    // return;
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            // mProgressBar.setProgress(100);
                            Intent i = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(i);

                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);


                }
                OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                if (OnPreferenceManager.getInstance(this).getIsFirstTime()) {
                    OnPreferenceManager.getInstance(this).setIsFirstTime(false);
                }
            }

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle(getResources().getString(R.string.popup_title));

            // set dialog message
            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.popup_message))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, close
                            // current activity
                            //MainActivity.this.finish();
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            if (OnPreferenceManager.getInstance(SplashScreen.this).getIsFirstTime()) {
                                return;

                            } else {
                                //new GetNews().execute();
                                loadData();
                                dialog.cancel();
                                return;
                            }

                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }
    }

    private void loadData() {
        new GetNews(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetSchedule(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetSquad(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetUpcomingTournament(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetLiveStreamingLink(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetHighlightsData(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    /*class GetNews extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
            //showEmptyLayout();
            // mProgressBar.setVisibility(View.VISIBLE);
            mProgressBar.setProgress(10);
        }

        protected String doInBackground(Void... arg0) {
            try {
                // Create a URL for the desired page
                URL url = new URL(newsFileURL2);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                //if (count == 0) {

                //}
                StringBuilder total = new StringBuilder();

                while ((str = in.readLine()) != null) {
                    total.append(str);
                }
                Log.e("string total", total.toString());
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
                int numberOfNews = info.getInt("numberofnews");
                int updateNews = info.getInt("update");
                if (OnPreferenceManager.getInstance(SplashScreen.this).getNewsUpdate() == updateNews) {
                    return;
                }
                OnPreferenceManager.getInstance(SplashScreen.this).setNewsUpdate(updateNews);
                // Database.init(SplashScreen.this);

                if (numberOfNews < 1) {
                    return;
                }

                Database.init(SplashScreen.this);
                Database.deleteAll(Database.NEWS_TABLE);
                for (int i = 1; i <= numberOfNews; i++) {
                    JSONObject news1 = reader.getJSONObject("news" + i);
                    parseJSONInsertDatabase(news1);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void parseJSONInsertDatabase(JSONObject news) {
            String title, detail, imageLink;
            NewsDataModel model = new NewsDataModel();

            Database.init(SplashScreen.this);
            //Database.deleteAll(Database.NEWS_TABLE);
            try {
                title = news.getString("title");
                model.setTitle(SecureProcessor.onEncrypt(title.trim()));
                detail = news.getString("detail");
                model.setFullNews(SecureProcessor.onEncrypt(detail.trim()));
                imageLink = news.getString("imagelink");
                model.setImageLink(imageLink);
                Database.insertNewsValues(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        protected void onPostExecute(String result) {
            mProgressBar.setProgress(30);
            //setRecyclerView();
            //finish();
            //startActivity(new Intent(SplashScreen.this, MainActivity.class));
            new GetSchedule().execute();

        }

    }

    class GetSchedule extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
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
                Database.init(SplashScreen.this.getApplicationContext());

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
                if (update == OnPreferenceManager.getInstance(SplashScreen.this).getScheduleUpdate()) {
                    return;
                }
                OnPreferenceManager.getInstance(SplashScreen.this).setScheduleUpdate(update);
                int numberOfNews = info.getInt("numberofmatch");
                if (numberOfNews < 1) {
                    return;
                }

                Database.init(SplashScreen.this);
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

            Database.init(SplashScreen.this);
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
            mProgressBar.setProgress(50);
            new GetSquad().execute();
            Log.e("final ", result);
        }


    }

    class GetSquad extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
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
                Database.init(SplashScreen.this.getApplicationContext());

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
                if (update == OnPreferenceManager.getInstance(SplashScreen.this).getSquadUpdate()) {
                    return;
                }
                OnPreferenceManager.getInstance(SplashScreen.this).setSquadUpdate(update);
                int numberOfNews = info.getInt("numberofplayer");
                if (numberOfNews < 1) {
                    return;
                }

                Database.init(SplashScreen.this);
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

            Database.init(SplashScreen.this);
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
                Database.init(SplashScreen.this);
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

            Database.init(SplashScreen.this);
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
            mProgressBar.setProgress(70);
            new GetUpcomingTournament().execute();
            Log.e("final ", result);
        }
    }

    class GetUpcomingTournament extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
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
                Database.init(SplashScreen.this.getApplicationContext());

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
                if (update == OnPreferenceManager.getInstance(SplashScreen.this).getTournamentUpdate()) {
                    return;
                }
                OnPreferenceManager.getInstance(SplashScreen.this).setTournamentUpdate(update);
                int numberOfNews = info.getInt("numberofmatch");
                String tournamentname = info.getString("tournamentname");

                if (tournamentname != null && !tournamentname.equalsIgnoreCase("")) {
                    OnPreferenceManager.getInstance(SplashScreen.this).setTournamentName(SecureProcessor.onEncrypt(tournamentname));
                    Log.e("tournamentname", tournamentname);
                }
                if (numberOfNews < 1) {
                    return;
                }
                Database.init(SplashScreen.this);
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

            Database.init(SplashScreen.this);
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
            mProgressBar.setProgress(100);
            //setRecyclerView();
            finish();
            startActivity(new Intent(SplashScreen.this, MainActivity.class));
            Log.e("final ", result);
        }


    }
*/
}
