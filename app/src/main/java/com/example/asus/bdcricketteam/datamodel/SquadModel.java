package com.example.asus.bdcricketteam.datamodel;

/**
 * Created by ASUS on 2/16/2016.
 */
public class SquadModel {
    public String getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(String playerNumber) {
        this.playerNumber = playerNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String playerName) {
        this.name = playerName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    private String playerNumber;
    private String name;
    private String  role;// 1 means batsman, 2 means bolwer, 3 means allrounder, 4 means wicketkepper Batsman
    private String imageLink;
    private String profilelink;
    public String getProfilelink() {
        return profilelink;
    }

    public void setProfilelink(String profilelink) {
        this.profilelink = profilelink;
    }



    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    private String age;

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    private String style;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;


}
