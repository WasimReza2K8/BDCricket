package com.example.asus.bdcricketteam.datamodel;

import java.io.Serializable;



/**
 * Created by ASUS on 2/8/2016.
 */
public class NewsDataModel implements Serializable {
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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    private int id;
    private String title;
    private String detail;
    private String imagelink;
}
