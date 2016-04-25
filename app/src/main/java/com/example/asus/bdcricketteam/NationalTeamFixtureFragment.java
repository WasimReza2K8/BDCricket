package com.example.asus.bdcricketteam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.asus.bdcricketteam.adapter.FixtureAdapter;
import com.example.asus.bdcricketteam.adapter.SquadRecyclerAdapter;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.CareerDataModel;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2/6/2016.
 */
public class NationalTeamFixtureFragment extends Fragment {
    private ArrayList<FixtureDataModel> list;
    private FixtureAdapter adapter;
    private RecyclerView mRecyclerView;
    private String fileSchedule = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.national_team_fixture, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fixture_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //TextView textView = (TextView) rootView.findViewById(R.id.text);
        //Database.init(getActivity());
        //List<FixtureDataModel> fixture = Database.getAll(Database.NATIONAL_TEAM_FIXTURE_TABLE);
        //textView.setText(SecureProcessor.onDecrypt(fixture.get(0).getBetween()));
        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
           // new GetSchedule().execute();
        } else {
            setRecyclerView();
        }
        setRecyclerView();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }

                return false;
            }
        });
        return rootView;
    }
/*
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
                Database.init(getActivity().getApplicationContext());

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
                if (update == OnPreferenceManager.getInstance(getActivity()).getScheduleUpdate()) {
                    return;
                }
                OnPreferenceManager.getInstance(getActivity()).setScheduleUpdate(update);
                int numberOfNews = info.getInt("numberofmatch");
                if (numberOfNews < 1) {
                    return;
                }

                Database.init(getActivity());
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

            Database.init(getActivity());
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
                model.setResult(SecureProcessor.onEncrypt(result.trim()));
                Database.insertFixtureValues(model, Database.NATIONAL_TEAM_FIXTURE_TABLE);
                //career = news.getString("Career");
                // careerParser(career, id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        protected void onPostExecute(String result) {
            setRecyclerView();
            Log.e("final ", result);
        }


    }*/

    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getAll(Database.NATIONAL_TEAM_FIXTURE_TABLE);
    }

    public void setRecyclerView() {
        getAllDataFromDb();
        Log.e("list", list.size() + "");
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new FixtureAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        }

    }


}
