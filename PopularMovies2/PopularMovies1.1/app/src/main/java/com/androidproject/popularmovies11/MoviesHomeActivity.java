package com.androidproject.popularmovies11;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MoviesHomeActivity extends ActionBarActivity implements MoviesHomeFragment.Callback {
    private final String LOG_TAG = MoviesHomeActivity.class.getSimpleName();
    private final String DETAILFRAGMENT_TAG = "DFTAG";
    private String mPreference;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //mPreference = Utility.getSortPreference(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_home);
        if (findViewById(R.id.movie_detail_container) != null){
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.movie_detail_container,new DetailActivityFragment(),DETAILFRAGMENT_TAG)
                    .commit();
            } else {
                mTwoPane = false;
            }
        }
    }

    // Inflates options menu and adds items to the action bar if present
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this,SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String pref = Utility.getSortPreference(this);
        if(pref !=null && !pref.equals(mPreference)){
            MoviesHomeFragment mhFragment = (MoviesHomeFragment)getSupportFragmentManager()
                    .findFragmentById(R.id.fragment_movies_home);
            if (null!=mhFragment){
                mhFragment.onPreferenceChange(pref);
            }
            DetailActivityFragment daf = (DetailActivityFragment)getSupportFragmentManager().findFragmentByTag(DETAILFRAGMENT_TAG);
            if (null!=daf){
                daf.onPreferenceChange();
            }
            mPreference = pref;

        }
    }

    @Override
    public void onItemSelected(Uri contentUri) {
        if (mTwoPane){
            Bundle args = new Bundle();
            args.putParcelable(DetailActivityFragment.DETAIL_URI,contentUri);

            DetailActivityFragment daf = new DetailActivityFragment();
            daf.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container,daf,DETAILFRAGMENT_TAG)
                    .commit();
        } else{
            Intent i = new Intent(this, DetailActivity.class).setData(contentUri);
            startActivity(i);
        }
    }
}
