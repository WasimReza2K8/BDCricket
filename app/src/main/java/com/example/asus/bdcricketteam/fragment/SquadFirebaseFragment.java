package com.example.asus.bdcricketteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asus.bdcricketteam.LiveScoreActivity;
import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
import com.example.asus.bdcricketteam.viewholder.FixtureViewHolder;
import com.example.asus.bdcricketteam.viewholder.SquadViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by ASUS on 8/30/2016.
 */
public class SquadFirebaseFragment extends Fragment {
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<SquadModel, SquadViewHolder> mAdapter;
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
            rootView = inflater.inflate(R.layout.national_team_squard, container, false);
            mRecycler = (RecyclerView) rootView.findViewById(R.id.squadRecyclerView);
           // mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
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
        mAdapter = new FirebaseRecyclerAdapter<SquadModel, SquadViewHolder>(SquadModel.class, R.layout.squad_item,
                SquadViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final SquadViewHolder viewHolder, final SquadModel model, final int position) {
                // Bind Post to ViewHolder, setting OnClickListener for the star button
               // mProgressBar.setVisibility(View.GONE);
                viewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(getActivity(), LiveScoreActivity.class);
                        i.putExtra("link", model.getProfilelink());
                        i.putExtra("title", "player_details");
                        startActivity(i);
                        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
                    }
                });
                viewHolder.bindToPost(model, getActivity());
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("sqard");
        return recentPostsQuery;
    }
}
