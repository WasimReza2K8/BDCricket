package com.example.asus.bdcricketteam.datamodel;

/**
 * Created by ASUS on 10/31/2016.
 */

public class Users {

    public String username;
    public String email;


    public Users() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public Users(String username, String email) {
        this.username = username;
        this.email = email;
    }

}