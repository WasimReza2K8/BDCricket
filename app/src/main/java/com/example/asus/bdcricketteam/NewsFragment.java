package com.example.asus.bdcricketteam;

import android.content.Intent;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.adapter.NewsRecyclerAdapter;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.onlclick.RecyclerItemClickListener;
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
public class NewsFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private NewsRecyclerAdapter adapter;
    private List<NewsDataModel> list;

    //https://drive.google.com/open?id=0B85b1FRNOEQwR2c2SGNVUHVFdXc
    //https://drive.google.com/open?id=0B85b1FRNOEQwR2c2SGNVUHVFdXc
    //https://drive.google.com/open?id=0B85b1FRNOEQwVFQzREV1WktLOTg
    //https://drive.google.com/open?id=0B85b1FRNOEQwTS1ZNGVENzdzTDA
    //https://drive.google.com/file/d/0B85b1FRNOEQwTS1ZNGVENzdzTDA/view?usp=sharing
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);
        mTextView = (TextView) rootView.findViewById(R.id.textViewLoading);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //new GetNews().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // list = new ArrayList<>();
        // adapter = new NewsRecyclerAdapter(getActivity(), list);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), NewsDetailActivity.class);
                i.putExtra("id", list.get(position).getId());
                startActivity(i);
            }
        }));
        setRecyclerView();
        return rootView;
    }


    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getAllNews();
    }

    private void setRecyclerView() {
        getAllDataFromDb();
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new NewsRecyclerAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        }

    }
}
