package com.example.asus.bdcricketteam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.LiveScoreDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 3/6/2016.
 */
public class LiveScoreListAdapter extends RecyclerView.Adapter<LiveScoreListAdapter.ViewHolder> {

    private List<LiveScoreDataModel> list = new ArrayList<>();
    private Context mContext;


    public LiveScoreListAdapter(Context context, List<LiveScoreDataModel> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public LiveScoreListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.livescoreitem, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LiveScoreListAdapter.ViewHolder holder, int position) {
        holder.mTextViewTitle.setText(list.get(position).getMatchTitle());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mFlag1, mFlag2;
        public TextView mTextViewTitle;

        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.liveScoreTitle);


        }
    }
}
