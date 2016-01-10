package com.mycompany.msversion03;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class Dashboard extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        String URL = "content://com.mycompany.provider.SaleData/MasterTable";
        Uri masterTable = Uri.parse(URL);
      //  String[] projection = b.getStringArray(SelectParameters.COL);//Columns
        //String selection = b.getString(SelectParameters.SEL); //Where clause
        //ArrayList<String> selectionArgsList = b.getStringArrayList(SelectParameters.SP); //Arguments of Where clause
      //  String[] selectionArgs = {"Arabella", "Gurgateway", "Primanti"};
        String sortOrder = "project"; //Orderby
        //pass these parameters to cursor

        Cursor c = getContentResolver().query(masterTable, null, null, null, sortOrder);
        String result = "Sales Data:";
//        String[]columnNms = c.getColumnNames();

//        for (int k=0;k<columnNms.length;k++){
//            TextView tv = new TextView(this);
//            tv.setText(columnNms[k+1]);
//        }
        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show();
        }else{
            do{
                result = result + "\n" + c.getString(c.getColumnIndex(SalesDataProvider.UNTSLD)) +
                        " units sold in Project " + c.getString(c.getColumnIndex(SalesDataProvider.PROJ)) +
                        " by " + c.getString(c.getColumnIndex(SalesDataProvider.SLPERSON));
            } while (c.moveToNext());
            //Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            TextView tv = new TextView(this);
            tv.setText(result);
            LinearLayout SlsName = (LinearLayout)findViewById(R.id.activity_dboard);
            //THNorth.removeAllViewsInLayout();
            LinearLayout.LayoutParams slsNamepara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            SlsName.addView(tv, slsNamepara);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
