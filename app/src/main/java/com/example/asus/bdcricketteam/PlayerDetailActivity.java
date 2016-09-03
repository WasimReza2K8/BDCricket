package com.example.asus.bdcricketteam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.asus.bdcricketteam.database.Database;
import com.example.asus.bdcricketteam.datamodel.CareerDataModel;
import com.example.asus.bdcricketteam.datamodel.SquadModel;
import com.example.asus.bdcricketteam.security.SecureProcessor;
import com.squareup.picasso.Picasso;

/**
 * Created by ASUS on 2/22/2016.
 */
public class PlayerDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);
        int id = getIntent().getIntExtra("id", -1);

        SquadModel squadModel = new SquadModel();
        CareerDataModel test = new CareerDataModel();
        CareerDataModel odi = new CareerDataModel();
        CareerDataModel t20 = new CareerDataModel();
        Database.init(this);
        squadModel = Database.getWholeSquad(Database.SQUAD_TABLE, id);
        test = Database.getCareerData(id, Database.TABLE_TEST);
        odi = Database.getCareerData(id, Database.TABLE_ODI);
        t20 = Database.getCareerData(id, Database.TABLE_T20);
        Log.e("player id", id + " " + squadModel.getImageLink());
        TextView battingHeading, testTextView, odiTextView, t2oTextView;
        battingHeading = (TextView) findViewById(R.id.battingHeading);
        testTextView = (TextView) findViewById(R.id.testBattingDetail);
        TextView name = (TextView) findViewById(R.id.name);
        name.setText(SecureProcessor.onDecrypt(squadModel.getName()));
        ImageView imageView = (ImageView) findViewById(R.id.image);
        Picasso.with(this)
                .load(squadModel.getImageLink())
                .into(imageView);

        battingHeading.setText("    " + " Mat " + " Inns " + " Runs " + " HS " + " Ave " + " SR " + " 100 " + " 50 ");
        testTextView.setText("test " + test.getMatches() + " " + test.getBattingInns() + " " + test.getBattingRuns()
                + " " + test.getBattingHighestScore() + " " + test.getBattingAvg() + " " + test.getBattingStrikeRate() + " "
                + test.getBattingHundreds() + " " + test.getBattingFifties());



    }
}
