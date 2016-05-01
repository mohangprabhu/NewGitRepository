package com.androidproject.popularmovies11;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.androidproject.popularmovies11.data.MovieContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Vector;

/**
 *  This class extends AsyncTask and does the background operation of connecting to the network,
 *  creating the required URL and passing the URL over the network to get the required data in
 *  the form of JSON String. It also parses the JSON string to get List of Movie Objects which
 *  are later used to display the movie data on the UI.
 */
public class FetchPopularMovies extends AsyncTask<String,Void,Void> {
    private final String LOG_TAG = FetchPopularMovies.class.getSimpleName();

    private Context mContext;

    public FetchPopularMovies (Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(String... params) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string.
        String popularMoviesJsonStr = null;
        String movie_type = params[0];
        String apiKey = "TMDB_API_KEY";

        try{
            final String SCHEME = "https";
            final String AUTHORITY = "api.themoviedb.org";
            final String TYPE = "movie";
            final String API_KEY = "api_key";

            // url = https://api.themoviedb.org/3/movie/preference?api_key=API_KEY
            Uri.Builder builder = new Uri.Builder();
            builder
                    .scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath("3")
                    .appendPath(TYPE)
                    .appendPath(movie_type)
                    .appendQueryParameter(API_KEY, apiKey);

            String myURL = builder.build().toString();
            URL url = new URL(myURL);

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
            getMovieDataFromJson(popularMoviesJsonStr, movie_type);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error IOException", e);
            return null;
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
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
        return null;
    }

    private void getMovieDataFromJson(String popularMoviesJsonStr, String category)
            throws JSONException {

        // These are the names of the JSON objects that need to be extracted.
        final String TMDB_RESULT = "results";
        final String TMDB_TITLE = "original_title";
        final String TMDB_DATE = "release_date";
        final String TMDB_RATING = "vote_average";
        final String TMDB_VOTE_COUNT = "vote_count";
        final String TMDB_MOVIE_ID = "id";
        final String TMDB_POSTER_PATH = "poster_path";
        final String TMDB_BACKDROP_PATH = "backdrop_path";
        final String TMDB_OVERVIEW = "overview";

        final String SCHEME = "http";
        final String AUTHORITY = "image.tmdb.org";
        final String IMAGE_SIZE = "w185";

        try{
            JSONObject MoviesJson = new JSONObject(popularMoviesJsonStr);
            JSONArray movieArray = MoviesJson.getJSONArray(TMDB_RESULT);

            Vector<ContentValues> cVVector = new Vector<ContentValues>(movieArray.length());

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movieData = movieArray.getJSONObject(i);
                String movieId = movieData.getString(TMDB_MOVIE_ID);
                String movieTitle = movieData.getString(TMDB_TITLE);
                String movieReleaseDate = movieData.getString(TMDB_DATE);
                String movieRating = movieData.getString(TMDB_RATING);
                String movieVoteCount = movieData.getString(TMDB_VOTE_COUNT);
                String movieOverview = movieData.getString(TMDB_OVERVIEW);

                //Construct Poster Path
                Uri.Builder posterPath = new Uri.Builder();
                posterPath
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath(IMAGE_SIZE)
                        .appendPath(movieData.getString(TMDB_POSTER_PATH).substring(1));
                String posterURL = posterPath.build().toString();

                //Construct Backdrop Path
                Uri.Builder backdropPath = new Uri.Builder();
                backdropPath
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("t")
                        .appendPath("p")
                        .appendPath(IMAGE_SIZE)
                        .appendPath(movieData.getString(TMDB_BACKDROP_PATH).substring(1));
                String backdropURL = backdropPath.build().toString();


                ContentValues movieValues = new ContentValues();

                movieValues.put(MovieContract.COLUMN_MOVIE_ID,movieId);
                movieValues.put(MovieContract.COLUMN_MOVIE_TITLE,movieTitle);
                movieValues.put(MovieContract.COLUMN_RELEASE_DATE,movieReleaseDate);
                movieValues.put(MovieContract.COLUMN_MOVIE_RATING,movieRating);
                movieValues.put(MovieContract.COLUMN_MOVIE_VOTE_COUNT,movieVoteCount);
                movieValues.put(MovieContract.COLUMN_MOVIE_POSTER_PATH,posterURL);
                movieValues.put(MovieContract.COLUMN_MOVIE_BACKDROP_PATH,backdropURL);
                movieValues.put(MovieContract.COLUMN_MOVIE_OVERVIEW,movieOverview);
                movieValues.put(MovieContract.COLUMN_MOVIE_CATEGORY,category);

                cVVector.add(movieValues);
            }

            int inserted = 0;
            if ( cVVector.size() > 0 ) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                Uri content_uri;
                if (category.equals("popular")) {
                    content_uri = MovieContract.PopularEntry.CONTENT_URI;
                }else{
                    content_uri = MovieContract.TopEntry.CONTENT_URI;
                }
                // inserts all values in content provider
                inserted = mContext.getContentResolver().bulkInsert(content_uri, cvArray);
            }
        }
        catch (JSONException e){
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
