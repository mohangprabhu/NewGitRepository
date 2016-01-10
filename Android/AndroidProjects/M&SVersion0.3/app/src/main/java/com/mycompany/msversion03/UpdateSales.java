package com.mycompany.msversion03;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class UpdateSales extends ActionBarActivity {
    public final static String CN = "com.mycompany.msversion03.CN";
    public final static String US = "com.mycompany.msversion03.US";
    public final static String VL = "com.mycompany.msversion03.VL";
    public final static String PR = "com.mycompany.msversion03.PR";
    public final static String PN = "com.mycompany.msversion03.PN";
    public final static String CS = "com.mycompany.msversion03.CS";
    public final static String CO = "com.mycompany.msversion03.CO";
    public final static String CI = "com.mycompany.msversion03.CI";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_sales);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String projName = bundle.getString("Project_Name");
        String userName = bundle.getString("User_Name");
        //String message = projName +"- ("+userName+")";
        TextView prjnme = (TextView) findViewById(R.id.proj_name);
        prjnme.setText(projName);
        TextView usrNm = (TextView) findViewById(R.id.UserHidden);
        usrNm.setText(userName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_update_sales, menu);
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

    public void updateSls(View view){
        Intent intent = new Intent(this, SubmitDisplay.class);
        TextView Resp_Person = (TextView) findViewById(R.id.UserHidden);
        TextView ProjNme = (TextView) findViewById(R.id.proj_name);
        EditText CustNme = (EditText) findViewById(R.id.enter_cust_name);
        EditText UtsSld = (EditText) findViewById(R.id.enter_units_sold);
        EditText UtsVal = (EditText) findViewById(R.id.enter_unit_value);
        EditText CustSrc = (EditText) findViewById(R.id.enter_cust_source);
        EditText CustOcc = (EditText) findViewById(R.id.enter_occupation);
        EditText Comments = (EditText) findViewById(R.id.enter_cust_information);

        String Project_Name = ProjNme.getText().toString();
        String Person_Responsible = Resp_Person.getText().toString();
        String Customer_Name = CustNme.getText().toString();
        String Units_Sold = UtsSld.getText().toString();
        String Unit_Value = UtsVal.getText().toString();
        String Customer_Source = CustSrc.getText().toString();
        String Customer_Occupation = CustOcc.getText().toString();
        String Customer_Information = Comments.getText().toString();

        if(Units_Sold.matches("\\d+(?:\\.\\d+)?") && Unit_Value.matches("\\d+(?:\\.\\d+)?"))
        {
            Bundle saleData = new Bundle();
            saleData.putString(PR, Person_Responsible);
            saleData.putString(PN, Project_Name);
            saleData.putString(US, Units_Sold);
            saleData.putString(VL, Unit_Value);
            saleData.putString(CN, Customer_Name);
            saleData.putString(CS, Customer_Source);
            saleData.putString(CO, Customer_Occupation);
            saleData.putString(CI, Customer_Information);

            intent.putExtras(saleData);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(getBaseContext(),"No. of Unit Sold and Unit Value should only contain Numbers", Toast.LENGTH_LONG).show();
        }
    }
}
