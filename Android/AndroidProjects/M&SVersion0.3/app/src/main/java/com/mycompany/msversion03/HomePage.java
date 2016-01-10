package com.mycompany.msversion03;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class HomePage extends ActionBarActivity {
   // public final static String USER_DATA = "com.mycompany.msversion03.USERDATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Intent i = getIntent();
        String userName = i.getStringExtra("User_Name");
        TextView welcomeUser = new TextView(this);
        TextView userNm = new TextView(this);
        Button logSales = new Button(this);
        Button viewDashboard = new Button(this);

        welcomeUser.setId(R.id.UserId);
        welcomeUser.setText("Welcome " + userName + ",");
        welcomeUser.setTextSize(25);
        logSales.setText("Log Sales");
        viewDashboard.setText("View Dashboard");
        userNm.setId(R.id.UserHidden);
        userNm.setText(userName);
        userNm.setTextSize(0);

        LinearLayout HomePage = (LinearLayout)findViewById(R.id.act_home_page);
        HomePage.removeAllViewsInLayout();
        LinearLayout.LayoutParams HomePagePara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        HomePage.addView(welcomeUser, HomePagePara);
        HomePage.addView(logSales,HomePagePara);
        HomePage.addView(viewDashboard,HomePagePara);
        HomePage.addView(userNm,HomePagePara);

        logSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logSales(v);
            }
        });
        viewDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDashboard(v);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_page, menu);
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

    public void logSales(View view){
        TextView textView = (TextView) findViewById(R.id.UserHidden);
        String userName = textView.getText().toString();
        Intent intent = new Intent (this, ViewProjects.class);
        intent.putExtra("User_Name", userName);
        startActivity(intent);
    }

    public void viewDashboard (View view){
        Intent intent = new Intent (this, SelectParameters.class);
        startActivity(intent);
    }
}
