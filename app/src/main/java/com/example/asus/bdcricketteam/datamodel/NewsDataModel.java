package com.example.asus.bdcricketteam.datamodel;

/**
 * Created by ASUS on 2/8/2016.
 */
public class NewsDataModel {
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

    public String getFullNews() {
        return fullNews;
    }

    public void setFullNews(String fullNews) {
        this.fullNews = fullNews;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    private int id;
    private String title;
    private String fullNews;
    private String imageLink;
}
