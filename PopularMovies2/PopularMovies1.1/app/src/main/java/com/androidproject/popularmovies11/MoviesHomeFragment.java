package com.androidproject.popularmovies11;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidproject.popularmovies11.data.MovieContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesHomeFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    private final String LOG_TAG = MoviesHomeFragment.class.getSimpleName();

    private static final int MOVIE_LOADER = 1;
    private static final String SELECTED_KEY = "position";
    private MovieGridAdapter mAdapter;
    private int mPosition;
    private GridView mGridView;

    private static final String[] POP_MOVIE_COLUMNS = {
            MovieContract.PopularEntry._ID,
            MovieContract.COLUMN_MOVIE_ID,
            MovieContract.COLUMN_MOVIE_TITLE,
            MovieContract.COLUMN_RELEASE_DATE,
            MovieContract.COLUMN_MOVIE_RATING,
            MovieContract.COLUMN_MOVIE_VOTE_COUNT,
            MovieContract.COLUMN_MOVIE_POSTER_PATH,
            MovieContract.COLUMN_MOVIE_BACKDROP_PATH,
            MovieContract.COLUMN_MOVIE_OVERVIEW,
            MovieContract.COLUMN_MOVIE_CATEGORY
    };
    private static final String[] TOP_MOVIE_COLUMNS = {
            MovieContract.TopEntry._ID,
            MovieContract.COLUMN_MOVIE_ID,
            MovieContract.COLUMN_MOVIE_TITLE,
            MovieContract.COLUMN_RELEASE_DATE,
            MovieContract.COLUMN_MOVIE_RATING,
            MovieContract.COLUMN_MOVIE_VOTE_COUNT,
            MovieContract.COLUMN_MOVIE_POSTER_PATH,
            MovieContract.COLUMN_MOVIE_BACKDROP_PATH,
            MovieContract.COLUMN_MOVIE_OVERVIEW,
            MovieContract.COLUMN_MOVIE_CATEGORY
    };

    private static final String[] FAV_MOVIE_COLUMNS = {
            MovieContract.FavEntry._ID,
            MovieContract.COLUMN_MOVIE_ID,
            MovieContract.COLUMN_MOVIE_TITLE,
            MovieContract.COLUMN_RELEASE_DATE,
            MovieContract.COLUMN_MOVIE_RATING,
            MovieContract.COLUMN_MOVIE_VOTE_COUNT,
            MovieContract.COLUMN_MOVIE_POSTER_PATH,
            MovieContract.COLUMN_MOVIE_BACKDROP_PATH,
            MovieContract.COLUMN_MOVIE_OVERVIEW,
            MovieContract.COLUMN_MOVIE_CATEGORY
    };

    static final int COL_ID = 0;
    static final int COL_MOVIE_ID = 1;
    static final int COL_MOVIE_TITLE = 2;
    static final int COL_RELEASE_DATE = 3;
    static final int COL_MOVIE_RATING = 4;
    static final int COL_MOVIE_VOTE_COUNT = 5;
    static final int COL_MOVIE_POSTER_PATH = 6;
    static final int COL_MOVIE_BACKDROP_PATH = 6;
    static final int COL_MOVIE_OVERVIEW = 7;
    static final int COL_MOVIE_CATEGORY = 8;

    public MoviesHomeFragment() {}

    public void onSaveInstanceState(Bundle outState){
        if (mPosition!=GridView.INVALID_POSITION){
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     *  Description: This method is used to inflate the Home Page (Fragment) to display a grid view
     *  showing posters and titles of user preferred movies. It is  and used to send intent on click
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_home, container, false);
        mAdapter = new MovieGridAdapter(getActivity(), null, 0);

        mGridView =(GridView)rootView.findViewById(R.id.moviegrid);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position); // returns the cursor at (position)
                if (cursor!=null){
                    String pref = Utility.getSortPreference(getActivity());
                    if (pref.equals("popular")){
                        ((Callback) getActivity())
                                .onItemSelected(MovieContract.PopularEntry.buildUriforMovieId(cursor.getString(COL_MOVIE_ID)));
                    }else if (pref.equals("top_rated")){
                        ((Callback) getActivity())
                                .onItemSelected(MovieContract.TopEntry.buildUriforMovieId(cursor.getString(COL_MOVIE_ID)));
                    } else{
                        ((Callback) getActivity())
                                .onItemSelected(MovieContract.FavEntry.buildUriforMovieId(cursor.getString(COL_MOVIE_ID)));
                    }
                }
                mPosition = position;
            }
        });

        if(savedInstanceState!=null && savedInstanceState.containsKey(SELECTED_KEY)){
            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootView;
    }

    /**
     *  This method passes the preferences as parameters and calls the background thread to fetch
     *  the required movie data.
     */
    private void getMovieData(){
        //Call FetchPopularMovies to fetch movie data using Async Task (background thread)
        String preference = Utility.getSortPreference(getActivity());
        FetchPopularMovies popularMovies = new FetchPopularMovies(getActivity());
        popularMovies.execute(preference);
    }

    void onPreferenceChange(String preference){
        if (!preference.equals("favorite")) {
            getMovieData();
        }
        getLoaderManager().restartLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // Ensures a loader is initialized and active. A new Loader is created and started or last one is re-used
        String pref = Utility.getSortPreference(getActivity());
        onPreferenceChange(pref);
        getLoaderManager().initLoader(MOVIE_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String pref = Utility.getSortPreference(getActivity());
        Uri content_uri;
        String[] Movie_Columns;
        if (pref.equals("popular")){
            content_uri = MovieContract.PopularEntry.CONTENT_URI;
            Movie_Columns = POP_MOVIE_COLUMNS;
        }else if (pref.equals("top_rated")){
            content_uri = MovieContract.TopEntry.CONTENT_URI;
            Movie_Columns = TOP_MOVIE_COLUMNS;
        }else{
            content_uri = MovieContract.FavEntry.CONTENT_URI;
            Movie_Columns = FAV_MOVIE_COLUMNS;
        }
        return new CursorLoader(getActivity(),
                content_uri,
                Movie_Columns,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        mAdapter.swapCursor(cursor);
        if(mPosition!=GridView.INVALID_POSITION){
            mGridView.setSelection(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }

    public interface Callback {
        public void onItemSelected(Uri contentUri);
    }
 }
