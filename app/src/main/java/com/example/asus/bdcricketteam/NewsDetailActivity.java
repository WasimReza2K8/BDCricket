package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.ads.GoogleAds;
import com.example.asus.bdcricketteam.analytics.ApplicationAnalytics;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 2/28/2016.
 */
public class NewsDetailActivity extends AppCompatActivity {
    private AdView mAdView;
    private Toolbar mToolbar;
    private Tracker mTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        int id = getIntent().getIntExtra("id", -1);
        TextView title, detail;
        ImageView imageView;
        Database.init(this);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.detail));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NewsDataModel model = Database.getNews(id);
        title = (TextView) findViewById(R.id.title);
        detail = (TextView) findViewById(R.id.detail);
        imageView = (ImageView) findViewById(R.id.image);
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        GoogleAds.getGoogleAds(this).requestNewInterstitial();
        ApplicationAnalytics application = (ApplicationAnalytics) getApplication();
        mTracker = application.getDefaultTracker();

        title.setText(SecureProcessor.onDecrypt(model.getTitle()));
        detail.setText(fixToNewline(SecureProcessor.onDecrypt(model.getFullNews())));
        Picasso.with(this)
                .load(model.getImageLink())
                .into(imageView);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_left, R.anim.slide_left_in);
    }

    public String fixToNewline(String orig) {
        char[] chars = orig.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char c : chars) {
            switch (c) {
                case '\r':
                case '\f':
                    break;
                case '\n':
                    sb.append("\n");
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // NavUtils.navigateUpFromSameTask(this);
                finish();
                overridePendingTransition(R.anim.slide_left, R.anim.slide_left_in);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        if (mAdView != null) {
            mAdView.resume();
        }
        // requestNewInterstitial();
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        //   GoogleAds.getGoogleAds(this).showInterstitial();
        super.onDestroy();
    }
}
