package com.androidproject.popularmovies11;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieGridAdapter extends BaseAdapter {
    private final String LOG_TAG = MovieGridAdapter.class.getSimpleName();

    private Context mContext;
    private List<Movies> movieItems;

    public MovieGridAdapter(Context context, List<Movies> movieItems) {
        this.mContext = context;
        this.movieItems = movieItems;
    }

    @Override
    public int getCount() {
        return movieItems.size();
    }

    @Override
    public Object getItem(int position) {
        return movieItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_view_layout, null);
        TextView title = (TextView) view.findViewById(R.id.movieName);
        ImageView movie_icon = (ImageView)view.findViewById(R.id.movieImage);
        String imageURL = movieItems.get(position).getImagePath();
        Log.w(LOG_TAG, "Image "+ position + ": " + imageURL);
        title.setText(movieItems.get(position).getTitle());
        Picasso
                .with(view.getContext())
                .load(imageURL)
                .into(movie_icon);
        return view;
    }

    public void clear(){
        movieItems.clear();
    }
}
