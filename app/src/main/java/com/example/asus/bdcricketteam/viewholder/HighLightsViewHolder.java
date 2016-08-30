package com.example.asus.bdcricketteam.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.HighlightsDataModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 8/28/2016.
 */
public class HighLightsViewHolder extends RecyclerView.ViewHolder {
    public CardView itemCard;
    public ImageView mNewsImage;
    public TextView mTextViewTitle, mTextViewDuration;
    ProgressBar mProgressBar;

    public HighLightsViewHolder(View v) {
        super(v);
        mTextViewTitle = (TextView) v.findViewById(R.id.newsTitle);
        mTextViewDuration = (TextView) v.findViewById(R.id.duration);
        mNewsImage = (ImageView) v.findViewById(R.id.newsImage);
        itemCard = (CardView) v.findViewById(R.id.item_card);
        mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarLoading);
    }

    public void bindToPost(final HighlightsDataModel post, final Context context) {
        mTextViewTitle.setText(post.getTitle());
        mTextViewDuration.setText(post.getDuration());
        mTextViewDuration.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        String link = post.getLink();
        String fullLink = "http://img.youtube.com/vi/" + link + "/0.jpg";
        Picasso.with(context)
                .load(fullLink)
                .placeholder(R.mipmap.app_icon) // can also be a drawable
                .error(R.mipmap.app_icon) // will be displayed if the image cannot be loaded
                .resize(100, 100)
                .centerCrop()
                .into(mNewsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        mProgressBar.setVisibility(View.GONE);
                        mTextViewDuration.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }
}
