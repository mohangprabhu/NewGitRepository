package com.androidproject.popularmovies11;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MoviesHomeFragment extends Fragment {
    private final String LOG_TAG = MoviesHomeFragment.class.getSimpleName();

    private List<Movies> movieList = new ArrayList<Movies>();
    private MovieGridAdapter mAdapter;

    public MoviesHomeFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        //Clear previous data from the adapter
        mAdapter.clear();

        getMovieData();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    /*
     *  Description: This method is used to inflate the Home Page (Fragment) to display a grid view
     *  showing posters
     *  and titles of user preferred movies. It is  and used to send intent on click
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_movies_home, container, false);
        GridView mGridView =(GridView)rootView.findViewById(R.id.moviegrid);
        mAdapter = new MovieGridAdapter(getActivity(), movieList);
        mGridView.setAdapter(mAdapter);

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Intent i = new Intent(getActivity(), DetailActivity.class);
                i.putExtra(Intent.EXTRA_TEXT, movieList.get(position));
                startActivity(i);
            }
        });
        return rootView;
    }

    /*
     *  This method passes the preferences as parameters and calls the background thread to fetch
     *  the required movie data.
     */
    private void getMovieData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sort_by = sharedPreferences.getString(getString(R.string.key_sort_by), getString(R.string.default_sort_by));
       //Call FetchPopularMovies to fetch movie data using Async Task (background thread)
        FetchPopularMovies popularMovies = new FetchPopularMovies();
        popularMovies.execute(sort_by);
    }

    /*
     *  This class extends AsyncTask and does the background operation of connecting to the network,
     *  creating the required URL and passing the URL over the network to get the required data in
     *  the form of JSON String. It also parses the JSON string to get List of Movie Objects which
     *  are later used to display the movie data on the UI.
     */
    public class FetchPopularMovies extends AsyncTask<String,String,List<Movies>> {
        private final String LOG_TAG = FetchPopularMovies.class.getSimpleName();

        @Override
        protected List<Movies> doInBackground(String... params) {
            Log.v(LOG_TAG, "Background Operations Started");
            //NETWORKING CODE SNIPPET STARTS HERE
            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String popularMoviesJsonStr = null;

            final String SCHEME = "https";
            final String AUTHORITY = "api.themoviedb.org";
            final String DISCOVER = "discover";
            final String TYPE = "movie";
            final String SORT_PARAM = "sort_by";

            final String API_KEY = "api_key";

            String sort_by = "popularity.desc";

            if(params[0].equals("Least Popular"))
                sort_by = "popularity.asc";
            else if(params[0].equals("Highest Rated"))
                sort_by = "vote_average.desc";
            else if(params[0].equals("Lowest Rated"))
                sort_by = "vote_average.asc";
            else if(params[0].equals("Highest Grossing"))
                sort_by = "vote_count.desc";
            else if(params[0].equals("Lowest Grossing"))
                sort_by = "vote_count.asc";

            String apiKey = "YOUR_API_KEY";

            try {
                Uri.Builder builder = new Uri.Builder();
                builder
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("3")
                        .appendPath(DISCOVER)
                        .appendPath(TYPE)
                        .appendQueryParameter(API_KEY, apiKey)
                        .appendQueryParameter(SORT_PARAM, sort_by);

                String myURL = builder.build().toString();
                URL url = new URL(myURL);

                //url = https://api.themoviedb.org/3/discover/movie?api_key=104a1b66c2ebc22009216d0c8f0b8256&sort_by=popularity.desc

                // Create the request to TMDB, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                popularMoviesJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            //NETWORKING CODE SNIPPET ENDS HERE
            try {
                return getMovieDataFromJson(popularMoviesJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            Log.v(LOG_TAG, "Background Operations Ended Successfully!!");
            // This will only happen if there was an error getting or parsing
            return null;
        }

        private List<Movies> getMovieDataFromJson(String popularMoviesJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULT = "results";
            final String TMDB_TITLE = "original_title";
            final String TMDB_DATE = "release_date";
            final String TMDB_RATING = "vote_average";
            final String TMDB_VOTE_COUNT = "vote_count";
            final String TMDB_LANGUAGE = "original_language";
            final String TMDB_MOVIE_ID = "id";
            final String TMDB_IMAGE_PATH = "backdrop_path";
            final String TMDB_OVERVIEW = "overview";
            final String SCHEME = "http";
            final String AUTHORITY = "image.tmdb.org";
            final String IMAGE_SIZE = "w185";

            JSONObject MoviesJson = new JSONObject(popularMoviesJsonStr);
            JSONArray movieArray = MoviesJson.getJSONArray(TMDB_RESULT);

            Movies[] popularMovies = new Movies[movieArray.length()];

            for (int i = 0; i < popularMovies.length; i++) {
                Movies movie  = new Movies();
                JSONObject movieData = movieArray.getJSONObject(i);
                movie.setMovieID(movieData.getString(TMDB_MOVIE_ID));
                movie.setTitle(movieData.getString(TMDB_TITLE));
                movie.setLanguage(movieData.getString(TMDB_LANGUAGE));
                movie.setReleaseDate(movieData.getString(TMDB_DATE));
                movie.setRating(movieData.getString(TMDB_RATING));
                movie.setVoteCount(movieData.getString(TMDB_VOTE_COUNT));
                movie.setOverview(movieData.getString(TMDB_OVERVIEW));
                //Construct Image Path
                Uri.Builder imagePath = new Uri.Builder();
                imagePath
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath(IMAGE_SIZE)
                        .appendPath(movieData.getString(TMDB_IMAGE_PATH).substring(1));

                String imageURL = imagePath.build().toString();
                movie.setImagePath(imageURL);
                movieList.add(i, movie);
            }
            Log.w(LOG_TAG, "Added "+ movieList.size()+ " Movies to the MovieList.");
            return movieList;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<Movies> mov) {
            mAdapter.notifyDataSetChanged();
        }
    }
}
