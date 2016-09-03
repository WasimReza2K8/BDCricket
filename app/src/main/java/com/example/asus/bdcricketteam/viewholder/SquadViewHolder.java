package com.example.asus.bdcricketteam.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 8/30/2016.
 */
public class SquadViewHolder extends RecyclerView.ViewHolder {
    public ImageView mImageViewPlayerImage;
    public CardView itemCard;
    public TextView mTextViewPlayerAge, mTextViewPlayerName, mTextViewPlayerRole, mTextViewPlayerStyle;

    public SquadViewHolder(View v) {
        super(v);
        mTextViewPlayerAge = (TextView) v.findViewById(R.id.playerAge);
        mTextViewPlayerRole = (TextView) v.findViewById(R.id.playerRole);
        mTextViewPlayerStyle = (TextView) v.findViewById(R.id.playerStyle);
        mTextViewPlayerName = (TextView) v.findViewById(R.id.playerName);
        mImageViewPlayerImage = (ImageView) v.findViewById(R.id.playerImage);
        itemCard = (CardView) v.findViewById(R.id.item_card);
    }
    public void bindToPost(final SquadModel post, /*View.OnClickListener starClickListener,*/ final Context context) {
        mTextViewPlayerAge.setText(context.getResources().getString(R.string.age) + ": "
                + (post.getAge()));
        mTextViewPlayerRole.setText(context.getResources().getString(R.string.role) + ": "
                + (post.getRole()));
        mTextViewPlayerStyle.setText(context.getResources().getString(R.string.style) + ": "
                + (post.getStyle()));
        mTextViewPlayerName.setText(post.getName());
        Picasso.with(context)
                .load(post.getImageLink())
                .into(mImageViewPlayerImage);
    }
}
