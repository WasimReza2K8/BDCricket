package com.example.asus.bdcricketteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.asus.bdcricketteam.NewsDetailActivity;
import com.example.asus.bdcricketteam.R;
import com.example.asus.bdcricketteam.adapter.NewsRecyclerAdapter;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.onlclick.RecyclerItemClickListener;
import com.example.asus.bdcricketteam.security.SecureProcessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ASUS on 2/6/2016.
 */
public class NewsFragment extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private ProgressBar mProgressBar;
    private NewsRecyclerAdapter adapter;
    private List<NewsDataModel> list;
    private SliderLayout mDemoSlider;
    //private Tracker mTracker;

    //https://drive.google.com/open?id=0B85b1FRNOEQwR2c2SGNVUHVFdXc
    //https://drive.google.com/open?id=0B85b1FRNOEQwR2c2SGNVUHVFdXc
    //https://drive.google.com/open?id=0B85b1FRNOEQwVFQzREV1WktLOTg
    //https://drive.google.com/open?id=0B85b1FRNOEQwTS1ZNGVENzdzTDA
    //https://drive.google.com/file/d/0B85b1FRNOEQwTS1ZNGVENzdzTDA/view?usp=sharing
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);
        mTextView = (TextView) rootView.findViewById(R.id.textViewLoading);
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
        mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
        mDemoSlider.setVisibility(View.GONE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //new GetNews().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // list = new ArrayList<>();
        // adapter = new NewsRecyclerAdapter(getActivity(), list);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getActivity(), NewsDetailActivity.class);
               // i.putExtra("id", list.get(position).getId());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
              /*  Intent i = new Intent(getActivity(), NewsDetailActivity.class);
                i.putExtra("id", list.get(position).getId());
                View sharedView = view;
                String transitionName = "trans";

                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, transitionName);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    getActivity().startActivity(i, transitionActivityOptions.toBundle());
                }*/
            }
        }));
        setRecyclerView();
        setSlider();
        /*ApplicationAnalytics application = (ApplicationAnalytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
        return rootView;
    }


    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getAllNews();
    }

    private void setSlider() {
        HashMap<Integer, String> sliderMap = new HashMap();
        // int count = 0;
        if(list.size() == 0){
            return;
        }
        for (int i = 0; i < 5; i++) {
            sliderMap.put(i, list.get(i).getImagelink());
            //count++;
        }

        for (int position : sliderMap.keySet()) {
            TextSliderView textSliderView = new TextSliderView(getActivity());

            // initialize a SliderLayout
            textSliderView
                    .description(SecureProcessor.onDecrypt(list.get(position).getTitle()))
                    .image(sliderMap.get(position))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putInt("extra", position);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    private void setRecyclerView() {
        getAllDataFromDb();
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new NewsRecyclerAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        int position = slider.getBundle().getInt("extra");
        Intent i = new Intent(getActivity(), NewsDetailActivity.class);
        //i.putExtra("id", list.get(position).getId());
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
