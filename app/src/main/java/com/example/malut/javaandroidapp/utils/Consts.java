package com.example.malut.javaandroidapp.utils;

public class Consts {

    public final static String DB_NAME = "search_results_db";
    public final static String DB_TABLE_ALL_NAME = "search_results_all_db";
    public final static String DB_TABLE_BY_TRACK_NAME = "search_results_by_track_db";
    public final static String DB_TABLE_BY_ARTIST_NAME = "search_results_by_artist_db";
    public final static String DB_COL_ID_PRIMARY = "_id";
    public final static String DB_COL_ID = "track_id";
    public final static String DB_COL_TRACK_NAME = "track_name";
    public final static String DB_COL_ARTIST_NAME = "artist_name";
    public final static String DB_COL_COUNTRY = "country";
    public final static String DB_COL_ALBUM_NAME = "album_name";
    public final static String DB_COL_TRACK_TIME = "track_time";
    public final static String DB_COL_GENRE = "genre";
    public final static String DB_COL_TRACK_IMAGE = "track_image";
    public final static String DB_COL_RELEASE_DATE = "release_date";
    public final static int DB_VERSION = 1;

    public static String createDB (String table) {
        return  "create table " + table + "(" +
                DB_COL_ID_PRIMARY + " integer primary key autoincrement, " +
                DB_COL_ID + " integer, " +
                DB_COL_TRACK_NAME + " text, " +
                DB_COL_ARTIST_NAME + " text," +
                DB_COL_COUNTRY + " text," +
                DB_COL_ALBUM_NAME + " text," +
                DB_COL_TRACK_TIME + " text," +
                DB_COL_GENRE + " text," +
                DB_COL_RELEASE_DATE + " text," +
                DB_COL_TRACK_IMAGE + " text," +
                " UNIQUE ( " + DB_COL_ID + " ) ON CONFLICT IGNORE" +
                ");";

    }

    public static String deleteEntriesDB (String table){
        return "DROP TABLE IF EXISTS " + table;
    }





}
