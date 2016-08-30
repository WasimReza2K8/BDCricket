package com.example.asus.bdcricketteam.viewholder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 8/28/2016.
 */
public class FixtureViewHolder extends RecyclerView.ViewHolder {

    public ImageView mFlag1, mFlag2;
    public TextView mTextViewMatchNumber, mTextViewMatchBetween,
            mTextViewVenue, mTextViewTime, mTextViewDate, mTextViewResult;

    public FixtureViewHolder(View v) {
        super(v);
        mTextViewMatchBetween = (TextView) v.findViewById(R.id.between);
        mTextViewMatchNumber = (TextView) v.findViewById(R.id.matchNumber);
        mTextViewVenue = (TextView) v.findViewById(R.id.venue);
        mTextViewTime = (TextView) v.findViewById(R.id.time);
        mTextViewDate = (TextView) v.findViewById(R.id.date);
        mTextViewResult = (TextView) v.findViewById(R.id.result);
        mFlag1 = (ImageView) v.findViewById(R.id.flagTeam1);
        mFlag2 = (ImageView) v.findViewById(R.id.flagTeam2);
    }

    public void bindToPost(final FixtureDataModel post, /*View.OnClickListener starClickListener,*/ final Context context) {
        mTextViewResult.setVisibility(View.GONE);
        mFlag1.setVisibility(View.GONE);
        mFlag2.setVisibility(View.GONE);
        if (post.getTournament() == null
                || post.getTournament().equalsIgnoreCase("")) {
            if (post.getMatchNumber() != null && !post.getMatchNumber().equals("")) {
                mTextViewMatchNumber.setText(context.getResources().getString(R.string.matchnumber)
                        + ": " + post.getMatchNumber());
                mTextViewMatchNumber.setVisibility(View.VISIBLE);
            } else {
                mTextViewMatchNumber.setVisibility(View.INVISIBLE);
            }

        } else {
            mTextViewMatchNumber.setText(post.getTournament());
            mTextViewMatchNumber.setVisibility(View.VISIBLE);
        }
        mTextViewMatchBetween.setText(post.getBetween());
        mTextViewVenue.setText(post.getVenue());
        mTextViewTime.setText(post.getTime());
        mTextViewDate.setText(post.getDate());
        if (post.getResult() != null
                && !post.getResult().equalsIgnoreCase("")) {
            mTextViewResult.setText(context.getResources().getString(R.string.result) + ": "
                    + post.getResult());
            mTextViewResult.setVisibility(View.VISIBLE);
        }

        if (post.getFlag1() != null && !post.getFlag1().equals("")
                && post.getFlag2() != null && !post.getFlag2().equals("")) {
            Picasso.with(context)
                    .load(post.getFlag1())
                    .placeholder(R.mipmap.white_flag) // can also be a drawable
                    .error(R.mipmap.white_flag) // will be displayed if the image cannot be loaded
                    .into(mFlag1);
            Picasso.with(context)
                    .load(post.getFlag2())
                    .placeholder(R.mipmap.white_flag) // can also be a drawable
                    .error(R.mipmap.white_flag) // will be displayed if the image cannot be loaded
                    .into(mFlag2);
            mFlag1.setVisibility(View.VISIBLE);
            mFlag2.setVisibility(View.VISIBLE);
        }
    }

}
