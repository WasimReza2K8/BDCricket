package com.example.asus.bdcricketteam;


import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.provider.Settings;
import android.provider.Telephony;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.asus.bdcricketteam.ads.GoogleAds;
import com.example.asus.bdcricketteam.async.GetVersionUpdate;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.fragment.HighlightsFragment;
import com.example.asus.bdcricketteam.fragment.LiveScoreList;
import com.example.asus.bdcricketteam.fragment.NationalTeamFragment;
import com.example.asus.bdcricketteam.fragment.NewsFragment;
import com.example.asus.bdcricketteam.fragment.NewsFragmentFirebase;
import com.example.asus.bdcricketteam.fragment.UpcomingTournament;
import com.example.asus.bdcricketteam.interfaceui.UpdatePopupCallBack;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private AdView mAdView;
    private RelativeLayout content;
    FragmentManager fragmentManager = getSupportFragmentManager();
    private NavigationView navigationView;
    private UpdatePopupCallBack updatePopupCallBack;
    private int timeDelay = 350;
    FragmentTransaction fragTransaction;
    // private Tracker mTracker;
    public String fixtureFileURl = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwdHRvSjB2UlVTdTA";
    public static final String EMPTY_STRING = "";
    //https://drive.google.com/file/d/0B85b1FRNOEQwdHRvSjB2UlVTdTA/view?usp=sharing
    //https://drive.google.com/open?id=0B85b1FRNOEQwdHRvSjB2UlVTdTA
    //https://drive.google.com/file/d/0B85b1FRNOEQwTS1ZNGVENzdzTDA/view?usp=sharing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        content = (RelativeLayout) findViewById(R.id.content);
        mAdView = (AdView) findViewById(R.id.ad_view);
        AdRequest adRequest = new AdRequest.Builder().build();
        // Start loading the ad in the background.
        mAdView.loadAd(adRequest);
        /*ApplicationAnalytics application = (ApplicationAnalytics) getApplication();
        mTracker = application.getDefaultTracker();*/
        fragTransaction = getSupportFragmentManager().beginTransaction();
        //NewsFragment base = new NewsFragment();
        NewsFragmentFirebase base = new NewsFragmentFirebase();
        toolbar.setTitle(getResources().getString(R.string.app_name));

        updatePopupCallBack = new UpdatePopupCallBack() {
            @Override
            public void onUpdate(boolean update) {
                if (update) {
                    updateRequest();
                }
            }
        };

        new GetVersionUpdate(this, updatePopupCallBack).execute();
        //item.setVisible(false);
        //fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
        fragTransaction.add(content.getId(), base, "uniqueTag").addToBackStack("uniqueTag");
        fragTransaction.commit();
        setSupportActionBar(toolbar);

        // new GetFixture().execute();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {


                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    //menuItem.setChecked(false);
                    drawerLayout.closeDrawers();
                    return true;
                }
                // else menuItem.setChecked(true);
                menuItem.setChecked(true);
                //Closing drawer on item click
                drawerLayout.closeDrawers();


                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.news:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                FragmentTransaction fTransaction = fragmentManager.beginTransaction();
                                Fragment fragment = fragmentManager.findFragmentByTag("uniqueTag");
                                Log.e("frag", fragment+"");
