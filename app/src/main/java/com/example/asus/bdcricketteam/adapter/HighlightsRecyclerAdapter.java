package com.example.asus.bdcricketteam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.HighlightsDataModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 4/30/2016.
 */
public class HighlightsRecyclerAdapter extends RecyclerView.Adapter<HighlightsRecyclerAdapter.ViewHolder> {
    private List<HighlightsDataModel> news;
    private Context mContext;


    public HighlightsRecyclerAdapter(Context context, List<HighlightsDataModel> list) {
        this.news = list;
        this.mContext = context;
    }

    @Override
    public HighlightsRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.highlights_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final HighlightsRecyclerAdapter.ViewHolder holder, int position) {
        holder.mTextViewTitle.setText(SecureProcessor.onDecrypt(news.get(position).getTitle()));
        holder.mTextViewDuration.setText(SecureProcessor.onDecrypt(news.get(position).getDuration()));
        holder.mTextViewDuration.setVisibility(View.GONE);
        holder.mProgressBar.setVisibility(View.VISIBLE);
        String link = news.get(position).getLink();
        String fullLink = "http://img.youtube.com/vi/" + link + "/0.jpg";
        Picasso.with(mContext)
                .load(fullLink)
                .placeholder(R.mipmap.app_icon) // can also be a drawable
                .error(R.mipmap.app_icon) // will be displayed if the image cannot be loaded
                .resize(100, 100)
                .centerCrop()
                .into(holder.mNewsImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.mProgressBar.setVisibility(View.GONE);
                        holder.mTextViewDuration.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mNewsImage;
        public TextView mTextViewTitle, mTextViewDuration;
        ProgressBar mProgressBar;

        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.newsTitle);
            mTextViewDuration = (TextView) v.findViewById(R.id.duration);
            mNewsImage = (ImageView) v.findViewById(R.id.newsImage);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarLoading);
        }
    }
}
