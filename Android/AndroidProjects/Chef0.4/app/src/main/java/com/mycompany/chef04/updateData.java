package com.mycompany.chef04;

import android.content.ContentValues;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class updateData extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_update_chef, menu);
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

    public void updData(View view){
        EditText FoodName = (EditText) findViewById(R.id.food_name_hint);
        EditText FoodType = (EditText) findViewById(R.id.select_type_hint);
        EditText FoodChar1 = (EditText) findViewById(R.id.enter_food_char1);
        EditText FoodChar2 = (EditText) findViewById(R.id.enter_food_char2);
        EditText FoodImage = (EditText) findViewById(R.id.enter_food_image);
        EditText ChefName = (EditText) findViewById(R.id.enter_chef_name);
        EditText ChefPhNo = (EditText) findViewById(R.id.enter_chef_ph_no);
        EditText ChefEmail = (EditText) findViewById(R.id.enter_chef_email);
        EditText ChefDesc = (EditText) findViewById(R.id.enter_chef_desc);
        EditText ChefImage = (EditText) findViewById(R.id.enter_chef_image);

        String Food_Name = FoodName.getText().toString();
        String Food_Type = FoodType.getText().toString();
        String Food_Char1 = FoodChar1.getText().toString();
        String Food_Char2 = FoodChar2.getText().toString();
        String Food_Char3 = "Healthy";
        String Food_Char4 = "Nutritious";
        String Food_Image = FoodImage.getText().toString();
        String Chef_Name = ChefName.getText().toString();
        String Chef_Ph_No = ChefPhNo.getText().toString();
        String Chef_Email = ChefEmail.getText().toString();
        String Chef_Desc = ChefDesc.getText().toString();
        String Chef_Image = ChefImage.getText().toString();
        String Loc_Ser1 = "Lower Parel";
        String Loc_Ser2 = "Elphiston";
        String Loc_Ser3 = "Dadar";

        ContentValues values = new ContentValues();
        values.put(DataProvider.FOODName, Food_Name);
        values.put(DataProvider.VegNonveg, Food_Type);
        values.put(DataProvider.Char1, Food_Char1);
        values.put(DataProvider.Char2, Food_Char2);
        values.put(DataProvider.Char3, Food_Char3);
        values.put(DataProvider.Char4, Food_Char4);
        values.put(DataProvider.FoodImagelink, Food_Image);
        values.put(DataProvider.ChefName, Chef_Name);
        values.put(DataProvider.ChefPhoneNumber, Chef_Ph_No);
        values.put(DataProvider.ChefEmail, Chef_Email);
        values.put(DataProvider.ChefImageLink, Chef_Image);
        values.put(DataProvider.ChefDescription, Chef_Desc);
        values.put(DataProvider.LocationSer1, Loc_Ser1);
        values.put(DataProvider.LocationSer2, Loc_Ser2);
        values.put(DataProvider.LocationSer3, Loc_Ser3);

        getContentResolver().insert(DataProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Update Successful!!", Toast.LENGTH_LONG).show();

        Intent intent = new Intent(updateData.this, MainActivity.class);
        startActivity(intent);
    }

    public void delData(View view){
        int count = getContentResolver().delete(DataProvider.CONTENT_URI, null, null);
        String countNum = count + " records are deleted.";
        Toast.makeText(getBaseContext(), countNum, Toast.LENGTH_LONG).show();
    }
}
