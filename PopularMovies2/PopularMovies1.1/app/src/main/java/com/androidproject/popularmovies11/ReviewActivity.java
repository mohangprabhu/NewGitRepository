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
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class ReviewActivity extends ActionBarActivity {

    private List<Reviews> revList = new ArrayList<Reviews>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        showReviews();
        updateView();
    }

    private void updateView(){
        LinearLayout ll = (LinearLayout) findViewById(R.id.reviewLayout);
        //ll.setBackgroundColor(getResources().getColor(R.color.background_color));
        int i = revList.size();
        int j = 2*i;
        int k = 0;
        TextView[] tv = new TextView[j];
        TextView tempContent;
        TextView tempAuthor;
        for (int position=0; position<i; position++){
            tempAuthor = new TextView(this);
            tempAuthor.setText("Author: " + revList.get(position).getAuthor());
            tempAuthor.setTextSize(22);
            tempAuthor.setTextColor(getResources().getColor(R.color.background_color));
            ll.addView(tempAuthor);
            tv[k] = tempAuthor;

            tempContent = new TextView(this);
            tempContent.setPadding(0,0,0,32);
            tempContent.setText(revList.get(position).getContent());
            tempContent.setTextColor(getResources().getColor(R.color.background_color));

            ll.addView(tempContent);
            tv[++k] = tempContent;
        }
    }

    // This method will take the user to the Reviews page
    public void showReviews() {
        Intent intent = getIntent();
        String movieID = intent.getStringExtra(Intent.EXTRA_TEXT);
        FetchReviews movieReviews = new FetchReviews();
        movieReviews.execute(movieID);
    }

    public class FetchReviews extends AsyncTask<String, String, List<Reviews>> {
        private final String LOG_TAG = FetchReviews.class.getSimpleName();

        @Override
        protected List<Reviews> doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String reviewJsonStr = null;
            String movie_id = params[0];
            String apiKey = "TMDB_API_KEY";

            try {
                final String SCHEME = "https";
                final String AUTHORITY = "api.themoviedb.org";
                final String TYPE = "movie";
                final String REVIEWS = "reviews";
                final String API_KEY = "api_key";

                // url = https://api.themoviedb.org/3/movie/<id>/reviews?api_key=API_KEY
                Uri.Builder builder = new Uri.Builder();
                builder
                        .scheme(SCHEME)
                        .authority(AUTHORITY)
                        .appendPath("3")
                        .appendPath(TYPE)
                        .appendPath(movie_id)
                        .appendPath(REVIEWS)
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
                reviewJsonStr = buffer.toString();
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
                return getReviewsFromJson(reviewJsonStr, movie_id);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }
        private List<Reviews> getReviewsFromJson(String reviewJsonStr, String id)
                throws JSONException {

            final String TMDB_RESULT = "results";
            final String TMDB_AUTHOR = "author";
            final String TMDB_CONTENT = "content";

            JSONObject MoviesJson = new JSONObject(reviewJsonStr);
            JSONArray reviewArray = MoviesJson.getJSONArray(TMDB_RESULT);

            if (reviewArray.length() == 0){
                Reviews review = new Reviews();
                review.setAuthor("No Reviewer!!");
                review.setContent("No Review Available for this Movie!!");
                revList.add(review);
            }else{
                for (int i = 0; i < reviewArray.length(); i++) {
                    Reviews review = new Reviews();
                    JSONObject reviewData = reviewArray.getJSONObject(i);
                    String revAuthor = reviewData.getString(TMDB_AUTHOR);
                    String revContent = reviewData.getString(TMDB_CONTENT);
                    review.setAuthor(revAuthor);
                    review.setContent(revContent);
                    revList.add(review);
                }
            }
            return revList;
        }

        @TargetApi(Build.VERSION_CODES.HONEYCOMB)
        @Override
        protected void onPostExecute(List<Reviews> rev) {
            updateView();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_review, menu);
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
