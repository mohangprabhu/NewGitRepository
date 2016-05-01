package com.androidproject.popularmovies11;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MovieGridAdapter extends CursorAdapter {
    private final String LOG_TAG = MovieGridAdapter.class.getSimpleName();

    public MovieGridAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_view_layout, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView movie_icon = (ImageView)view.findViewById(R.id.movieImage);
        String posterURL = cursor.getString(MoviesHomeFragment.COL_MOVIE_POSTER_PATH);
        Picasso
                .with(view.getContext())
                .load(posterURL)
                .into(movie_icon);
    }
}
