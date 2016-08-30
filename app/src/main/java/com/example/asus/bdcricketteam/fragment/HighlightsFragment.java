package com.example.asus.bdcricketteam.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.HighlightsVideoActivity;
import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.adapter.HighlightsRecyclerAdapter;

import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.HighlightsDataModel;
import com.example.asus.bdcricketteam.onlclick.RecyclerItemClickListener;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 4/30/2016.
 */
public class HighlightsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private HighlightsRecyclerAdapter adapter;
    private List<HighlightsDataModel> list;
    //private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highlights, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);
        mTextView = (TextView) rootView.findViewById(R.id.textViewLoading);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
                    Intent i = new Intent(getActivity(), HighlightsVideoActivity.class);
                    i.putExtra("link", list.get(position).getLink());
                    startActivity(i);
                } else {
                    noInternetConnectionAlertDialog();
                }

            }
        }));
        setRecyclerView();
       /* ApplicationAnalytics application = (ApplicationAnalytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/

        return rootView;
    }


    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getAllHighlights();
    }

    private void setRecyclerView() {
        getAllDataFromDb();
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new HighlightsRecyclerAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        }

    }

    public void noInternetConnectionAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.popup_title));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.popup_message_live))
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
                        if (OnPreferenceManager.getInstance(getActivity()).getIsFirstTime()) {
                            return;

                        } else {
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
