package com.example.asus.bdcricketteam.receiver;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.SplashScreen;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.prefmanager.OnPreferenceManager;
import com.example.asus.bdcricketteam.security.SecureProcessor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ASUS on 2/28/2016.
 */
public class ConnectivityChangeReceiver extends BroadcastReceiver {
    private Context mContext;
    public static int NOTIFICATION_ID = 345;
    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        Log.e("connectivity", "Change received");
        if (ConnectionDetector.getInstance(context).isConnectingToInternet()) {
            Log.e("connectivity", "internet available");
            new GetNews().execute();
        }
    }


    class GetNews extends AsyncTask<Void, Void, Boolean> {

        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
            //showEmptyLayout();
            // mProgressBar.setVisibility(View.VISIBLE);
            //  mProgressBar.setProgress(10);
        }

        protected Boolean doInBackground(Void... arg0) {
            try {
                // Create a URL for the desired page
                URL url = new URL(SplashScreen.newsFileURL2);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                //if (count == 0) {

                //}
                StringBuilder total = new StringBuilder();

                while ((str = in.readLine()) != null) {
                    total.append(str);
                }
                Log.e("string total", total.toString());
                Boolean result = parseJSONGetData(total.toString());
                in.close();
                return result;

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } catch (Exception e) {
            }
            return false;
        }

        public Boolean parseJSONGetData(String total) {
            try {
                JSONObject reader = new JSONObject(total);
                JSONObject info = reader.getJSONObject("info");
                int numberOfNews = info.getInt("numberofnews");
                int updateNews = info.getInt("update");
                if (OnPreferenceManager.getInstance(mContext).getNewsUpdate() == updateNews) {
                    return false;
                }else{
                    return true;
                }
                //OnPreferenceManager.getInstance(mContext).setNewsUpdate(updateNews);
                // Database.init(SplashScreen.this);

                /*if (numberOfNews < 1) {
                    return false;
                }*/

               /* Database.init(SplashScreen.this);
                Database.deleteAll(Database.NEWS_TABLE);
                for (int i = 1; i <= numberOfNews; i++) {
                    JSONObject news1 = reader.getJSONObject("news" + i);
                    parseJSONInsertDatabase(news1);
                }*/

            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        public void parseJSONInsertDatabase(JSONObject news) {
            String title, detail, imageLink;
            NewsDataModel model = new NewsDataModel();

            Database.init(mContext);
            //Database.deleteAll(Database.NEWS_TABLE);
            try {
                title = news.getString("title");
                model.setTitle(SecureProcessor.onEncrypt(title.trim()));
                detail = news.getString("detail");
                model.setFullNews(SecureProcessor.onEncrypt(detail.trim()));
                imageLink = news.getString("imagelink");
                model.setImageLink(imageLink);
                Database.insertNewsValues(model);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        protected void onPostExecute(Boolean result) {
            if(result){
                createNotification(mContext);
            }
            //mProgressBar.setProgress(30);
            //setRecyclerView();
            //finish();
            //startActivity(new Intent(SplashScreen.this, MainActivity.class));
            // new GetSchedule().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        }

    }
    public void createNotification(Context context) {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.mipmap.app_icon)
                        .setAutoCancel(true)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(mContext.getResources().getString(R.string.notification_message)))
                        .setContentTitle(mContext.getResources().getString(R.string.notification_title))
                        .setContentText(mContext.getResources().getString(R.string.notification_message));
// Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(mContext, SplashScreen.class);
        //Intent resultIntent = new Intent(this, ResultActivity.class);

// The stack builder object will contain an artificial back stack for the
// started Activity.
// This ensures that navigating backward from the Activity leads out of
// your application to the Home screen.
        //  TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
        // stackBuilder.addParentStack(ResultActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
// Creates the PendingIntent
        PendingIntent notifyPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

// Puts the PendingIntent into the notification builder
       /* builder.setContentIntent(notifyPendingIntent);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );*/
        mBuilder.setContentIntent(notifyPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());


    }
}
