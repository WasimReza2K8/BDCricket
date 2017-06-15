package com.example.asus.bdcricketteam.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.viewholder.NewsViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 8/15/2016.
 */
public class NewsFragmentFirebase extends Fragment implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {


    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<NewsDataModel, NewsViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private ProgressBar mProgressBar;
    private LinearLayoutManager mManager;
    private TextView mLoadingTextView;

    private SliderLayout mDemoSlider;
    private View rootView;
    private int newDataAdded;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<NewsDataModel> sliderList = new ArrayList<>();
    // private static String VIDEO_ID = null;
    //private static final String API_KEY = "AIzaSyAfuxBW5SMQDLf8IDU_9Rwkn0-esinOfNw";
    //  private YouTubePlayerSupportFragment youTubePlayerFragment;

    public NewsFragmentFirebase() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (mDatabase == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            // database.setPersistenceEnabled(true);
            mDatabase = database.getReference();
            mDatabase.keepSynced(true);
        }
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.news_fragment, container, false);
            mRecycler = (RecyclerView) rootView.findViewById(R.id.newsRecyclerView);
            mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBarLoading);
            mLoadingTextView = (TextView) rootView.findViewById(R.id.textViewLoading);
            mDemoSlider = (SliderLayout) rootView.findViewById(R.id.slider);
            mProgressBar.setVisibility(View.VISIBLE);
            mSwipeRefreshLayout = (SwipeRefreshLayout)rootView.findViewById(R.id.swiperefreshlayout);
            mSwipeRefreshLayout.bringToFront();
            mSwipeRefreshLayout.setEnabled(true);
            mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    if(newDataAdded != 1){
                        mSwipeRefreshLayout.setRefreshing(false);
                    }

                }
            });
            mLoadingTextView.setVisibility(View.VISIBLE);
            Query sliderQuery = getSliderQuery(mDatabase);
            sliderQuery.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    NewsDataModel newPost = dataSnapshot.getValue(NewsDataModel.class);
                    sliderList.add(newPost);


                    if (sliderList != null && sliderList.size() == 5) {
                        for (int i = 4; i >= 0; i--) {
                            setSlider(sliderList.get(i));
                        }
                    }
                    Log.e("scrollList", "onDataAdded");
                    newDataAdded = 1;
                    mSwipeRefreshLayout.bringToFront();
                    mSwipeRefreshLayout.setRefreshing(true);
                  /*  mSwipeRefreshLayout.setEnabled( true );
                    mSwipeRefreshLayout.setRefreshing( true );
                    mSwipeRefreshLayout.bringToFront();*/
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    Log.e("scrollList", "onDatachange");
                   // mSwipeRefreshLayout.bringToFront();
                   /* mSwipeRefreshLayout.setEnabled( true );
                    mSwipeRefreshLayout.setRefreshing( true );
                    mSwipeRefreshLayout.bringToFront();*/
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        // [START create_database_reference]


        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //setSlider();
    }

    @Override
    public void onDestroyView() {
        if (rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();
    }

    public void setSlider(NewsDataModel model) {
        //for (NewsDataModel model : sliderList) {
        TextSliderView textSliderView = new TextSliderView(getActivity());

        // initialize a SliderLayout
        textSliderView
                .description(model.getTitle())
                .image(model.getImagelink())
                .setScaleType(BaseSliderView.ScaleType.Fit)
                .setOnSliderClickListener(this);

        //add your extra information
        textSliderView.bundle(new Bundle());
        textSliderView.getBundle()
                .putSerializable("extra", model);

        mDemoSlider.addSlider(textSliderView);
        // }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(3000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        Log.e("Query", postsQuery.toString() + " null");
        mSwipeRefreshLayout.setRefreshing(true);
        mAdapter = new FirebaseRecyclerAdapter<NewsDataModel, NewsViewHolder>(NewsDataModel.class, R.layout.news_item,
                NewsViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final NewsViewHolder viewHolder, final NewsDataModel model, final int position) {
                /*final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();*/
                // Log.e("favitem", OnPreferenceManager.getInstance(getActivity()).getFavArrayList().size()+"");
                //sliderList.add(model);
                viewHolder.itemCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goToDetailActivity(model);
                    }
                });

                // Bind Post to ViewHolder, setting OnClickListener for the star button
                mProgressBar.setVisibility(View.GONE);
                mLoadingTextView.setVisibility(View.GONE);
                viewHolder.bindToPost(model /*new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Neeed to write to both places the post is stored
                        Log.e("click", "item click 2");
                        DatabaseReference globalPostRef = mDatabase.child("seo").child(postRef.getKey());
                        //DatabaseReference userPostRef = mDatabase.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        // onStarClicked(userPostRef);
                    }
                }*/, getActivity());
                // setSlider();
                if(newDataAdded == 1){
                    if(mRecycler != null){
                        mRecycler.smoothScrollToPosition(30);
                        newDataAdded = 0;
                        mSwipeRefreshLayout.setRefreshing(false);
                        //mSwipeRefreshLayout.bringToFront();
                        //mSwipeRefreshLayout.setEnabled(false);
                       // mSwipeRefreshLayout.setRefreshing(false);
                    }
                }
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

  /*  private float dp2pix(int dp) {

        float density = getActivity().getResources().getDisplayMetrics().density;
        return dp * density;

    }*/


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    /* public String getUid() {
         return FirebaseAuth.getInstance().getCurrentUser().getUid();
     }
 */
    public Query getQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("news").limitToLast(30);
        return recentPostsQuery;
    }

    public Query getSliderQuery(DatabaseReference databaseReference) {
        Query recentPostsQuery = databaseReference.child("news").limitToLast(5);
        return recentPostsQuery;
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

    @Override
    public void onSliderClick(BaseSliderView slider) {
        NewsDataModel position = (NewsDataModel) slider.getBundle().getSerializable("extra");
        goToDetailActivity(position);
    }

    private void goToDetailActivity(NewsDataModel model) {
        Intent i = new Intent(getActivity(), NewsDetailActivity.class);
        i.putExtra("news", model);
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

    }
}



