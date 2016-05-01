package com.androidproject.popularmovies11.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class MovieProvider extends ContentProvider{

    private static final String LOG_TAG = MovieProvider.class.getSimpleName();

    MovieDBHelper mDBHelper;
    // The URI Matcher used by this content provider. (only use of build URI Matcher)
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int POPULAR_MOVIES = 301;
    static final int TOP_MOVIES = 302;
    static final int FAVORITE_MOVIES = 303;
    static final int MOVIES_WITH_ID = 201;

    // Matches the URI and sets a code to return when URI matches
    static UriMatcher buildUriMatcher() {

        // Begin with no_match as we don't want the root node to match anything
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        //com.androidproject.popularmovies11
        final String authority = MovieContract.CONTENT_AUTHORITY;
        //com.androidproject.popularmovies11/popular
        matcher.addURI(authority, MovieContract.PATH_POPULAR_MOVIES, POPULAR_MOVIES);
        //com.androidproject.popularmovies11/top_rated
        matcher.addURI(authority,MovieContract.PATH_TOP_MOVIES,TOP_MOVIES);
        //com.androidproject.popularmovies11/favorite
        matcher.addURI(authority,MovieContract.PATH_FAVORITE_MOVIES,FAVORITE_MOVIES);
        //com.androidproject.popularmovies11/popular/<movie_id>
        matcher.addURI(authority,MovieContract.PATH_POPULAR_MOVIES + "/#", MOVIES_WITH_ID);
        //com.androidproject.popularmovies11/top_rated/<movie_id>
        matcher.addURI(authority,MovieContract.PATH_TOP_MOVIES + "/#", MOVIES_WITH_ID);
        //com.androidproject.popularmovies11/favorite/<movie_id>
        matcher.addURI(authority,MovieContract.PATH_FAVORITE_MOVIES + "/#", MOVIES_WITH_ID);
        return matcher;
    }

    private static final String sMovieIdSelection = MovieContract.COLUMN_MOVIE_ID + " = ? ";

    private Cursor getMovieByCategory(Uri uri, String[] projection, String sortOrder) {
        String category = MovieContract.getMovieCategoryfromUri(uri);
        String tableName;
        if (category.equals("popular")) {
            tableName = "popular";
        } else if (category.equals("top_rated")) {
            tableName = "top_rated";
        } else{
            tableName = "favorite";
        }
        return mDBHelper.getReadableDatabase().query(
                tableName,
                projection,
                null,
                null,
                null,
                null,
                null,
                sortOrder);
    }

    private Cursor getMovieByMovieId(Uri uri, String[] projection){
        String tableName = MovieContract.getMovieCategoryfromUri(uri);
        String movie_id = MovieContract.getMovieIdfromUri(uri);

        return mDBHelper.getReadableDatabase().query(
                tableName,
                projection,
                sMovieIdSelection,
                new String[]{movie_id},
                null,
                null,
                null,
                null);
    }

    @Override
    public boolean onCreate() {
        mDBHelper = new MovieDBHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;

        switch (sUriMatcher.match(uri)) {
            case MOVIES_WITH_ID:{
                retCursor = getMovieByMovieId(uri,projection);
                break;
            }
            case POPULAR_MOVIES:{
                retCursor = getMovieByCategory(uri,projection,sortOrder);
                break;
            }
            case TOP_MOVIES:{
                retCursor = getMovieByCategory(uri,projection,sortOrder);
                break;
            }
            case FAVORITE_MOVIES:{
                Log.e(LOG_TAG, "query favorite movies");
                retCursor = getMovieByCategory(uri,projection,sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public String getType(Uri uri) {
        // Use the Uri Matcher to determine what kind of URI this is.
        final int match = sUriMatcher.match(uri);

        switch(match){
            case MOVIES_WITH_ID:{
                String tableName = MovieContract.getMovieCategoryfromUri(uri);
                if (tableName.equals(MovieContract.PopularEntry.TABLE_NAME)) {
                    return MovieContract.PopularEntry.CONTENT_ITEM_TYPE;
                } else{
                    return MovieContract.TopEntry.CONTENT_ITEM_TYPE;
                }
            }
            case POPULAR_MOVIES:{
                return MovieContract.PopularEntry.CONTENT_TYPE;
            }
            case TOP_MOVIES:{
                return MovieContract.TopEntry.CONTENT_TYPE;
            }
            case FAVORITE_MOVIES:{
                Log.e(LOG_TAG, "type favorite movies");
                return MovieContract.FavEntry.CONTENT_TYPE;
            }
        }
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch(match) {
            case POPULAR_MOVIES:{
                long _id = db.insert(MovieContract.PopularEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    //content://com.androidproject.popularmovies11/popular/<id>
                    returnUri = MovieContract.PopularEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case TOP_MOVIES:{
                long _id = db.insert(MovieContract.TopEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    //content://com.androidproject.popularmovies11/top_rated/<id>
                    returnUri = MovieContract.TopEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case FAVORITE_MOVIES:{
                Log.e(LOG_TAG, "insert favorite movies");
                long _id = db.insert(MovieContract.FavEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    //content://com.androidproject.popularmovies11/favorite/<id>
                    returnUri = MovieContract.FavEntry.buildMovieUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        db.close();
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        //this makes delete all rows and return the number of rows deleted
        if (selection == null) selection = "1";
        switch (match) {
            case POPULAR_MOVIES:{
                rowsDeleted = db.delete(MovieContract.PopularEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case TOP_MOVIES:{
                rowsDeleted = db.delete(MovieContract.TopEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            case FAVORITE_MOVIES:{
                Log.e(LOG_TAG, "delete favorite movies");
                rowsDeleted = db.delete(MovieContract.FavEntry.TABLE_NAME, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted!=0){
            // notifies the observer about no. of rows deleted
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch(match){
            case POPULAR_MOVIES:{
                rowsUpdated = db.update(MovieContract.PopularEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case TOP_MOVIES:{
                rowsUpdated = db.update(MovieContract.TopEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            case FAVORITE_MOVIES:{
                Log.e(LOG_TAG, "update favorite movies");
                rowsUpdated = db.update(MovieContract.FavEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated!=0){
            // notifies the observer about no. of rows deleted
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        String tableName = MovieContract.getMovieCategoryfromUri(uri);
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();
        db.beginTransaction();
        db.delete(tableName,null,null);
        int returnCount = 0;
        try {
            for (ContentValues value : values) {
                long _id = db.insert(tableName, null, value);
                if (_id != -1) {
                    returnCount++;
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnCount;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mDBHelper.close();
        super.shutdown();
    }

}
