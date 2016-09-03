package com.example.asus.bdcricketteam.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ASUS on 2/16/2016.
 */
public class SquadRecyclerAdapter extends RecyclerView.Adapter<SquadRecyclerAdapter.ViewHolder> {

    private List<SquadModel> news;
    private Context mContext;


    public SquadRecyclerAdapter(Context context, List<SquadModel> list) {
        this.news = list;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.squad_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mTextViewPlayerAge.setText(mContext.getResources().getString(R.string.age) + ": "
                + (SecureProcessor.onDecrypt(news.get(position).getAge())));
        holder.mTextViewPlayerRole.setText(mContext.getResources().getString(R.string.role) + ": "
                + (SecureProcessor.onDecrypt(news.get(position).getRole())));
        holder.mTextViewPlayerStyle.setText(mContext.getResources().getString(R.string.style) + ": "
                + (SecureProcessor.onDecrypt(news.get(position).getStyle())));
        holder.mTextViewPlayerName.setText(SecureProcessor.onDecrypt(news.get(position).getName()));
        Picasso.with(mContext)
                .load(news.get(position).getImageLink())
                .into(holder.mImageViewPlayerImage);
    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mImageViewPlayerImage;
        public TextView mTextViewPlayerAge, mTextViewPlayerName, mTextViewPlayerRole, mTextViewPlayerStyle;

        public ViewHolder(View v) {
            super(v);
            mTextViewPlayerAge = (TextView) v.findViewById(R.id.playerAge);
            mTextViewPlayerRole = (TextView) v.findViewById(R.id.playerRole);
            mTextViewPlayerStyle = (TextView) v.findViewById(R.id.playerStyle);
            mTextViewPlayerName = (TextView) v.findViewById(R.id.playerName);
            mImageViewPlayerImage = (ImageView) v.findViewById(R.id.playerImage);
        }
    }

}
