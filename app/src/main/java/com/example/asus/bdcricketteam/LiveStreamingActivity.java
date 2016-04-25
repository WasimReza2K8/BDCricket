package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

/**
 * Created by ASUS on 3/12/2016.
 */
public class LiveStreamingActivity extends YouTubeBaseActivity
        implements YouTubePlayer.OnInitializedListener {
    //private YouTubePlayerView mPlayerView;
    private YouTubePlayerFragment youTubePlayerFragment;
    private AdView mAdView, mAdView2, mAdView3, mAdView4;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_live_streaming);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        mAdView = (AdView) findViewById(R.id.ad_view);
        mAdView2 = (AdView) findViewById(R.id.ad_view2);
        mAdView3 = (AdView) findViewById(R.id.ad_view3);
        mAdView4 = (AdView) findViewById(R.id.ad_view4);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        mAdView2.loadAd(adRequest);
        mAdView3.loadAd(adRequest);
        mAdView4.loadAd(adRequest);
        youTubePlayerFragment.initialize(getResources().getString(R.string.youtube_api_key), this);
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        if (!b) {

            // loadVideo() will auto play video
            // Use cueVideo() method, if you don't want to play it automatically
            youTubePlayer.loadVideo(OnPreferenceManager.getInstance(LiveStreamingActivity.this).getLiveStreamLink());

            // Hiding player controls
            //player.setPlayerStyle(PlayerStyle.CHROMELESS);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Toast.makeText(LiveStreamingActivity.this, "initialization failure", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        if (mAdView2 != null) {
            mAdView2.resume();
        }
        if (mAdView3 != null) {
            mAdView3.resume();
        }
        if (mAdView4 != null) {
            mAdView4.resume();
        }
        // requestNewInterstitial();
    }

   /* @Override
    public void onPause() {
        super.onPause();
        if (mAdView != null) {
            mAdView.destroy();
        }
        // requestNewInterstitial();
    }*/

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        if (mAdView2 != null) {
            mAdView2.destroy();
        }
        if (mAdView3 != null) {
            mAdView3.destroy();
        }
        if (mAdView4 != null) {
            mAdView4.destroy();
        }
        //GoogleAds.getGoogleAds(this).showInterstitial();
        super.onDestroy();
    }
}
