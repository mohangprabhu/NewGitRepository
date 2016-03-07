package com.androidproject.popularmovies11;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {
    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private static final String MOVIE_SHARE_HASHTAG = "#PopularMovies1";
    private static final String SHARE_TEXT = "SHARE";

    private String mShareMovieDetails;
    private ShareActionProvider mShareActionProvider;

    public DetailActivityFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();
        if(intent != null&&intent.hasExtra(Intent.EXTRA_TEXT)) {
            //used Serializable as only one Object is passed
            Movies movie = (Movies)intent.getSerializableExtra(Intent.EXTRA_TEXT);
            //Image
            ImageView movie_icon = (ImageView)rootView.findViewById(R.id.backdrop_image);
            String imageURL = movie.getImagePath();
            Log.w(LOG_TAG, "image path: "+ imageURL);
            Picasso
                    .with(rootView.getContext())
                    .load(imageURL)
                    .into(movie_icon);
            //Movie Name
            TextView title = (TextView) rootView.findViewById(R.id.movie_title);
            title.setText(movie.getTitle());
            Log.w(LOG_TAG, "Title: " +movie.getTitle());
            //Movie Rating
            TextView rating = (TextView)rootView.findViewById(R.id.rating);
            rating.setText(movie.getRating());
            //User Votes
            TextView votes = (TextView)rootView.findViewById(R.id.votes);
            votes.setText(movie.getVoteCount());
            //Releasew date
            TextView release_date = (TextView)rootView.findViewById(R.id.release_date);
            release_date.setText(movie.getReleaseDate());
            //Plot Synopsis
            TextView overview = (TextView)rootView.findViewById(R.id.description);
            overview.setText(movie.getOverview());
            //Share Text
            mShareMovieDetails = "Get details of Popular Movies like "+movie.getTitle() + " ";
        }
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.shareaction, menu);

        // Retrieve the share menu item
        MenuItem menuItem = menu.findItem(R.id.action_share);

        // Get the provider and hold onto it to set/change the share intent.
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        // If onLoadFinished happens before this, we can go ahead and set the share intent now.
        if (mShareMovieDetails != null) {
            mShareActionProvider.setShareIntent(shareMovieInformation());
        }else{
            Log.d(LOG_TAG, "Share Action Provider is null");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_share){
            shareMovieInformation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent shareMovieInformation(){
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, mShareMovieDetails + MOVIE_SHARE_HASHTAG);
        return shareIntent;
    }
}
