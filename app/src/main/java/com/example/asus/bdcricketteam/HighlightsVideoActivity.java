package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.widget.Toast;

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
    //private Tracker mTracker;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_highlights_video);
        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager()
                .findFragmentById(R.id.youtube_fragment);
        /*ApplicationAnalytics application = (ApplicationAnalytics) getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
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
}
