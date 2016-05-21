package com.example.asus.bdcricketteam;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.bdcricketteam.adapter.FixtureAdapter;
import com.example.asus.bdcricketteam.analytics.ApplicationAnalytics;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ASUS on 2/6/2016.
 */
public class UpcomingTournament extends Fragment {
    private ArrayList<FixtureDataModel> list;
    private FixtureAdapter adapter;
    private RecyclerView mRecyclerView;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.upcoming_tournament, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.fixture_recycler_view);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //TextView textView = (TextView) rootView.findViewById(R.id.text);
        //Database.init(getActivity());
        //List<FixtureDataModel> fixture = Database.getAll(Database.NATIONAL_TEAM_FIXTURE_TABLE);
        //textView.setText(SecureProcessor.onDecrypt(fixture.get(0).getBetween()));
        setRecyclerView();
        ApplicationAnalytics application = (ApplicationAnalytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        return rootView;
    }


    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getAll(Database.UPCOMING_TOURNAMENT_FIXTURE);
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
