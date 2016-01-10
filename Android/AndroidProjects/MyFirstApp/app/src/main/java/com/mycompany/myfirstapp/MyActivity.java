package com.mycompany.myfirstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class MyActivity extends ActionBarActivity {
    public final static String EXTRA_MESAGE = "com.mycompany.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_menu, menu);

        //What does this statement do?
        return super.onCreateOptionsMenu(menu);

        //The below statement was replaced by the above statement
        //return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
          //  return true;

            // Handle presses on the action bar items id tells which item was pressed
            switch (id) {
                case R.id.action_search:
                    openSearch();
                    return true;
                case R.id.action_settings:
                    openSettings();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
                }
        //}

       // return super.onOptionsItemSelected(item);
    }
    /** called when the user clicks on the send button**/
    public void sendMessage(View view){

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESAGE,message);
        startActivity(intent);
    }

    public void openSearch(){
        //Write logic of what happens after open Search
    }

    public void openSettings(){
        //Write logic of what happens after open Settings
    }
}
