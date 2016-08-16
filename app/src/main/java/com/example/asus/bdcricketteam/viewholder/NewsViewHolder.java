package com.example.asus.bdcricketteam.viewholder;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by ASUS on 8/15/2016.
 */
public class NewsViewHolder extends RecyclerView.ViewHolder {
    public ImageView mNewsImage, favIcon;
    public TextView mTextViewTitle, mTextViewDetail;
    private ProgressBar mProgressBar;
    public CardView itemCard;
    ArrayList<String> favItems = new ArrayList<>();

    public NewsViewHolder(View itemView) {
        super(itemView);
        mTextViewTitle = (TextView) itemView.findViewById(R.id.newsTitle);
        mTextViewDetail = (TextView) itemView.findViewById(R.id.newsDetail);
        mNewsImage = (ImageView) itemView.findViewById(R.id.newsImage);
        itemCard = (CardView) itemView.findViewById(R.id.item_card);
        mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressBarLoading);
        // mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarLoading);
    }

    public void bindToPost(final NewsDataModel post, /*View.OnClickListener starClickListener,*/ final Context context) {
        mTextViewTitle.setText(post.getTitle());
        mTextViewDetail.setText(post.getDetail());
        //mTextViewDetail.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        Log.e("imageLink",  post.getDetail()+"");
        Log.e("imageLink",  post.getImagelink()+"");

        Picasso.with(context)
                .load(post.getImagelink())
                .placeholder(R.mipmap.app_icon) // can also be a drawable
                .error(R.mipmap.app_icon) // will be displayed if the image cannot be loaded
                .resize(100, 100)
                .noFade()
                .centerCrop()
                .into(mNewsImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        }
                );

        // numStarsView.setText(String.valueOf(post.starCount));
        //  bodyView.setText(post.body);

        //starView.setOnClickListener(starClickListener);
    }
}

