package com.example.asus.bdcricketteam.analytics;

import android.app.Application;

import com.example.asus.bdcricketteam.R;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;


/**
 * Created by ASUS on 5/11/2016.
 */
public class ApplicationAnalytics extends Application {
    private Tracker mTracker;
    private String projectId = "UA-77590904-1";

    /**
     * Gets the default {@link Tracker} for this {@link Application}.
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }
}
