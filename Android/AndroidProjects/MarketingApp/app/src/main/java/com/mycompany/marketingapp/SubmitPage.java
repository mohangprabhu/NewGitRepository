package com.mycompany.marketingapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class SubmitPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_submit_page);
        Intent intent = getIntent();
        Bundle salesData = intent.getExtras();
        String Customer_Name = salesData.getString("CUST_NAME");
        String Units_Sold = salesData.getString("UNIT_SOLD");
        String Unit_Value = salesData.getString("VALUE");

        TextView textView = new TextView(this);
        textView.setTextSize(400);
        String message = Units_Sold +" units were so    ld to Client "+Customer_Name+ " for Rs. "+Unit_Value+" Crs. Thank you for logging!!";
        textView.setText(message);
        setContentView(textView);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
