package com.androidproject.popularmovies11;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidproject.popularmovies11.data.MovieContract;
import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();

    private static final String MOVIE_SHARE_HASHTAG = "#PopularMovies2";
    private ShareActionProvider mShareActionProvider;


    private static final int DETAIL_LOADER = 0;
    public static final String DETAIL_URI = "DETAILURI";
    private Uri mUri;


    // Passed as a selection parameter to cursor loader to load all the required columns
    private static final String[] POP_MOVIE_COLUMNS = {
            MovieContract.PopularEntry.TABLE_NAME + "." + MovieContract.PopularEntry._ID,
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
            MovieContract.TopEntry.TABLE_NAME + "." + MovieContract.TopEntry._ID,
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
            MovieContract.FavEntry.TABLE_NAME + "." + MovieContract.FavEntry._ID,
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

    public static final int COL_ID = 0;
    public static final int COL_MOVIE_ID = 1;
    public static final int COL_MOVIE_TITLE = 2;
    public static final int COL_RELEASE_DATE = 3;
    public static final int COL_MOVIE_RATING = 4;
    public static final int COL_MOVIE_VOTE_COUNT = 5;
    public static final int COL_MOVIE_POSTER_PATH = 6;
    public static final int COL_MOVIE_BACKDROP_PATH = 7;
    public static final int COL_MOVIE_OVERVIEW = 8;
    public static final int COL_MOVIE_CATEGORY = 9;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args!=null){
            mUri = args.getParcelable(DetailActivityFragment.DETAIL_URI);
        }

        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(DETAIL_LOADER, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    void onPreferenceChange(){
        getLoaderManager().restartLoader(DETAIL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (mUri != null) {
            String pref = Utility.getSortPreference(getActivity());
            String[] MOVIE_COLUMNS;
            if (pref.equals("popular")) {
                MOVIE_COLUMNS = POP_MOVIE_COLUMNS;
            } else if (pref.equals("top_rated")) {
                MOVIE_COLUMNS = TOP_MOVIE_COLUMNS;
            } else {
                MOVIE_COLUMNS = FAV_MOVIE_COLUMNS;
            }

            return new CursorLoader(
                    getActivity(),
                    mUri,
                    MOVIE_COLUMNS,
                    null,
                    null,
                    null);
        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, final Cursor cursor) {
        if (!cursor.moveToFirst()) return;
        ViewHolder viewHolder = (ViewHolder) getView().getTag();
        viewHolder.titleView.setText(cursor.getString(COL_MOVIE_TITLE));
        viewHolder.overviewView.setText(cursor.getString(COL_MOVIE_OVERVIEW));
        viewHolder.ratingView.setText(cursor.getString(COL_MOVIE_RATING));
        viewHolder.voteCountView.setText(cursor.getString(COL_MOVIE_VOTE_COUNT));
        viewHolder.releaseDateView.setText(cursor.getString(COL_RELEASE_DATE));

        ImageView movie_icon = (ImageView) getView().findViewById(R.id.backdrop_image);
        Picasso
                .with(getActivity())
                .load(cursor.getString(COL_MOVIE_BACKDROP_PATH))
                .into(movie_icon);

        Button trailer = (Button) getView().findViewById(R.id.but_trail);
        trailer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrailerActivity.class);
                i.putExtra(Intent.EXTRA_TEXT, cursor.getString(COL_MOVIE_ID));
                startActivity(i);
            }
        });

        Button review = (Button) getView().findViewById(R.id.but_rev);
        review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), ReviewActivity.class);
                i.putExtra(Intent.EXTRA_TEXT, cursor.getString(COL_MOVIE_ID));
                startActivity(i);
            }
        });

        Button favorite = (Button) getView().findViewById(R.id.but_fav);
        if (Utility.getSortPreference(getActivity()).equals("favorite")){
            favorite.setVisibility(View.GONE);
            FrameLayout fl = (FrameLayout) getView().findViewById(R.id.fav_border);
            fl.setVisibility(View.GONE);
        }
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFavorite(
                        cursor.getString(COL_MOVIE_ID),
                        cursor.getString(COL_MOVIE_CATEGORY),
                        cursor.getString(COL_MOVIE_TITLE),
                        cursor.getString(COL_MOVIE_OVERVIEW),
                        cursor.getString(COL_MOVIE_RATING),
                        cursor.getString(COL_MOVIE_VOTE_COUNT),
                        cursor.getString(COL_RELEASE_DATE),
                        cursor.getString(COL_MOVIE_BACKDROP_PATH),
                        cursor.getString(COL_MOVIE_POSTER_PATH));
            }
        });

        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareMovieInformation());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public static class ViewHolder {

        public final TextView titleView;
        public final TextView overviewView;
        public final TextView ratingView;
        public final TextView voteCountView;
        public final TextView releaseDateView;
        public final Button favoriteButton;
        public final Button reviewButton;
        public final Button trailerButton;

        public ViewHolder (View view){
            titleView= (TextView) view.findViewById(R.id.movie_title);
            overviewView = (TextView) view.findViewById(R.id.description);
            ratingView = (TextView) view.findViewById(R.id.rating);
            voteCountView = (TextView) view.findViewById(R.id.votes);
            releaseDateView = (TextView) view.findViewById(R.id.release_date);
            favoriteButton = (Button) view.findViewById(R.id.but_fav);
            reviewButton = (Button) view.findViewById(R.id.but_rev);
            trailerButton = (Button) view.findViewById(R.id.but_trail);
        }
    }

    // This will add/remove data from the favorite table
    public void updateFavorite(String movID, String movCategory, String movTitle, String movOverview,
                               String movRating, String movVoteCount, String movReleaseDate, String backdropURL, String movPosterPath) {
        int rowsDeleted =
                getActivity()
                        .getContentResolver()
                        .delete(
                                MovieContract.FavEntry.CONTENT_URI,
                                MovieContract.COLUMN_MOVIE_ID + "=?",
                                new String[]{movID});

        if (rowsDeleted == 0){
            ContentValues cv = new ContentValues();
            cv.put(MovieContract.COLUMN_MOVIE_ID, movID);
            cv.put(MovieContract.COLUMN_MOVIE_TITLE, movTitle);
            cv.put(MovieContract.COLUMN_RELEASE_DATE, movReleaseDate);
            cv.put(MovieContract.COLUMN_MOVIE_RATING, movRating);
            cv.put(MovieContract.COLUMN_MOVIE_VOTE_COUNT, movVoteCount);
            cv.put(MovieContract.COLUMN_MOVIE_POSTER_PATH, movPosterPath);
            cv.put(MovieContract.COLUMN_MOVIE_BACKDROP_PATH, backdropURL);
            cv.put(MovieContract.COLUMN_MOVIE_OVERVIEW, movOverview);
            cv.put(MovieContract.COLUMN_MOVIE_CATEGORY, movCategory);

            Uri insertedUri = getActivity().getContentResolver().insert(MovieContract.FavEntry.CONTENT_URI, cv);
            //Add Toast
            Toast.makeText(getActivity(),"Added Movie '" + movTitle + "' to Favorites",Toast.LENGTH_SHORT).show();
        } else{
            // Add deleted toast
            Toast.makeText(getActivity(),"Deleted Movie '" + movTitle + "' from Favorites",Toast.LENGTH_SHORT).show();
        }
    }

    //Options Menu
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.shareaction, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mShareActionProvider != null) {
            mShareActionProvider.setShareIntent(shareMovieInformation());
        } else {
            Log.d(LOG_TAG, "Share Action Provider is null");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share) {
            shareMovieInformation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent shareMovieInformation() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Checkout details, reviews and trailers of my Favorite Movie at " + mUri + " " +MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }
}
