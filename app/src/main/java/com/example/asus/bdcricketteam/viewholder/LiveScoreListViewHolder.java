package com.example.asus.bdcricketteam.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.LiveScoreDataModel;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 9/25/2016.
 */
public class LiveScoreListViewHolder extends RecyclerView.ViewHolder {
    private TextView mTextViewTitle;
    public CardView itemCard;

    public LiveScoreListViewHolder(View itemView) {
        super(itemView);
        itemCard = (CardView) itemView.findViewById(R.id.item_card);
        mTextViewTitle = (TextView) itemView.findViewById(R.id.liveScoreTitle);
    }

    public void bindToPost(final LiveScoreDataModel post, final Context context) {
        mTextViewTitle.setText(post.getTitle());
    }
}
