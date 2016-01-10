package com.mycompany.marketingapp;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class UpdateSales extends ActionBarActivity {
    public final static String SALE_DATA = "com.mycompany.marketingapp.SALEDATA";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sales);
        Intent intent = getIntent();
        String Project_Name = "Arabella";
        TextView textView = (TextView) findViewById(R.id.proj_name);
        textView.setText(Project_Name);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_sales, menu);
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

    public void updateSales(View view){

        //Before submitting the data get a login activity in between so that
        //only authentic people can update data
        //Intent intentLogin = new Intent (this, Login.class);
        //startActivityForResult(intentLogin,int reqCode);
        //if (reqCode==1){
        //}else
        //  Toast.(Error message saying not updated)
         //   return;


        //Update Database (Put onclickAddName data over here)
        //ContentValues values = new ContentValues();
        //values.put(SalesData.Brand,(EditText)findViewById(R.id.brand_name).getText().toString());




        Intent intent = new Intent(this, SubmitPage.class);
        //Figureout how to get person responsible later
        //String Person_Responsible = Resp_Person.getText().toString();

        TextView ProjNme = (TextView) findViewById(R.id.proj_name);
        EditText CustNme = (EditText) findViewById(R.id.enter_cust_name);
        EditText UtsSld = (EditText) findViewById(R.id.enter_units_sold);
        EditText UtsVal = (EditText) findViewById(R.id.enter_unit_value);

        String Project_Name = ProjNme.getText().toString();
        String Customer_Name = CustNme.getText().toString();
        String Units_Sold = UtsSld.getText().toString();
        String Unit_Value = UtsVal.getText().toString();

        Bundle saleData = new Bundle();
        saleData.putString("PROJECT_NAME",Project_Name);
        saleData.putString("UNIT_SOLD", Units_Sold);
        saleData.putString("VALUE", Unit_Value);
        saleData.putString("CUST_NAME", Customer_Name);
        //saleData.putString("PERSON_RESPONSIBLE", );
        intent.putExtra(SALE_DATA,saleData);
        startActivity(intent);
    }
}

