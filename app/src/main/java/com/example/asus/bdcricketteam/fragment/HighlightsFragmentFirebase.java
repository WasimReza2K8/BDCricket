package com.example.asus.bdcricketteam.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asus.bdcricketteam.HighlightsVideoActivity;
import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.datamodel.HighlightsDataModel;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.example.asus.bdcricketteam.viewholder.HighLightsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by ASUS on 8/28/2016.
 */
public class HighlightsFragmentFirebase extends Fragment {
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<HighlightsDataModel, HighLightsViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private ProgressBar mProgressBar;
    private LinearLayoutManager mManager;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if (mDatabase == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            // database.setPersistenceEnabled(true);
            mDatabase = database.getReference();
        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.upcoming_tournament, container, false);
            mRecycler = (RecyclerView) rootView.findViewById(R.id.fixture_recycler_view);
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
        }
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        Log.e("Query", postsQuery.toString() + " null");
        mAdapter = new FirebaseRecyclerAdapter<HighlightsDataModel, HighLightsViewHolder>(HighlightsDataModel.class, R.layout.highlights_item,
                HighLightsViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final HighLightsViewHolder viewHolder, final HighlightsDataModel model, final int position) {
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                mProgressBar.setVisibility(View.GONE);
                viewHolder.bindToPost(model, getActivity());
                viewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToYouTube(model);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    private void goToYouTube(final HighlightsDataModel model) {
        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
            Intent i = new Intent(getActivity(), HighlightsVideoActivity.class);
            i.putExtra("link", model.getLink());
            startActivity(i);
        } else {
            noInternetConnectionAlertDialog();
        }
    }

    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("highlights");
        return recentPostsQuery;
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

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }
}
