package com.example.asus.bdcricketteam.datamodel;

/**
 * Created by ASUS on 4/30/2016.
 */
public class HighlightsDataModel {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String fullNews) {
        this.duration = fullNews;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    private int id;
    private String title;
    private String duration;
    private String imageLink;
}
