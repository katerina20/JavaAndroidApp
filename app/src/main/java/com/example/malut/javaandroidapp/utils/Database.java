package com.example.malut.javaandroidapp.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.malut.javaandroidapp.model.Track;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class Database {

    private final Context context;
    private  DBHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;


    public Database(Context context) {
        this.context = context;
    }

    public void open() {
        dbHelper = new DBHelper(context, Consts.DB_NAME, null, Consts.DB_VERSION);
        sqLiteDatabase = dbHelper.getWritableDatabase();
    }

    public void close() {
        if (dbHelper != null) {
            dbHelper.close();
        }
    }

    public Cursor getAllData(String table) {
        return sqLiteDatabase.query(table, null, null, null, null, null, Consts.DB_COL_ID_PRIMARY + " DESC");
    }

    public void clearData(String table) {
        sqLiteDatabase.delete(table, null, null);
    }

    public void addApiData(List<Track> tracks, String table) {
        if (tracks.size() != 0) {
            for (int i = tracks.size() - 1; i >= 0; i--) {
                addInfo(tracks.get(i), table);
            }
        }
    }

    private void addInfo(Track track, String table) {
        ContentValues cv = new ContentValues();
        cv.put(Consts.DB_COL_TRACK_NAME, track.getTrackName());
        cv.put(Consts.DB_COL_ID, track.getTrackId());
        cv.put(Consts.DB_COL_ARTIST_NAME, track.getArtistName());
        cv.put(Consts.DB_COL_COUNTRY, track.getCountry());
        cv.put(Consts.DB_COL_ALBUM_NAME, track.getAlbumName());
        cv.put(Consts.DB_COL_TRACK_TIME, track.getTrackTimeInMillis());
        cv.put(Consts.DB_COL_GENRE, track.getGenreName());
        cv.put(Consts.DB_COL_TRACK_IMAGE, track.getTrackImage());
        cv.put(Consts.DB_COL_RELEASE_DATE, new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).format(track.getReleaseDate()));

        sqLiteDatabase.insert(table, null, cv);
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(Consts.createDB(Consts.DB_TABLE_ALL_NAME));
            db.execSQL(Consts.createDB(Consts.DB_TABLE_BY_ARTIST_NAME));
            db.execSQL(Consts.createDB(Consts.DB_TABLE_BY_TRACK_NAME));
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(Consts.deleteEntriesDB(Consts.DB_TABLE_ALL_NAME));
            db.execSQL(Consts.deleteEntriesDB(Consts.DB_TABLE_BY_TRACK_NAME));
            db.execSQL(Consts.deleteEntriesDB(Consts.DB_TABLE_BY_ARTIST_NAME));

            onCreate(db);
        }
    }
}
