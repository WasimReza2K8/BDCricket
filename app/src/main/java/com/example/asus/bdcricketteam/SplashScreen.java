package com.example.asus.bdcricketteam;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.async.GetHighlightsData;
import com.example.asus.bdcricketteam.async.GetLiveStreamingLink;
import com.example.asus.bdcricketteam.async.GetNews;
import com.example.asus.bdcricketteam.async.GetSchedule;
import com.example.asus.bdcricketteam.async.GetSquad;
import com.example.asus.bdcricketteam.async.GetUpcomingTournament;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.interfaceui.UIRefreshCallBack;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;

/**
 * Created by ASUS on 2/15/2016.
 */
public class SplashScreen extends AppCompatActivity {
    //private ProgressBar mProgressBar;
    public UIRefreshCallBack uiRefreshCallBack;
    // private String newsFileURL = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";
    public static String newsFileURL2 = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwTS1ZNGVENzdzTDA";
    // private String fileSchedule = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";
    //private String squadFileLink = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwOEk3VUx3LTIxZHc";
    //private String fileUpcomingTournament = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwaDZpLWJiZF9tY1E";
    private static int SPLASH_TIME_OUT = 2000;
    private boolean loadingData;

    //https://drive.google.com/file/d/0B85b1FRNOEQwTS1ZNGVENzdzTDA/view?usp=sharing
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        ImageView appIcon = (ImageView) findViewById(R.id.appImage);
        TextView appName = (TextView) findViewById(R.id.appName);
        Animation anim = new ScaleAnimation(
                0f, 1f, // Start and end values for the X axis scaling
                0, 1f, // Start and end values for the Y axis scaling
                Animation.RELATIVE_TO_SELF, 0.5f, // Pivot point of X scaling
                Animation.RELATIVE_TO_SELF, 0.5f); // Pivot point of Y scaling
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(2000);
        appIcon.startAnimation(anim);
        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade_in);
        animFadeIn.setFillAfter(true);
        appName.startAnimation(animFadeIn);
        //mProgressBar = (ProgressBar) findViewById(R.id.progressBarLoading);
        loadingData = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
       /* uiRefreshCallBack = new UIRefreshCallBack() {
            @Override
            public void onProgress(int progress) {
                mProgressBar.setProgress(progress);
                if (progress == 100) {
                    finish();
                    startActivity(new Intent(SplashScreen.this, MainActivity.class));
                }
            }
        };*/
        try {
            getUpdate();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getUpdate() throws PackageManager.NameNotFoundException {

        if (ConnectionDetector.getInstance(this).isConnectingToInternet()) {
            //   int hour = 0;
            PackageInfo packageInfo = getPackageManager()
                    .getPackageInfo(getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            boolean onUpgrade = versionCode > OnPreferenceManager.getInstance(this).getAppVersionCode();
            if (onUpgrade) {
                OnPreferenceManager.getInstance(this).setAppVersionCode(versionCode);
            }

           /* if (OnPreferenceManager.getInstance(this).getIsFirstTime()) {
                OnPreferenceManager.getInstance(this).setIsFirstTime(false);
                OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                //new GetNews().execute();
                //loadData();

                return;
            } else {
                Long timeDifference = System.currentTimeMillis() - OnPreferenceManager.getInstance(this).getDate();
                hour = (int) (timeDifference / (60 * 1000));

                if (hour > 10 || onUpgrade) {
                    loadData();
                    OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                } else if (loadingData) {
                    return;
                } else {
                    mProgressBar.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // This method will be executed once the timer is over
                            // Start your app main activity
                            // mProgressBar.setProgress(100);
                            Intent i = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(i);

                            // close this activity
                            finish();
                        }
                    }, SPLASH_TIME_OUT);


                }
                OnPreferenceManager.getInstance(this).setDate(System.currentTimeMillis());
                if (OnPreferenceManager.getInstance(this).getIsFirstTime()) {
                    OnPreferenceManager.getInstance(this).setIsFirstTime(false);
                }
            }*/
            if (OnPreferenceManager.getInstance(this).getIsFirstTime()) {
                OnPreferenceManager.getInstance(this).setIsFirstTime(false);
            }
            startMainActivity();

        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            // set title
            alertDialogBuilder.setTitle(getResources().getString(R.string.popup_title));

            // set dialog message
            alertDialogBuilder
                    .setMessage(getResources().getString(R.string.popup_message))
                    .setCancelable(false)
                    .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            if (OnPreferenceManager.getInstance(SplashScreen.this).getIsFirstTime()) {
                                finish();
                                return;

                            } else {
                                //new GetNews().execute();
                                //loadData();
                                startMainActivity();
                                dialog.cancel();
                                return;
                            }

                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();
            // show it
            alertDialog.show();
        }
    }

    private void startMainActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                // mProgressBar.setProgress(100);
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void loadData() {
        loadingData = true;
        new GetNews(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetSchedule(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetSquad(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetUpcomingTournament(this, uiRefreshCallBack).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetLiveStreamingLink(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        new GetHighlightsData(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }
}
