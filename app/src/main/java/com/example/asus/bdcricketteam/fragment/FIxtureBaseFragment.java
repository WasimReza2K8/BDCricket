package com.example.asus.bdcricketteam.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.viewholder.FixtureViewHolder;
import com.example.asus.bdcricketteam.viewholder.NewsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * Created by ASUS on 8/28/2016.
 */
public abstract class FixtureBaseFragment extends Fragment {
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<FixtureDataModel, FixtureViewHolder> mAdapter;
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
        mAdapter = new FirebaseRecyclerAdapter<FixtureDataModel, FixtureViewHolder>(FixtureDataModel.class, R.layout.fixture_item,
                FixtureViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final FixtureViewHolder viewHolder, final FixtureDataModel model, final int position) {
                // Bind Post to ViewHolder, setting OnClickListener for the star button
                mProgressBar.setVisibility(View.GONE);
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

    public abstract Query getQuery(DatabaseReference databaseReference);


}
