package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.asus.bdcricketteam.analytics.ApplicationAnalytics;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
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
    private AdView mAdView;
    private Toolbar mToolbar;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.fragment_live_streaming);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        mAdView = (AdView) findViewById(R.id.ad_view);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        ApplicationAnalytics application = (ApplicationAnalytics) getApplication();
        mTracker = application.getDefaultTracker();
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
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
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

        //GoogleAds.getGoogleAds(this).showInterstitial();
        super.onDestroy();
    }
}
