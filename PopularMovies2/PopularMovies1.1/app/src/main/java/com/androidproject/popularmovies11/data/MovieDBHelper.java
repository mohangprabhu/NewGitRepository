package com.androidproject.popularmovies11.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MovieDBHelper extends SQLiteOpenHelper{

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 11;
    static final String DATABASE_NAME = "movies.db"; // Name of the database in the file system

    public MovieDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Writes and executes SQL statements to CREATE all local database tables
    @Override
    public void onCreate(SQLiteDatabase sqlitedb) {

        final String SQL_CREATE_POPULAR_TABLE = "CREATE TABLE " + MovieContract.PopularEntry.TABLE_NAME + " (" +
                MovieContract.PopularEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieContract.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_CATEGORY + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_TOP_TABLE = "CREATE TABLE " + MovieContract.TopEntry.TABLE_NAME + " (" +
                MovieContract.TopEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieContract.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_CATEGORY + " TEXT NOT NULL " +
                ");";

        final String SQL_CREATE_FAV_TABLE = "CREATE TABLE " + MovieContract.FavEntry.TABLE_NAME + " (" +
                MovieContract.FavEntry._ID + " INTEGER PRIMARY KEY," +
                MovieContract.COLUMN_MOVIE_ID + " INTEGER UNIQUE NOT NULL, " +
                MovieContract.COLUMN_MOVIE_TITLE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_RELEASE_DATE + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_RATING + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_VOTE_COUNT + " INTEGER NOT NULL, " +
                MovieContract.COLUMN_MOVIE_POSTER_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_BACKDROP_PATH + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_OVERVIEW + " TEXT NOT NULL, " +
                MovieContract.COLUMN_MOVIE_CATEGORY + " TEXT NOT NULL " +
                ");";

        sqlitedb.execSQL(SQL_CREATE_POPULAR_TABLE);
        sqlitedb.execSQL(SQL_CREATE_TOP_TABLE);
        sqlitedb.execSQL(SQL_CREATE_FAV_TABLE);

    }

    // Writes and executes SQL statements to UPGRADE all local database tables
    @Override
    public void onUpgrade(SQLiteDatabase sqlitedb, int oldVersion, int newVersion) {

        sqlitedb.execSQL("DROP TABLE IF EXISTS " + MovieContract.PopularEntry.TABLE_NAME);
        sqlitedb.execSQL("DROP TABLE IF EXISTS " + MovieContract.TopEntry.TABLE_NAME);
        sqlitedb.execSQL("DROP TABLE IF EXISTS " + MovieContract.FavEntry.TABLE_NAME);
        onCreate(sqlitedb);

    }
}
