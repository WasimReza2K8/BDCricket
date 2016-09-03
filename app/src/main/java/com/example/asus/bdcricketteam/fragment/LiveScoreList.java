package com.example.asus.bdcricketteam.fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.asus.bdcricketteam.LiveScoreActivity;
import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.adapter.LiveScoreListAdapter;
import com.example.asus.bdcricketteam.connectivity.ConnectionDetector;
import com.example.asus.bdcricketteam.datamodel.LiveScoreDataModel;
import com.example.asus.bdcricketteam.onlclick.RecyclerItemClickListener;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 3/6/2016.
 */
public class LiveScoreList extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private LiveScoreListAdapter adapter;
    private List<LiveScoreDataModel> list;
   // private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highlights, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);
        mTextView = (TextView) rootView.findViewById(R.id.textViewLoading);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), LiveScoreActivity.class);
                i.putExtra("link", list.get(position).getLink());
                i.putExtra("title", "live_score");
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
            }
        }));
        //  setRecyclerView();
        list = new ArrayList<>();
        if (ConnectionDetector.getInstance(getActivity()).isConnectingToInternet()) {
            new GetLiveLink(getActivity()).execute();
        } else {
            mTextView.setText(getActivity().getResources().getString(R.string.live_score_connect_to_internet));
            mTextView.setVisibility(View.VISIBLE);
        }
       /* ApplicationAnalytics application = (ApplicationAnalytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
        //new GetLiveLink(getActivity()).execute();
        return rootView;
    }


    public class GetLiveLink extends AsyncTask<Void, Void, String> {
        private Context mContext;
        //private UIRefreshCallBack mUiRefreshCallBack;
        public String newsFileURL2 = "http://static.cricinfo.com/rss/livescores.xml";
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> link = new ArrayList<>();
        private ProgressDialog mProgressDialog;

        public GetLiveLink(Context context) {
            mContext = context;
            mProgressDialog = new ProgressDialog(mContext);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // mProgressDialog.setTitle(mContext.getResources().getString(R.string.loading));
            // mProgressDialog.show();
            mProgressBar.setVisibility(View.VISIBLE);
            mTextView.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(Void... arg0) {
            try {
                // Create a URL for the desired page
                URL url = new URL(newsFileURL2);
                URLConnection connection = url.openConnection();
                InputStream is = connection.getInputStream();

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                //  BufferedReader in = new BufferedReader(new InputStreamReader(is));

                XmlPullParserFactory xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myParser = xmlFactoryObject.newPullParser();
                myParser.setInput(is, null);

                int event = myParser.getEventType();
                String text = null;
                while (event != XmlPullParser.END_DOCUMENT) {
                    String name = myParser.getName();
                    switch (event) {
                        case XmlPullParser.START_TAG:
                            break;

                        case XmlPullParser.TEXT:
                            text = myParser.getText();

                            break;

                        case XmlPullParser.END_TAG:
                            if (name.equals("title")) {
                                Log.e("title", text);
                                title.add(text);
                                // temperature = myParser.getAttributeValue(null,"value");
                            } else if (name.equals("link")) {
                                Log.e("link", text);
                                link.add(text);

                            }
                            break;
                    }
                    event = myParser.next();
                }

                return null;

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "";
        }

        protected void onPostExecute(String result) {
            String url = null;

            for (int i = title.size() - 1; i > 0; i--) {
                //if (title.get(i).contains("Bangladesh")) {
                Log.e("url", link.get(i));
                LiveScoreDataModel model = new LiveScoreDataModel();
                model.setLink(link.get(i));
                model.setMatchTitle(title.get(i));
                list.add(model);
                //  }
            }
            // mProgressDialog.dismiss();
            mProgressBar.setVisibility(View.GONE);
            mTextView.setVisibility(View.GONE);
            setRecyclerView();
        }
    }


    private void setRecyclerView() {
        // getAllDataFromDb();
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new LiveScoreListAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        } else {
            mTextView.setText(getActivity().getResources().getString(R.string.no_match));
            mTextView.setVisibility(View.VISIBLE);
        }

    }
}
