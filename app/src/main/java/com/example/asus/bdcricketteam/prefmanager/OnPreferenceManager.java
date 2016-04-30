package com.example.asus.bdcricketteam.prefmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.preference.PreferenceManager;

import java.util.Date;

/**
 * Created by Wasim on 10/15/2015.
 */
public class OnPreferenceManager {
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    private String newsUpdate = "newsUpdate";
    private String highlightsUpdate = "highlightsUpdate";
    private String tournamentUpdate = "tournamentUpdate";
    private String tournamentName = "tournamentName";
    private String date = "date";
    private String scheduleUpdate = "scheduleUpdate";
    private String squadUpdate = "squadUpdate";
    private String dateFixture = "dateFixture";
    private String numberOfNews = "numberOfNews";
    private String isFirstTime = "isFirstTime";
    private String liveStreamLink = "liveStreamLink";
    private String defaultLiveStreamingLink = "OIaPQMtCkRc";
    private static OnPreferenceManager sOnPreferenceManager;


    private OnPreferenceManager(Context mContext) {
        prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = prefs.edit();
    }

    public static OnPreferenceManager getInstance(Context mContext) {
        if (sOnPreferenceManager == null)
            sOnPreferenceManager = new OnPreferenceManager(mContext);
        return sOnPreferenceManager;
    }


    public synchronized void setNewsUpdate(int news) {
        editor.putInt(newsUpdate, news);
        editor.apply();
    }

    public synchronized int getNewsUpdate() {
        int selectionStart = prefs.getInt(newsUpdate, 1);
        return selectionStart;
    }

    public synchronized void setHighlightsUpdate(int news) {
        editor.putInt(highlightsUpdate, news);
        editor.apply();
    }

    public synchronized int getHighlightsUpdate() {
        int selectionStart = prefs.getInt(highlightsUpdate, 1);
        return selectionStart;
    }


    public synchronized void setDate(Long hour) {
        editor.putLong(date, hour);
        editor.apply();
    }

    public synchronized void setTournamentUpdate(int minute) {
        editor.putInt(tournamentUpdate, minute);
        editor.apply();
    }

    public synchronized void setTournamentName(String minute) {
        editor.putString(tournamentName, minute);
        editor.apply();
    }

    public synchronized Long getDate() {
        long selectionStart = prefs.getLong(date, (long) -1);
        return selectionStart;
    }



    public synchronized int getTournamentUpdate() {
        int selectionStart = prefs.getInt(tournamentUpdate, 1);
        return selectionStart;
    }

    public synchronized String getTournamentName() {
        String selectionStart = prefs.getString(tournamentName, null);
        return selectionStart;
    }

    public synchronized void setSquadUpdate(int minute) {
        editor.putInt(squadUpdate, minute);
        editor.apply();
    }

    public synchronized int getSquadUpdate() {
        int selectionStart = prefs.getInt(squadUpdate, 1);
        return selectionStart;
    }

    public synchronized void setIsFirstTime(boolean minute) {
        editor.putBoolean(isFirstTime, minute);
        editor.apply();
    }

    public synchronized Boolean getIsFirstTime() {
        Boolean selectionStart = prefs.getBoolean(isFirstTime, true);
        return selectionStart;
    }

    public synchronized void setScheduleUpdate(int minute) {
        editor.putInt(scheduleUpdate, minute);
        editor.apply();
    }

    public synchronized void setDateFixtureDate(String hour) {
        editor.putString(dateFixture, hour);
        editor.apply();
    }

    public synchronized int getScheduleUpdate() {
        int selectionStart = prefs.getInt(scheduleUpdate, 1);
        return selectionStart;
    }

    public synchronized String getLiveStreamLink() {
        String selectionStart = prefs.getString(liveStreamLink, defaultLiveStreamingLink);
        return selectionStart;
    }

    public synchronized void setLiveStreamLink(String hour) {
        editor.putString(liveStreamLink, hour);
        editor.apply();
    }


    public void setNumberOfNews(int news) {
        editor.putInt(numberOfNews, news);
        editor.apply();
    }

    public int getNumberOfNews() {
        int selectionStart = prefs.getInt(numberOfNews, -1);
        return selectionStart;
    }
    //used pref------


}
