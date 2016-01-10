package com.mycompany.msversion03;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


public class LoginDialog extends ActionBarActivity {
    //public final static String USER_NAME = "com.mycompany.msversion03.USERNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_dialog);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login_dialog, menu);
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

    public void loginSuccessful(View view){
        Intent intent = new Intent(this, HomePage.class);
        EditText editText = (EditText) findViewById(R.id.txtUsername);
        String userName = editText.getText().toString();
        intent.putExtra("User_Name",userName);
        startActivity(intent);
    }

    public void exitApplication (View view){
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);

    }
}
