package com.example.asus.bdcricketteam.async;

import android.content.Context;
import android.os.AsyncTask;

import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.interfaceui.UpdatePopupCallBack;

import org.jsoup.Jsoup;

/**
 * Created by ASUS on 4/30/2016.
 */
public class GetVersionUpdate extends AsyncTask<Void, Void, Boolean> {

    private Context mContext;
    private UpdatePopupCallBack mUiRefreshCallBack;
    //public String newsFileURL2 = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwTS1ZNGVENzdzTDA";

    public GetVersionUpdate(Context context, UpdatePopupCallBack uiRefreshCallBack) {
        mContext = context;
        mUiRefreshCallBack = uiRefreshCallBack;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (aBoolean) {
            mUiRefreshCallBack.onUpdate(aBoolean);
        }
        super.onPostExecute(aBoolean);
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return updateInfo();
    }

    private boolean updateInfo() {
        try {
            String curVersion = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0).versionName;
            String newVersion = curVersion;
            if (!ConnectionDetector.getInstance(mContext).isConnectingToInternet()) return false;
            newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=" + mContext.getPackageName() + "&hl=en")
                    .timeout(30000)
                    .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
                    .referrer("http://www.google.com")
                    .get()
                    .select("div[itemprop=softwareVersion]")
                    .first()
                    .ownText();
            // Log.e("version", newVersion);
            // Log.e("version", curVersion);
            return (value(curVersion) < value(newVersion)) ? true : false;
        } catch (Exception e) {
            //  Log.e("version", e.toString());
            //  Log.e("version", e.toString());
            e.printStackTrace();
            return false;
        }
    }

    private long value(String string) {
        string = string.trim();
        if (string.contains(".")) {
            final int index = string.lastIndexOf(".");
            return value(string.substring(0, index)) * 100 + value(string.substring(index + 1));
        } else {
            return Long.valueOf(string);
        }
    }
}
