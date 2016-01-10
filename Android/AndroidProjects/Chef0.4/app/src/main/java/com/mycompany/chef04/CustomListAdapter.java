package com.mycompany.chef04;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

public class CustomListAdapter extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Food> foodItems;
   // ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    public CustomListAdapter(Activity activity, List<Food> foodItems) {
        this.activity = activity;
        this.foodItems = foodItems;
    }
    @Override
    public int getCount() {
        return foodItems.size();
    }
    @Override
    public Object getItem(int position) {
        return foodItems.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
        NetworkImageView foodthumbNail = (NetworkImageView) convertView.findViewById(R.id.foodthumbnail);
       // NetworkImageView chefimage = (NetworkImageView) convertView.findViewById(R.id.chef_image);

        TextView foodtitle = (TextView) convertView.findViewById(R.id.foodtitle);
        TextView cheftitle = (TextView) convertView.findViewById(R.id.cheftitle);
        TextView fooddescription = (TextView) convertView.findViewById(R.id.fooddescription);
        TextView price = (TextView) convertView.findViewById(R.id.price);

        // getting movie data for the row
        Food f = foodItems.get(position);

        // thumbnail image
        //foodthumbNail.setImageUrl(f.getFoodthumbnailUrl(), imageLoader);

        // foodtitle
        foodtitle.setText(String.valueOf(f.getFoodname()));

        // cheftitle
        cheftitle.setText("Chef: " + String.valueOf(f.getChefname()));

        // fooddescription
//        String charStr = "";
//        for (String str : f.getFoodchar()) {
//            charStr += str + ", ";
//        }
//        charStr = charStr.length() > 0 ? charStr.substring(0,
//                charStr.length() - 2) : charStr;
//        fooddescription.setText(charStr);

        // chefphno
        price.setText(String.valueOf(f.getChefphNo()));

        return convertView;
    }
}
