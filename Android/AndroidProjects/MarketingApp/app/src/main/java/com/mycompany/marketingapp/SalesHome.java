package com.mycompany.marketingapp;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SalesHome extends Activity implements View.OnClickListener{

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> lstDataHeader;
    HashMap<String, List<String>> lstDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Login Dialogue
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);
        // Set GUI of login screen
        login.setContentView(R.layout.login_dialog);
        login.setTitle("Tata Housing Login");
        login.show();

        // Init button of login GUI
        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        Button btnExit = (Button) login.findViewById(R.id.btnExit);
        final EditText txtUsername = (EditText)login.findViewById(R.id.txtUsername);
        final EditText txtPassword = (EditText)login.findViewById(R.id.txtPassword);

        /* Attached listener for login GUI button*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        if (txtUsername.getText().toString().trim().length() > 0 && txtPassword.getText().toString().trim().length() > 0) {
                            // Validate Your login credential here than display message
                            /*
                            {                                                           }
                            {                                                           }
                            {                                                           }
                            {                                                           }
                            {                                                           }
                             */

                            Toast.makeText(SalesHome.this, "Login Sucessfull", Toast.LENGTH_LONG).show();
                            // Redirect to dashboard / home screen.
                            login.dismiss();
                            setContentView(R.layout.activity_sales_home);
                            }
                        else {
                            Toast.makeText(SalesHome.this, "Please enter Username and Password", Toast.LENGTH_LONG).show();
                            login.show();
                        }
                }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
            }
        });

    expListView = (ExpandableListView) findViewById(R.id.exp_list_view);

        // preparing list data
        prepareListData();

        //lstAdapter is object of an adapter that links a ExpandableListView with the underlying data.
        // The implementation of this interface will provide access to the data
        // of the children (categorized by groups),
        // and also instantiate Views for children and groups.

        //linking the data with the view
        listAdapter = new Exp_List_Adapter(this, lstDataHeader, lstDataChild);

        // setting the data behind the list view
        expListView.setAdapter(listAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sales_home, menu);
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
    /*Preparing the list data*/
    private void prepareListData() {
        lstDataHeader = new ArrayList<String>();
        lstDataChild = new HashMap<String, List<String>>();

        // Adding child data
        lstDataHeader.add("North");
        lstDataHeader.add("East");
        lstDataHeader.add("West");
        lstDataHeader.add("South");

        // Adding child data
        List<String> North = new ArrayList<String>();
        North.add("Arabella");
        North.add("Gurgaon Gateway");
        North.add("Hailey Road");
        North.add("Primanti");
        North.add("MYST");
        North.add("Raisina");

        List<String> East = new ArrayList<String>();
        East.add("Ariana");
        East.add("Avenida");

        List<String> West = new ArrayList<String>();
        West.add("Aveza");
        West.add("Amantra");
        West.add("Infinium");
        West.add("Prive");
        West.add("Thane");
        West.add("Goa");

        List<String> South = new ArrayList<String>();
        South.add("Promont");
        South.add("Aquila Heights");

        lstDataChild.put(lstDataHeader.get(0), North); // Header, Child data
        lstDataChild.put(lstDataHeader.get(1), East);
        lstDataChild.put(lstDataHeader.get(2), West);
        lstDataChild.put(lstDataHeader.get(3), South);
    }

    @Override
    public void onClick(View v) {

    }
}
