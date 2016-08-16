package com.example.asus.bdcricketteam.application;

import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by ASUS on 8/16/2016.
 */
public class OnApplication extends android.app.Application  {

    @Override
    public void onCreate() {
        super.onCreate();
    /* Enable disk persistence  */
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
