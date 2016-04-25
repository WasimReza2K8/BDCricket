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
import com.example.asus.bdcricketteam.security.SecureProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 1/1/2016.
 */
public class FixtureAdapter extends RecyclerView.Adapter<FixtureAdapter.ViewHolder> {
    private List<FixtureDataModel> list = new ArrayList<>();
    private Context mContext;


    public FixtureAdapter(Context context, List<FixtureDataModel> list) {
        this.list = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fixture_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewResult.setVisibility(View.GONE);
        if (list.get(position).getTournament() == null
                || list.get(position).getTournament().equalsIgnoreCase("")) {
            holder.mTextViewMatchNumber.setText(mContext.getResources().getString(R.string.matchnumber)
                    + ": " + SecureProcessor.onDecrypt(list.get(position).getMatchNumber()));
        } else {
            holder.mTextViewMatchNumber.setText(SecureProcessor.onDecrypt(list.get(position).getTournament()));
        }
        holder.mTextViewMatchBetween.setText(SecureProcessor.onDecrypt(list.get(position).getBetween()));
        holder.mTextViewVenue.setText(SecureProcessor.onDecrypt(list.get(position).getVenue()));
        holder.mTextViewTime.setText(SecureProcessor.onDecrypt(list.get(position).getTime()));
        // String[]country = list.get(position).getBetween().split("v");
        //holder.mFlag1.setImageBitmap(setFlag(country[0].trim()));
        // holder.mFlag2.setImageBitmap(setFlag(country[1].trim()));
        // holder.mTextViewMatchBetween.setText(SecureProcessor.onDecrypt(list.get(position).getBetween()));
        holder.mTextViewDate.setText(SecureProcessor.onDecrypt(list.get(position).getDate()));
        if (list.get(position).getResult() != null
                && !SecureProcessor.onDecrypt(list.get(position).getResult()).equalsIgnoreCase("")) {
            holder.mTextViewResult.setText(mContext.getResources().getString(R.string.result) + ": "
                    + SecureProcessor.onDecrypt(list.get(position).getResult()));
            holder.mTextViewResult.setVisibility(View.VISIBLE);
        }
        //Log.e("matchnumber", list.get(position).getMatchNumber()+"");

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mFlag1, mFlag2;
        public TextView mTextViewMatchNumber, mTextViewMatchBetween,
                mTextViewVenue, mTextViewTime, mTextViewDate, mTextViewResult;

        public ViewHolder(View v) {
            super(v);
            mTextViewMatchBetween = (TextView) v.findViewById(R.id.between);
            mTextViewMatchNumber = (TextView) v.findViewById(R.id.matchNumber);
            mTextViewVenue = (TextView) v.findViewById(R.id.venue);
            mTextViewTime = (TextView) v.findViewById(R.id.time);
            mTextViewDate = (TextView) v.findViewById(R.id.date);
            mTextViewResult = (TextView) v.findViewById(R.id.result);


        }
    }
}
