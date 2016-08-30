package com.example.asus.bdcricketteam.datamodel;

import com.example.asus.bdcricketteam.security.SecureProcessor;

/**
 * Created by ASUS on 2/7/2016.
 */
public class FixtureDataModel {
    private String matchNumber;
    private String date;
    private String between;
    private String venue;
    private String time;
    private String flag1;
    private String flag2;
    private String result;
    private String tournament;
    private int id;

    public String getFlag1() {
        return flag1;
    }

    public void setFlag1(String flag1) {
        this.flag1 = flag1;
    }

    public String getFlag2() {
        return flag2;
    }

    public void setFlag2(String flag2) {
        this.flag2 = flag2;
    }


    public String getTournament() {
        return tournament;
    }

    public void setTournament(String tournament) {
        this.tournament = tournament;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getMatchNumber() {
        return matchNumber;
    }

    public void setMatchNumber(String matchNumber) {
        this.matchNumber = matchNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBetween() {
        return between;
    }

    public void setBetween(String between) {
        this.between = between;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
