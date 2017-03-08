package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by ASUS on 4/30/2016.
 */
public class HighlightsVideoActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {

    private YouTubePlayerFragment youTubePlayerFragment;
    private String link;
    private AdView mAdView;
    //private Tracker mTracker;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_live_streaming);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        link = getIntent().getExtras().getString("link");
        youTubePlayerFragment.initialize(getResources().getString(R.string.youtube_api_key), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {
            youTubePlayer.loadVideo(link);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(HighlightsVideoActivity.this, "initialization failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }


    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }
}
