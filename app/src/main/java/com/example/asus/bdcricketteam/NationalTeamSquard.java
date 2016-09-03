package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asus.bdcricketteam.adapter.SquadRecyclerAdapter;
import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
import com.example.asus.bdcricketteam.onlclick.RecyclerItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2/6/2016.
 */
public class NationalTeamSquard extends Fragment {
    private String squadFileLink = "https://drive.google.com/uc?export=download&id=0B85b1FRNOEQwOEk3VUx3LTIxZHc";
    private List<SquadModel> list;
    private SquadRecyclerAdapter adapter;
    private RecyclerView mRecyclerView;
    //private Tracker mTracker;

    //https://drive.google.com/file/d/0B85b1FRNOEQwOEk3VUx3LTIxZHc/view?usp=sharing
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.national_team_squard, container, false);
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.squadRecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        setRecyclerView();

        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                /*Intent i = new Intent(getActivity(), PlayerDetailActivity.class);
                i.putExtra("id", list.get(position).getId());
                startActivity(i);*/
            }
        }));
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().finish();
                    return true;
                }

                return false;
            }
        });
        /*ApplicationAnalytics application = (ApplicationAnalytics) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        Log.i("screen", "Setting screen name: " + this.toString());
        mTracker.setScreenName("Image~" + this.toString());
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());*/
        return rootView;
    }

   /* class GetSquad extends AsyncTask<Void, Void, String> {
        protected void onPreExecute() {
            Log.d("PreExceute", "On pre Exceute......");
        }

        protected String doInBackground(Void... arg0) {
            try {
                // Create a URL for the desired page
                URL url = new URL(squadFileLink);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("GET");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String str;
                Database.init(getActivity().getApplicationContext());

                StringBuilder total = new StringBuilder();
                while ((str = in.readLine()) != null) {
                    //stringDivideDataEntry(str.trim());
                    total.append(str);
                    Log.e("string server", str);
                }
                parseJSONGetData(total.toString());
                in.close();
                return total.toString().trim();

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            } catch (Exception e) {
            }
            return "";
        }

        public void parseJSONGetData(String total) {
            try {
                JSONObject reader = new JSONObject(total);
                JSONObject info = reader.getJSONObject("info");
                int update = info.getInt("update");
                if (update == OnPreferenceManager.getInstance(getActivity()).getSquadUpdate()) {
                    return;
                }
                OnPreferenceManager.getInstance(getActivity()).setSquadUpdate(update);
                int numberOfNews = info.getInt("numberofplayer");
                if (numberOfNews < 1) {
                    return;
                }

                Database.init(getActivity());
                Database.deleteAll(Database.SQUAD_TABLE);
                for (int i = 1; i <= numberOfNews; i++) {
                    JSONObject player = reader.getJSONObject("player" + i);
                    parseJSONInsertDatabase(player, i);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void parseJSONInsertDatabase(JSONObject news, int id) {
            String name, age, role, style, imageLink, career;
            SquadModel model = new SquadModel();

            Database.init(getActivity());
            //Database.deleteAll(Database.SQUAD_TABLE);
            try {
                //model.setId(id);
                model.setPlayerNumber(String.valueOf(id));
                name = news.getString("name");
                model.setName(SecureProcessor.onEncrypt(name.trim()));
                age = news.getString("age");
                model.setAge(SecureProcessor.onEncrypt(age.trim()));
                role = news.getString("role");
                model.setRole(role.trim());
                style = news.getString("style");
                model.setStyle(style.trim());
                imageLink = news.getString("imageLink");
                model.setImagelink(imageLink);
                Database.insertSquadValues(model, Database.SQUAD_TABLE);
                career = news.getString("Career");
                careerParser(career, id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void careerParser(String career, int id) {
            JSONObject reader = null;
            try {
                reader = new JSONObject(career);
                JSONObject test = reader.getJSONObject("test");
                Database.init(getActivity());
                //Database.deleteAll(Database.TABLE_TEST);
                JSONCareerObjectParse(test, Database.TABLE_TEST, id);
                //Database.deleteAll(Database.TABLE_TEST);
                JSONObject ODI = reader.getJSONObject("ODI");
                JSONCareerObjectParse(ODI, Database.TABLE_ODI, id);
                JSONObject t20 = reader.getJSONObject("t20");
                JSONCareerObjectParse(t20, Database.TABLE_T20, id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        public void JSONCareerObjectParse(JSONObject career, String tableName, int id) {
            CareerDataModel model = new CareerDataModel();

            Database.init(getActivity());
            try {
                // model.setId(id);
                model.setMatches(career.getInt("matchnumber"));
                model.setBattingInns(career.getInt("batInns"));
                model.setBattingRuns(career.getInt("batRuns"));
                model.setBattingHighestScore(career.getInt("batHighestScore"));
                model.setBattingAvg(career.getString("batAvg"));
                model.setBattingStrikeRate(career.getString("batStrikeRate"));
                model.setBattingHundreds(career.getInt("batHundreds"));
                model.setBattingFifties(career.getInt("batFifties"));
                model.setBowlingInns(career.getInt("ballInns"));
                model.setBowlingRuns(career.getInt("ballRuns"));
                model.setBowlingWickets(career.getInt("ballWkts"));
                model.setBowlingBestScore(career.getString("ballBestScore"));
                model.setBowlingAvg(career.getString("ballAvg"));
                model.setBowlingEcon(career.getString("ballEcon"));
                model.setBowlingStrikeRate(career.getString("ballStrikeRate"));
                model.setBowlingNumberOfFiveWickets(career.getInt("numberOfFiveWickets"));


                Database.insertCareerValues(model, tableName);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        protected void onPostExecute(String result) {
            setRecyclerView();
            Log.e("final ", result);
        }
    }*/

    private void getAllDataFromDb() {
        list = new ArrayList<>();
        Database.init(getActivity());
        list = Database.getWholeSquad(Database.SQUAD_TABLE);
    }

    public void setRecyclerView() {
        getAllDataFromDb();
        if (list.size() > 0) {
            //hideEmptyLayout();
            adapter = new SquadRecyclerAdapter(getActivity(), list);
            mRecyclerView.setAdapter(adapter);
        }

    }
}
