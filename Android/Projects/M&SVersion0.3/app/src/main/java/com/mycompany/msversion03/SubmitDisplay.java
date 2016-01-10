package com.mycompany.msversion03;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;


public class SubmitDisplay extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_display);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent i = getIntent();
        Bundle b = i.getExtras();

        final String Prsn_Rspb = b.getString(UpdateSales.PR);
        final String Proj_Nme = b.getString(UpdateSales.PN);
        final String Unt_Sld = b.getString(UpdateSales.US);
        final String Value = b.getString(UpdateSales.VL);
        final String Cust_name = b.getString(UpdateSales.CN);
        final String Cust_Src = b.getString(UpdateSales.CS);
        final String Cust_Occ = b.getString(UpdateSales.CO);
        final String Other_Info = b.getString(UpdateSales.CI);

        TextView pr = new TextView(this);
        TextView pn = new TextView(this);
        TextView cn = new TextView(this);
        TextView us = new TextView(this);
        TextView v = new TextView(this);
        TextView cs = new TextView(this);
        TextView co = new TextView(this);
        TextView ci = new TextView(this);

        Button submit = new Button(this);


        pr.setId(R.id.Prsn_Rspb);
        pr.setText("Login by                  : " + Prsn_Rspb);
        pr.setTextSize(15);
        pn.setId(R.id.Proj_Nme);
        pn.setText("Project Name        : " + Proj_Nme);
        pn.setTextSize(15);
        cn.setId(R.id.Cust_name);
        cn.setText("Customer Name   : " + Cust_name);
        cn.setTextSize(15);
        cs.setId(R.id.Cust_Src);
        cs.setText("Customer Source : " + Cust_Src);
        cs.setTextSize(15);
        us.setId(R.id.Unt_Sld);
        us.setText("Units Sold               : " + Unt_Sld);
        us.setTextSize(15);
        v.setId(R.id.Value);
        v.setText("Value in Crs.           : " + Value);
        v.setTextSize(15);
        co.setId(R.id.Cust_Occ);
        co.setText("Cust Occupation   : " + Cust_Occ);
        co.setTextSize(15);
        ci.setId(R.id.Cust_Info);
        ci.setText("Other Information : " + Other_Info);
        ci.setTextSize(15);

        submit.setText("Submit");
        submit.setTextSize(15);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ProjectName = Proj_Nme;
                String BrandName;
                String Region;
                if (ProjectName.equals("Arabella") || ProjectName.equals("GurGateway") || ProjectName.equals("Myst") ||
                        ProjectName.equals("Primanti") || ProjectName.equals("HaileyRoad") || ProjectName.equals("Ariana") ||
                        ProjectName.equals("Avenida") || ProjectName.equals("Eden Court") || ProjectName.equals("Amantra") ||
                        ProjectName.equals("Aveza") || ProjectName.equals("Infinium") || ProjectName.equals("Prive") ||
                        ProjectName.equals("Aquila Heights") || ProjectName.equals("Promont") || ProjectName.equals("Cascades")) {
                    BrandName = "Tata Housing";
                } else {
                    BrandName = "Tata Value";
                }

                if (ProjectName.equals("Arabella") || ProjectName.equals("GurGateway") || ProjectName.equals("Myst") ||
                        ProjectName.equals("Primanti") || ProjectName.equals("HaileyRoad") || ProjectName.equals("Bahadurgarh")) {
                    Region = "North";
                } else if (ProjectName.equals("Peenya") || ProjectName.equals("RIVA") || ProjectName.equals("Ribbon Walk") ||
                        ProjectName.equals("Santorini") || ProjectName.equals("Aquila Heights") ||
                        ProjectName.equals("Promont") || ProjectName.equals("Cascades")) {
                    Region = "South";
                } else if (ProjectName.equals("BT Road") || ProjectName.equals("Ariana") ||
                        ProjectName.equals("Avenida") || ProjectName.equals("Eden Court")) {
                    Region = "East";
                } else {
                    Region = "West";
                }

                ContentValues values = new ContentValues();

                values.put(SalesDataProvider.BRAND, BrandName);
                values.put(SalesDataProvider.REG, Region);
                values.put(SalesDataProvider.PROJ, ProjectName);
                values.put(SalesDataProvider.SLPERSON, Prsn_Rspb);
                values.put(SalesDataProvider.CSTNME, Cust_name);
                values.put(SalesDataProvider.CSTSRC, Cust_Src);
                values.put(SalesDataProvider.UNTSLD, Unt_Sld);
                values.put(SalesDataProvider.VALUE, Value);
                values.put(SalesDataProvider.CSTOCC, Cust_Occ);
                values.put(SalesDataProvider.CSTINF, Other_Info);
                values.put(SalesDataProvider.DTE, System.currentTimeMillis());

                getContentResolver().insert(SalesDataProvider.CONTENT_URI, values);
                Toast.makeText(getBaseContext(), "Login Successful!!", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, LoginDialog.class);
                startActivity(intent);
                //Put logic to start a process to update SQL tables
            }
        });

        LinearLayout ll = (LinearLayout)findViewById(R.id.activity_submit_pg);
        ll.removeAllViewsInLayout();
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(pr,lp);
        ll.addView(cs,lp);
        ll.addView(pn,lp);
        ll.addView(cn,lp);
        ll.addView(us,lp);
        ll.addView(v,lp);
        ll.addView(co,lp);
        ll.addView(ci,lp);
        ll.addView(submit,lp);
        if (Prsn_Rspb.equals("xyz")){
            Toast.makeText(getBaseContext(),Prsn_Rspb,Toast.LENGTH_SHORT).show();
            Button delete = new Button(this);
            delete.setText("Delete");
            delete.setTextSize(15);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = getContentResolver().delete(SalesDataProvider.CONTENT_URI, null, null);
                    String countNum = count + " records are deleted.";
                    Toast.makeText(getBaseContext(), countNum, Toast.LENGTH_LONG).show();
                }
            });
            ll.addView(delete, lp);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_submit_display, menu);
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
