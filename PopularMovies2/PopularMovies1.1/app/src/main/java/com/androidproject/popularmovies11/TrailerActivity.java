package com.androidproject.popularmovies11;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

public class TrailerActivity extends ActionBarActivity {
    private final String LOG_TAG = TrailerActivity.class.getSimpleName();

    private List<String> trailers = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer);
        showTrailer();
        updateView(trailers);
    }

    public void showTrailer() {
        Intent intent = getIntent();
        String movieID = intent.getStringExtra(Intent.EXTRA_TEXT);
        FetchTrailers movieReviews = new FetchTrailers();
        movieReviews.execute(movieID);
    }

    private void updateView(List<String> vidKey){
        LinearLayout ll = (LinearLayout) findViewById(R.id.trailerLayout);

        if (!vidKey.equals("No Trailers Available!!!")) {
            final String SCHEME = "http";
            final String AUTHORITY_IMG = "img.youtube.com";
            final String AUTHORITY_VID = "www.youtube.com";
            int i = vidKey.size();
            ImageView[] iv = new ImageView[i];

            ImageView trailer_img;
            for (int position = 0; position < i; position++) {
                final Uri.Builder trailerImagePath = new Uri.Builder();
                final Uri.Builder trailerPath = new Uri.Builder();
                trailerPath
                        .scheme(SCHEME)
                        .authority(AUTHORITY_VID)
                        .appendPath("watch")
                        .appendQueryParameter("v", vidKey.get(position));

                final String trailerURL = trailerPath.build().toString();

                trailer_img = new ImageView(this);
                trailerImagePath
                        .scheme(SCHEME)
                        .authority(AUTHORITY_IMG)
                        .appendPath("vi")
                        .appendPath(vidKey.get(position))
                        .appendPath("0.jpg");

                final String trailerImageURL = trailerImagePath.build().toString();

                Picasso
                        .with(this)
                        .load(trailerImageURL)
                        .into(trailer_img);
                trailer_img.setPadding(0, 0, 0, 16);

                ll.addView(trailer_img);
                iv[position] = trailer_img;

                iv[position].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailerURL)));
                    }
                });
            }
        } else{
            TextView tv = new TextView(this);
            tv.setText("No Trailers available for this Movie!!!");
            tv.setTextColor(getResources().getColor(R.color.background_color));
            ll.addView(tv);
        }
    }

    public class FetchTrailers extends AsyncTask<String,Void,List<String>> {
        private final String LOG_TAG = FetchTrailers.class.getSimpleName();

        @Override
        protected List<String> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String trailerJsonStr = null;
            String movie_id = params[0];
            String apiKey = "TMDB_API_KEY";

            try {
                final String SCHEME = "https";
                final String AUTHORITY = "api.themoviedb.org";
                final String TYPE = "movie";
                final String VIDEOS = "videos";
                final String API_KEY = "api_key";

                // url = https://api.themoviedb.org/3/movie/<id>/videos?api_key=API_KEY
                Uri.Builder builder = new Uri.Builder();
                builder
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("3")
                        .appendPath(TYPE)
                        .appendPath(movie_id)
                        .appendPath(VIDEOS)
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
                trailerJsonStr = buffer.toString();
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the movie data, there's no point in attempting
                // to parse it.
                return null;
            } finally{
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
            try {
                return getTrailersFromJson(trailerJsonStr, movie_id);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        private List<String> getTrailersFromJson(String reviewJsonStr, String id)
                throws JSONException {

            final String TMDB_RESULT = "results";
            final String TMDB_KEY = "key";

            JSONObject MoviesJson = new JSONObject(reviewJsonStr);
            JSONArray trailerArray = MoviesJson.getJSONArray(TMDB_RESULT);

            if (trailerArray.length() == 0){
                trailers.add("No Trailers Available!!!");
            }else{
                for (int i = 0; i < trailerArray.length(); i++) {
                    JSONObject trailerData = trailerArray.getJSONObject(i);
                    String trailerKey = trailerData.getString(TMDB_KEY);

                    trailers.add(trailerKey);
                }
            }
            return trailers;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<String> tr) {
            updateView(tr);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trailer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