// If fragment doesn't exist yet, create one
                                if (fragment == null) {
                                    fTransaction.add(content.getId(), new NewsFragmentFirebase(), "uniqueTag").commit();
                                }
                                else { // re-use the old fragment
                                    fTransaction.replace(content.getId(), fragment, "uniqueTag").commit();
                                }
                               /* NewsFragmentFirebase main = (NewsFragmentFirebase) fragmentManager.findFragmentByTag("tag2");
                                Log.e("frag", main+"");
                                if (main == null) {
                                    main = new NewsFragmentFirebase();
                                }
                                fragTransaction = fragmentManager.beginTransaction();*/
                                //NewsFragment main = new NewsFragment();
                                toolbar.setTitle(getResources().getString(R.string.app_name));
                                //item.setVisible(false);
                                //fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
                               /* fragTransaction.replace(content.getId(), main, "tag2").addToBackStack("tag2");
                                fragTransaction.commit();*/
                            }
                        }, timeDelay);
                        //Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();


                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.bdTeam:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = fragmentManager.beginTransaction();
                                // Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                                NationalTeamFragment nationalTeamFragment = new NationalTeamFragment();
                                fragTransaction.replace(content.getId(), nationalTeamFragment).addToBackStack("tag2");
                                fragTransaction.commit();
                                toolbar.setTitle(getResources().getString(R.string.national_team));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.upcomingTournament:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = fragmentManager.beginTransaction();
                                // Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                                UpcomingTournament otherEventFragment = new UpcomingTournament();
                                //item.setVisible(false);
                                // fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
                                fragTransaction.replace(content.getId(), otherEventFragment).addToBackStack("tag3");
                                fragTransaction.commit();
                                // Log.e("tournamentname", OnPreferenceManager.getInstance(MainActivity.this).getTournamentName() + "");
                                if (OnPreferenceManager.getInstance(MainActivity.this).getTournamentName() != null
                                        && !OnPreferenceManager.getInstance(MainActivity.this).getTournamentName().equalsIgnoreCase("")) {
                                    toolbar.setTitle(SecureProcessor.onDecrypt(OnPreferenceManager.getInstance(MainActivity.this).getTournamentName()));
                                } else {
                                    toolbar.setTitle(getResources().getString(R.string.upcoming_tournament));
                                }
                            }
                        }, timeDelay);

                        return true;
                    case R.id.liveScore:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = fragmentManager.beginTransaction();
                                //Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                                LiveScoreList settings = new LiveScoreList();
                                //item.setVisible(false);
                                //fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
                                fragTransaction.replace(content.getId(), settings).addToBackStack("tag2");
                                fragTransaction.commit();
                                toolbar.setTitle(getResources().getString(R.string.live_score));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.highlights:
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                fragTransaction = fragmentManager.beginTransaction();
                                //Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                                HighlightsFragment settings = new HighlightsFragment();
                                //item.setVisible(false);
                                //fragTransaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_in_right);
                                fragTransaction.replace(content.getId(), settings).addToBackStack("tag2");
                                fragTransaction.commit();
                                toolbar.setTitle(getResources().getString(R.string.highlights));
                            }
                        }, timeDelay);

                        return true;

                    case R.id.liveStreaming:
                        if (ConnectionDetector.getInstance(MainActivity.this).isConnectingToInternet()) {
                            Intent i = new Intent(MainActivity.this, LiveStreamingActivity.class);
                            startActivity(i);
                        } else {
                            noInternetConnectionAlertDialog();
                        }
                        menuItem.setChecked(false);

                        // toolbar.setTitle(getResources().getString(R.string.live_streaming));
                        return true;
                    case R.id.invite:
                       /* mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("Share")
                                .build());*/
                        showInvitePopup();
                        menuItem.setChecked(false);
                        return true;
                    case R.id.like:
                        /*mTracker.send(new HitBuilders.EventBuilder()
                                .setCategory("Action")
                                .setAction("like")
                                .build());*/
                        getOpenFacebookIntent();
                        menuItem.setChecked(false);
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;
                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.action_settings) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();


    }


    public void getOpenFacebookIntent() {
        String facebookUrl = "https://m.facebook.com/Code-Artist-227094210954911";
        try {
            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                // open the Facebook app using the old method (fb://profile/id or fb://page/id)
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/336227679757310")));
            }
        } catch (PackageManager.NameNotFoundException e) {
            // Facebook is not installed. Open the browser
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }

    public void showInvitePopup() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.invite));
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.invitepopup, null);
        alertDialogBuilder.setView(dialogView);

        Button smsInvite = (Button) dialogView.findViewById(R.id.sms_invite);
        Button whatsAppInvite = (Button) dialogView.findViewById(R.id.whats_app_invite);
        Button otherInvite = (Button) dialogView.findViewById(R.id.other_invite);
        smsInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smsInvite();
            }
        });
        whatsAppInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsAppInvite();
            }
        });

        otherInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherAppInvite();
            }
        });
        alertDialogBuilder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // if this button is clicked, just close
                // the dialog box and do nothing
                dialog.cancel();
                return;
            }
        });
        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    public void smsInvite() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) // At least KitKat
        {
            String defaultSmsPackageName = Telephony.Sms.getDefaultSmsPackage(this); // Need to change the build to API 19

            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.setType("text/plain");
            sendIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.invite_message));

            if (defaultSmsPackageName != null)// Can be null in case that there is no default, then the user would be able to choose
            // any app that support this intent.
            {
                sendIntent.setPackage(defaultSmsPackageName);
            }
            startActivity(sendIntent);

        } else // For early versions, do what worked for you before.
        {
            Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
            smsIntent.setType("vnd.android-dir/mms-sms");
            smsIntent.putExtra("address", "");
            smsIntent.putExtra("sms_body", getResources().getString(R.string.invite_message));
            startActivity(smsIntent);
        }
    }

    public void whatsAppInvite() {
        List<Intent> targetShareIntents = new ArrayList<>();
        PackageManager packageManager = getPackageManager();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            //Logger.log_error( TAG + "sharenew Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.setPackage(resInfo.activityInfo.parentActivityName);

                if (packageName.contains("whatsapp")) {
                    // dont add subject for whatsapp
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_message));
                    targetShareIntents.add(new LabeledIntent(intent, packageName, resInfo.loadLabel(packageManager), resInfo.icon));
                }

            }
            if (!targetShareIntents.isEmpty()) {
                //Logger.log_error( TAG +"sharenew Have Intent");
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            } else {
                Toast.makeText(this, "You don't  have  whats app", Toast.LENGTH_LONG).show();
                //  Logger.log_error( TAG +"sharenew nothing");
            }
        }
    }

    public void otherAppInvite() {
        List<Intent> targetShareIntents = new ArrayList<Intent>();
        PackageManager packageManager = getPackageManager();
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        List<ResolveInfo> resInfos = getPackageManager().queryIntentActivities(shareIntent, 0);
        if (!resInfos.isEmpty()) {
            // Logger.log_error( TAG + "sharenew Have package");
            for (ResolveInfo resInfo : resInfos) {
                String packageName = resInfo.activityInfo.packageName;

                Intent intent = new Intent();
                intent.setComponent(new ComponentName(packageName, resInfo.activityInfo.name));
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");

                intent.setPackage(resInfo.activityInfo.parentActivityName);

                //ignore list
                if (packageName.contains("wifi") || packageName.contains("bluetooth") || packageName.contains("nfc") || packageName.contains("connect") || packageName.contains("memo") || packageName.contains("translate") || packageName.contains("gps")
                        || packageName.contains("file") || packageName.contains("File") || packageName.contains("drive") || packageName.contains("office") || packageName.contains("docs") || packageName.contains("dropbox") || packageName.contains("beam")
                        || packageName.contains("keep")) {
                    //  Logger.log_error( TAG + "sharenew IGNORE Package packageName = " + packageName);
                    continue;
                }

                // Logger.log_error( TAG + "sharenew Package packageName = " + packageName);
                if (packageName.contains("sms") || packageName.contains("mms") || packageName.contains("talk") || packageName.contains("messaging") || packageName.contains("twitter") || packageName.contains("com.facebook.orca")) {
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_message));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invite_message));
                } else if (packageName.contains("whatsapp")) {
                    // dont add subject for whatsapp
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_message));
                } else {
                    intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.invite_message));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.invite_message));
                }
                targetShareIntents.add(new LabeledIntent(intent, packageName, resInfo.loadLabel(packageManager), resInfo.icon));
            }
            if (!targetShareIntents.isEmpty()) {
                // Logger.log_error( TAG +"sharenew Have Intent");
                Intent chooserIntent = Intent.createChooser(targetShareIntents.remove(0), "Choose app to share");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, targetShareIntents.toArray(new Parcelable[]{}));
                startActivity(chooserIntent);
            } else {
                // Logger.log_error( TAG +"sharenew nothing");
            }
        }
    }

    public void noInternetConnectionAlertDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.popup_title));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.popup_message_live))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                        startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        if (OnPreferenceManager.getInstance(MainActivity.this).getIsFirstTime()) {
                            return;

                        } else {
                            //new GetNews().execute();
                            //loadData();
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

    public void updateRequest() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.update_title));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.update_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.update_now), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                        } catch (android.content.ActivityNotFoundException anfe) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                    }
                })
                .setNegativeButton(getResources().getString(R.string.later), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                        return;

                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
        // requestNewInterstitial();
    }

    @Override
    protected void onPause() {

        // GoogleAds.getGoogleAds(this).showInterstitial();

        super.onPause();
    }

    /**
     * Called before the activity is destroyed
     */
    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set title
        alertDialogBuilder.setTitle(getResources().getString(R.string.go_back_title));

        // set dialog message
        alertDialogBuilder
                .setMessage(getResources().getString(R.string.go_back_message))
                .setCancelable(false)
                .setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        finish();
                        GoogleAds.getGoogleAds(MainActivity.this).showInterstitial();
                        //super.onBackPressed();
                        // if this button is clicked, close
                        // current activity
                        //MainActivity.this.finish();
                        // startActivity(new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS));
                    }
                })
                .setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        return;


                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.rate) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

