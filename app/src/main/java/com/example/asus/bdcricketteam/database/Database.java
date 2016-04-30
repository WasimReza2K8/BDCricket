package com.example.asus.bdcricketteam.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.asus.bdcricketteam.datamodel.CareerDataModel;
import com.example.asus.bdcricketteam.datamodel.FixtureDataModel;
import com.example.asus.bdcricketteam.datamodel.HighlightsDataModel;
import com.example.asus.bdcricketteam.datamodel.NewsDataModel;
import com.example.asus.bdcricketteam.datamodel.SquadModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ASUS on 2/7/2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "T20";
    static final int DATABASE_VERSION = 2;
    private static Database instance = null;
    private static SQLiteDatabase database = null;
    public static final String NATIONAL_TEAM_FIXTURE_TABLE = "national_team_fixture";
    public static final String UPCOMING_TOURNAMENT_FIXTURE = "upcoming_tournament_fixture";
    public static final String NEWS_TABLE = "news_table";
    public static final String SQUAD_TABLE = "squad_table";
    public static final String TABLE_TEST = "table_test";
    public static final String TABLE_ODI = "table_ODI";
    public static final String TABLE_T20 = "table_t20";
    public static final String NEWS_TITLE = "news_title";
    public static final String NEWS_DETAIL = "news_detail";
    public static final String NEWS_IMAGE_LINK = "news_image_link";
    public static final String DURATION = "duration";
    public static final String HIGHLIGHTS_TABLE = "highlights_table";
    public static final String COLUMN_MATCH_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_BETWEEN = "_between";
    public static final String COLUMN_VENUE = "venue";
    public static final String COLUMN_MATCH_NUMBER = "match_number";
    public static final String COLUMN_TOURNAMENT = "tournament_name";
    public static final String COLUMN_MATCH_RESULT = "match_result";
    public static final String PLAYER_NUMBER = "player_number";
    public static final String PLAYER_NAME = "player_name";
    public static final String PLAYER_AGE = "player_age";
    public static final String PLAYER_IMAGE_LINK = "player_image_link";
    public static final String PLAYER_STYLE = "player_style";
    public static final String PLAYER_NUMBER_OF_MATCHES = "player_number_of_matches";
    public static final String BAT_INNS = "bat_inns";
    public static final String BAT_RUNS = "bat_runs";
    public static final String HIGHEST_SCORE = "highest_score";
    public static final String BAT_AVG = "bat_avg";
    public static final String BAT_STRIKE_RATE = "bat_strike_rate";
    public static final String BAT_HUNDREDS = "bat_hundreds";
    public static final String BAT_FIFTIES = "bat_fifties";
    public static final String BALL_INNS = "ball_inns";
    public static final String BALL_RUNS = "BALL_RUNS";
    public static final String BALL_WKTS = "ball_wickets";
    public static final String BALL_BEST_BOLWING_SCORE = "ball_best_bolwing";
    public static final String BALL_AVG = "ball_avg";
    public static final String BALL_ECON = "ball_econ";
    public static final String BALL_STRIKE_RATE = "ball_srike_rate";
    public static final String BALL_NO_OF_FIVE_WKTS = "ball_number_of_5_wkts";
    //public static final String COLUMN_MATCH_ID = "_id";
    public static final String PLAYER_ROLE = "player_role";


    Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static void init(Context context) {
        if (null == instance) {
            instance = new Database(context);
        }
    }

    public static SQLiteDatabase getDatabase() {
        if (null == database) {
            database = instance.getWritableDatabase();
        }
        return database;
    }

    public static void deactivate() {
        if (null != database && database.isOpen()) {
            database.close();
        }
        database = null;
        instance = null;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + NATIONAL_TEAM_FIXTURE_TABLE + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_BETWEEN + " TEXT NOT NULL, "
                + COLUMN_VENUE + " TEXT NOT NULL, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_MATCH_NUMBER + " TEXT, "
                + COLUMN_TOURNAMENT + " TEXT, "
                + COLUMN_MATCH_RESULT + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + NEWS_TABLE + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + NEWS_TITLE + " TEXT NOT NULL, "
                + NEWS_DETAIL + " TEXT NOT NULL, "
                + NEWS_IMAGE_LINK + " TEXT);");


        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_TEST + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + PLAYER_NUMBER_OF_MATCHES + " INTEGER NOT NULL, "
                + BAT_INNS + " INTEGER NOT NULL, "
                + BAT_RUNS + " INTEGER NOT NULL, "
                + HIGHEST_SCORE + " INTEGER NOT NULL, "
                + BAT_AVG + " TEXT NOT NULL, "
                + BAT_STRIKE_RATE + " TEXT NOT NULL, "
                + BAT_HUNDREDS + " INTEGER NOT NULL, "
                + BAT_FIFTIES + " INTEGER NOT NULL, "
                + BALL_INNS + " INTEGER, "
                + BALL_RUNS + " INTEGER, "
                + BALL_WKTS + " INTEGER, "
                + BALL_BEST_BOLWING_SCORE + " TEXT, "
                + BALL_AVG + " TEXT, "
                + BALL_ECON + " TEXT, "
                + BALL_STRIKE_RATE + " TEXT, "
                + BALL_NO_OF_FIVE_WKTS + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_ODI + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + PLAYER_NUMBER_OF_MATCHES + " INTEGER NOT NULL, "
                + BAT_INNS + " INTEGER NOT NULL, "
                + BAT_RUNS + " INTEGER NOT NULL, "
                + HIGHEST_SCORE + " INTEGER NOT NULL, "
                + BAT_AVG + " TEXT NOT NULL, "
                + BAT_STRIKE_RATE + " TEXT NOT NULL, "
                + BAT_HUNDREDS + " INTEGER NOT NULL, "
                + BAT_FIFTIES + " INTEGER NOT NULL, "
                + BALL_INNS + " INTEGER, "
                + BALL_RUNS + " INTEGER, "
                + BALL_WKTS + " INTEGER, "
                + BALL_BEST_BOLWING_SCORE + " TEXT, "
                + BALL_AVG + " TEXT, "
                + BALL_ECON + " TEXT, "
                + BALL_STRIKE_RATE + " TEXT, "
                + BALL_NO_OF_FIVE_WKTS + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_T20 + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + PLAYER_NUMBER_OF_MATCHES + " INTEGER NOT NULL, "
                + BAT_INNS + " INTEGER NOT NULL, "
                + BAT_RUNS + " INTEGER NOT NULL, "
                + HIGHEST_SCORE + " INTEGER NOT NULL, "
                + BAT_AVG + " TEXT NOT NULL, "
                + BAT_STRIKE_RATE + " TEXT NOT NULL, "
                + BAT_HUNDREDS + " INTEGER NOT NULL, "
                + BAT_FIFTIES + " INTEGER NOT NULL, "
                + BALL_INNS + " INTEGER, "
                + BALL_RUNS + " INTEGER, "
                + BALL_WKTS + " INTEGER, "
                + BALL_BEST_BOLWING_SCORE + " TEXT, "
                + BALL_AVG + " TEXT, "
                + BALL_ECON + " TEXT, "
                + BALL_STRIKE_RATE + " TEXT, "
                + BALL_NO_OF_FIVE_WKTS + " INTEGER);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + SQUAD_TABLE + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + PLAYER_NUMBER + " TEXT NOT NULL, "
                + PLAYER_NAME + " TEXT NOT NULL, "
                + PLAYER_AGE + " TEXT NOT NULL, "
                + PLAYER_STYLE + " TEXT NOT NULL, "
                + PLAYER_ROLE + " TEXT,"
                + PLAYER_IMAGE_LINK + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + UPCOMING_TOURNAMENT_FIXTURE + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + COLUMN_DATE + " TEXT NOT NULL, "
                + COLUMN_BETWEEN + " TEXT NOT NULL, "
                + COLUMN_VENUE + " TEXT NOT NULL, "
                + COLUMN_TIME + " TEXT, "
                + COLUMN_MATCH_NUMBER + " TEXT, "
                + COLUMN_TOURNAMENT + " TEXT, "
                + COLUMN_MATCH_RESULT + " TEXT);");

        db.execSQL("CREATE TABLE IF NOT EXISTS " + HIGHLIGHTS_TABLE + " ("
                + COLUMN_MATCH_ID + " INTEGER primary key autoincrement, "
                + NEWS_TITLE + " TEXT NOT NULL, "
                + DURATION + " TEXT NOT NULL, "
                + NEWS_IMAGE_LINK + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS " + NATIONAL_TEAM_FIXTURE_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEST);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_T20);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ODI);
        db.execSQL("DROP TABLE IF EXISTS " + SQUAD_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NEWS_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + UPCOMING_TOURNAMENT_FIXTURE);
        db.execSQL("DROP TABLE IF EXISTS " + HIGHLIGHTS_TABLE);*/

        onCreate(db);
    }

    public static synchronized long insertFixtureValues(FixtureDataModel model, String tableName) {
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_DATE, model.getDate());
        cv.put(COLUMN_BETWEEN, model.getBetween());
        cv.put(COLUMN_VENUE, model.getVenue());
        cv.put(COLUMN_TIME, model.getTime());
        cv.put(COLUMN_MATCH_NUMBER, model.getMatchNumber());
        cv.put(COLUMN_MATCH_RESULT, model.getResult());
        cv.put(COLUMN_TOURNAMENT, model.getTournament());


        return getDatabase().insert(tableName, null, cv);
    }

    public static synchronized long insertCareerValues(CareerDataModel model, String tableName) {
        ContentValues cv = new ContentValues();
        // cv.put(COLUMN_MATCH_ID, model.getId());
        cv.put(PLAYER_NUMBER_OF_MATCHES, model.getMatches());
        cv.put(BAT_INNS, model.getBattingInns());
        cv.put(BAT_RUNS, model.getBattingRuns());
        cv.put(HIGHEST_SCORE, model.getBattingHighestScore());
        cv.put(BAT_AVG, model.getBattingAvg());
        cv.put(BAT_STRIKE_RATE, model.getBattingStrikeRate());
        cv.put(BAT_HUNDREDS, model.getBattingHundreds());
        cv.put(BAT_FIFTIES, model.getBattingFifties());
        cv.put(BALL_INNS, model.getBowlingInns());
        cv.put(BALL_RUNS, model.getBowlingRuns());
        cv.put(BALL_WKTS, model.getBowlingWickets());
        cv.put(BALL_BEST_BOLWING_SCORE, model.getBowlingBestScore());
        cv.put(BALL_AVG, model.getBowlingAvg());
        cv.put(BALL_ECON, model.getBowlingEcon());
        cv.put(BALL_STRIKE_RATE, model.getBowlingStrikeRate());
        cv.put(BALL_NO_OF_FIVE_WKTS, model.getBowlingNumberOfFiveWickets());


        return getDatabase().insert(tableName, null, cv);
    }

    public static synchronized long insertSquadValues(SquadModel model, String tableName) {
        ContentValues cv = new ContentValues();
        //cv.put(COLUMN_MATCH_ID, model.getId());
        cv.put(PLAYER_NUMBER, model.getPlayerNumber());
        cv.put(PLAYER_NAME, model.getPlayerName());
        cv.put(PLAYER_AGE, model.getAge());
        cv.put(PLAYER_ROLE, model.getRole());
        cv.put(PLAYER_STYLE, model.getStyle());
        cv.put(PLAYER_IMAGE_LINK, model.getImageLink());

        return getDatabase().insert(tableName, null, cv);
    }

    public static synchronized long insertNewsValues(NewsDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(NEWS_TITLE, model.getTitle());
        cv.put(NEWS_DETAIL, model.getFullNews());
        cv.put(NEWS_IMAGE_LINK, model.getImageLink());

        return getDatabase().insert(NEWS_TABLE, null, cv);
    }

    public static synchronized long insertHighlightsValues(HighlightsDataModel model) {
        ContentValues cv = new ContentValues();
        cv.put(NEWS_TITLE, model.getTitle());
        cv.put(DURATION, model.getDuration());
        cv.put(NEWS_IMAGE_LINK, model.getImageLink());

        return getDatabase().insert(HIGHLIGHTS_TABLE, null, cv);
    }


    public static synchronized int deleteAll(String tableName) {
        return getDatabase().delete(tableName, "1", null);
    }


    public static int deleteEntry(int id, String tableName) {
        return getDatabase().delete(tableName, COLUMN_MATCH_ID + "=" + id, null);
    }

    public static synchronized FixtureDataModel getData(int id, String tableName) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                COLUMN_DATE,
                COLUMN_BETWEEN,
                COLUMN_VENUE,
                COLUMN_TIME,
                COLUMN_MATCH_NUMBER,
                COLUMN_MATCH_RESULT
        };
        Cursor c = getDatabase().query(tableName, columns, COLUMN_MATCH_ID + "=" + id, null, null, null,
                null);
        FixtureDataModel model = null;

        if (c.moveToFirst()) {

            model = new FixtureDataModel();
            model.setId(c.getInt(0));
            model.setDate(c.getString(1));
            model.setBetween(c.getString(2));
            model.setVenue(c.getString(3));
            model.setTime(c.getString(4));
            model.setMatchNumber((c.getString(5)));
            model.setResult(c.getString(6));

        }
        c.close();
        return model;
    }

    public static synchronized Cursor getCursor(String tableName) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                COLUMN_DATE,
                COLUMN_BETWEEN,
                COLUMN_VENUE,
                COLUMN_TIME,
                COLUMN_MATCH_NUMBER,
                COLUMN_TOURNAMENT,
                COLUMN_MATCH_RESULT
        };
        return getDatabase().query(tableName, columns, null, null, null, null, null);
    }

    public static synchronized ArrayList<FixtureDataModel> getAll(String tableName) {
        ArrayList<FixtureDataModel> list = new ArrayList<>();
        Cursor c = Database.getCursor(tableName);
        if (c.moveToFirst()) {

            do {
                FixtureDataModel model = new FixtureDataModel();
                model.setId(c.getInt(0));
                model.setDate(c.getString(1));
                model.setBetween(c.getString(2));
                model.setVenue(c.getString(3));
                model.setTime(c.getString(4));
                model.setMatchNumber((c.getString(5)));
                model.setTournament(c.getString(6));
                model.setResult(c.getString(7));
                list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized Cursor getCursorCareer(String tableName, int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER_OF_MATCHES,
                BAT_INNS,
                BAT_RUNS,
                HIGHEST_SCORE,
                BAT_AVG,
                BAT_STRIKE_RATE,
                BAT_HUNDREDS,
                BAT_FIFTIES,
                BALL_INNS,
                BALL_RUNS,
                BALL_WKTS,
                BALL_BEST_BOLWING_SCORE,
                BALL_AVG,
                BALL_ECON,
                BALL_STRIKE_RATE,
                BALL_NO_OF_FIVE_WKTS
        };
        return getDatabase().query(tableName, columns, COLUMN_MATCH_ID + "=" + id, null, null, null, null);
    }

    public static synchronized CareerDataModel getCareerData(int id, String tableName) {
        Cursor c = Database.getCursorCareer(tableName, id);
        CareerDataModel model = new CareerDataModel();
        if (c.moveToFirst()) {

            do {
                model.setId(c.getInt(0));
                model.setMatches(c.getInt(1));
                model.setBattingInns(c.getInt(2));
                model.setBattingRuns(c.getInt(3));
                model.setBattingHighestScore(c.getInt(4));
                model.setBattingAvg(c.getString(5));
                model.setBattingStrikeRate(c.getString(6));
                model.setBattingHundreds(c.getInt(7));
                model.setBattingFifties(c.getInt(8));
                model.setBowlingInns(c.getInt(9));
                model.setBowlingRuns(c.getInt(10));
                model.setBowlingWickets(c.getInt(11));
                model.setBowlingBestScore(c.getString(12));
                model.setBowlingAvg(c.getString(13));
                model.setBowlingEcon(c.getString(14));
                model.setBowlingStrikeRate(c.getString(15));
                model.setBowlingNumberOfFiveWickets(c.getInt(16));


            } while (c.moveToNext());
        }
        c.close();
        return model;
    }

    public static synchronized Cursor getSquadCursor() {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER,
                PLAYER_NAME,
                PLAYER_AGE,
                PLAYER_ROLE,
                PLAYER_STYLE,
                PLAYER_IMAGE_LINK
        };
        return getDatabase().query(SQUAD_TABLE, columns, null, null, null, null, null);
    }

    public static List<SquadModel> getWholeSquad(String tableName) {
        List<SquadModel> list = new ArrayList<>();
        Cursor c = Database.getSquadCursor();
        if (c.moveToFirst()) {

            do {
                SquadModel model = new SquadModel();
                model.setId(c.getInt(0));
                model.setPlayerNumber(c.getString(1));
                model.setPlayerName(c.getString(2));
                model.setAge(c.getString(3));
                model.setRole(c.getString(4));
                model.setStyle(c.getString(5));
                model.setImageLink(c.getString(6));

                list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized Cursor getSquadCursor(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                PLAYER_NUMBER,
                PLAYER_NAME,
                PLAYER_AGE,
                PLAYER_ROLE,
                PLAYER_STYLE,
                PLAYER_IMAGE_LINK
        };
        return getDatabase().query(SQUAD_TABLE, columns, COLUMN_MATCH_ID + "=" + id, null, null, null, null);
    }

    public static SquadModel getWholeSquad(String tableName, int id) {
        //List<SquadModel> list = new ArrayList<>();
        SquadModel model = new SquadModel();
        Cursor c = Database.getSquadCursor(id);
        if (c.moveToFirst()) {

            do {

                model.setId(c.getInt(0));
                model.setPlayerNumber(c.getString(1));
                model.setPlayerName(c.getString(2));
                model.setAge(c.getString(3));
                model.setRole(c.getString(4));
                model.setStyle(c.getString(5));
                model.setImageLink(c.getString(6));

                //list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return model;
    }

    public static synchronized Cursor getNewsCursor() {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                NEWS_TITLE,
                NEWS_DETAIL,
                NEWS_IMAGE_LINK
        };
        return getDatabase().query(NEWS_TABLE, columns, null, null, null, null, null);
    }

    public static List<NewsDataModel> getAllNews() {
        List<NewsDataModel> list = new ArrayList<>();
        Cursor c = Database.getNewsCursor();
        if (c.moveToFirst()) {

            do {
                NewsDataModel model = new NewsDataModel();
                model.setId(c.getInt(0));
                model.setTitle(c.getString(1));
                model.setFullNews(c.getString(2));
                model.setImageLink(c.getString(3));

                list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized Cursor getHighLightsCursor() {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                NEWS_TITLE,
                DURATION,
                NEWS_IMAGE_LINK
        };
        return getDatabase().query(HIGHLIGHTS_TABLE, columns, null, null, null, null, null);
    }

    public static List<HighlightsDataModel> getAllHighlights() {
        List<HighlightsDataModel> list = new ArrayList<>();
        Cursor c = Database.getHighLightsCursor();
        if (c.moveToFirst()) {

            do {
                HighlightsDataModel model = new HighlightsDataModel();
                model.setId(c.getInt(0));
                model.setTitle(c.getString(1));
                model.setDuration(c.getString(2));
                model.setImageLink(c.getString(3));

                list.add(model);


            } while (c.moveToNext());
        }
        c.close();
        return list;
    }

    public static synchronized Cursor getNewsCursorById(int id) {
        // TODO Auto-generated method stub
        String[] columns = new String[]{
                COLUMN_MATCH_ID,
                NEWS_TITLE,
                NEWS_DETAIL,
                NEWS_IMAGE_LINK
        };
        return getDatabase().query(NEWS_TABLE, columns, COLUMN_MATCH_ID + "=" + id, null, null, null, null);
    }

    public static synchronized NewsDataModel getNews(int id) {
        Cursor c = Database.getNewsCursorById(id);
        NewsDataModel model = new NewsDataModel();
        if (c.moveToFirst()) {

            do {

                model.setId(c.getInt(0));
                model.setTitle(c.getString(1));
                model.setFullNews(c.getString(2));
                model.setImageLink(c.getString(3));


            } while (c.moveToNext());
        }
        c.close();
        return model;
    }

}
