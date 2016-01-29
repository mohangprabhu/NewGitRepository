package com.androidproject.myappportfolio;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button app1 = (Button) findViewById(R.id.app1_launch);
        app1.setOnClickListener(this);

        Button app2 = (Button) findViewById(R.id.app2_launch);
        app2.setOnClickListener(this);

        Button app3 = (Button) findViewById(R.id.app3_launch);
        app3.setOnClickListener(this);

        Button app4 = (Button) findViewById(R.id.app4_launch);
        app4.setOnClickListener(this);

        Button app5 = (Button) findViewById(R.id.app5_launch);
        app5.setOnClickListener(this);

        Button capstone = (Button) findViewById(R.id.capstone_launch);
        capstone.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.app1_launch:
                Toast.makeText(getBaseContext(),"This Button will launch Spotify Streamer!",Toast.LENGTH_LONG).show();
                break;
            case R.id.app2_launch:
                Toast.makeText(getBaseContext(),"This Button will launch Scores App!",Toast.LENGTH_LONG).show();
                break;
            case R.id.app3_launch:
                Toast.makeText(getBaseContext(),"This Button will launch Library App!",Toast.LENGTH_LONG).show();
                break;
            case R.id.app4_launch:
                Toast.makeText(getBaseContext(),"This Button will launch Build it Bigger!",Toast.LENGTH_LONG).show();
                break;
            case R.id.app5_launch:
                Toast.makeText(getBaseContext(),"This Button will launch XYZ Reader!",Toast.LENGTH_LONG).show();
                break;
            case R.id.capstone_launch:
                Toast.makeText(getBaseContext(),"This Button will launch My Capstone App!",Toast.LENGTH_LONG).show();
                break;
        }
    }
}
