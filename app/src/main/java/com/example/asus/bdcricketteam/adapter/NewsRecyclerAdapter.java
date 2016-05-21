package com.example.asus.bdcricketteam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ASUS on 2/8/2016.
 */
public class NewsRecyclerAdapter extends RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder> {
    private List<NewsDataModel> news;
    private Context mContext;


    public NewsRecyclerAdapter(Context context, List<NewsDataModel> list) {
        this.news = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mTextViewTitle.setText(SecureProcessor.onDecrypt(news.get(position).getTitle()));
        holder.mTextViewDetail.setText(SecureProcessor.onDecrypt(news.get(position).getFullNews()));
        holder.mProgressBar.setVisibility(View.VISIBLE);
        Picasso.with(mContext)
                .load(news.get(position).getImageLink())
                .placeholder(R.mipmap.app_icon) // can also be a drawable
                .error(R.mipmap.app_icon) // will be displayed if the image cannot be loaded
                .resize(100, 100)
                .noFade()
                .centerCrop()
                .into(holder.mNewsImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                holder.mProgressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        }
                );
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public CircleImageView mNewsImage;
        public TextView mTextViewTitle, mTextViewDetail;
        ProgressBar mProgressBar;

        public ViewHolder(View v) {
            super(v);
            mTextViewTitle = (TextView) v.findViewById(R.id.newsTitle);
            mTextViewDetail = (TextView) v.findViewById(R.id.newsDetail);
            mNewsImage = (CircleImageView) v.findViewById(R.id.newsImage);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressBarLoading);
        }
    }

}
