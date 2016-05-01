package com.androidproject.popularmovies11.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {

    // The "Content authority" is a name for the entire content provider
    public static final String CONTENT_AUTHORITY = "com.androidproject.popularmovies11";

    // Create the base of all URI's which apps will use to contact the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_POPULAR_MOVIES = "popular";
    public static final String PATH_TOP_MOVIES = "top_rated";
    public static final String PATH_FAVORITE_MOVIES = "favorite";

    public static final String COLUMN_MOVIE_ID = "movie_id";
    public static final String COLUMN_MOVIE_TITLE = "movie_title";
    public static final String COLUMN_RELEASE_DATE = "movie_release_date";
    public static final String COLUMN_MOVIE_RATING = "movie_rating";
    public static final String COLUMN_MOVIE_VOTE_COUNT = "movie_vote_count";
    public static final String COLUMN_MOVIE_POSTER_PATH = "movie_poster_path";
    public static final String COLUMN_MOVIE_BACKDROP_PATH = "movie_backdrop_path";
    public static final String COLUMN_MOVIE_OVERVIEW = "movie_overview";
    public static final String COLUMN_MOVIE_CATEGORY = "movie_category";


    public static final class PopularEntry implements BaseColumns{
        //content://com.androidproject.popularmovies11/popular
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POPULAR_MOVIES).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;

        public static final String TABLE_NAME = "popular";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriforMovieId(String id) {
            return CONTENT_URI
                    .buildUpon()
                    .appendPath(id)
                    .build();
        }
    }

    public static final class TopEntry implements BaseColumns{
        //content://com.androidproject.popularmovies11/popular
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOP_MOVIES).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_POPULAR_MOVIES;

        public static final String TABLE_NAME = "top_rated";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriforMovieId(String id) {
            return CONTENT_URI
                    .buildUpon()
                    .appendPath(id)
                    .build();
        }
    }

    public static final class FavEntry implements BaseColumns{
        //content://com.androidproject.popularmovies11/favorite
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_FAVORITE_MOVIES).build();
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FAVORITE_MOVIES;

        public static final String TABLE_NAME = "favorite";

        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildUriforMovieId(String id) {
            return CONTENT_URI
                    .buildUpon()
                    .appendPath(id)
                    .build();
        }
    }

    public static String getMovieCategoryfromUri(Uri uri){
        return uri.getPathSegments().get(0);
    }

    public static String getMovieIdfromUri(Uri uri){
        return uri.getPathSegments().get(1);
    }
}
