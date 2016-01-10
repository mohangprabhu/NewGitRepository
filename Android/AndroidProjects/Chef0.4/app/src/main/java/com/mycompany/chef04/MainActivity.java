package com.mycompany.chef04;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private ProgressDialog pDialog;
    private List<Food> foodList = new ArrayList<Food>();
    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, foodList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before fetching data
        pDialog.setMessage("Loading...");
        pDialog.show();
        String URL = DataProvider.URL;
        Uri masterTable = Uri.parse(URL);
        String sortOrder = "foodname";
        Cursor c = getContentResolver().query(masterTable, null, null, null, sortOrder);
        if (!c.moveToFirst()) {
            Toast.makeText(this, " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                Food food = new Food();
                food.setFoodthumbnailUrl(c.getString(c.getColumnIndex(DataProvider.FoodImagelink)));
                food.setFoodname(c.getString(c.getColumnIndex(DataProvider.FOODName)));
                food.setChefname(c.getString(c.getColumnIndex(DataProvider.ChefName)));
                food.setFoodtype(c.getString(c.getColumnIndex(DataProvider.VegNonveg)));
                foodList.add(food);
            } while (c.moveToNext());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, updateData.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
