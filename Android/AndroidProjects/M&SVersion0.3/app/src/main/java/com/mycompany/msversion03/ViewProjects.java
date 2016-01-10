package com.mycompany.msversion03;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ViewProjects extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("User_Name");
        setContentView(R.layout.activity_view_projects);

        TextView tv = new TextView(this);
        tv.setText(userName);
        tv.setId(R.id.UserHidden);
        tv.setTextSize(0);
        LinearLayout SlsName = (LinearLayout)findViewById(R.id.activity_view_proj);
        //THNorth.removeAllViewsInLayout();
        LinearLayout.LayoutParams slsNamepara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        SlsName.addView(tv, slsNamepara);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_projects, menu);
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

    public void openProjects(View view){
        TextView usrName = (TextView) findViewById(R.id.UserHidden);
        final String userName = usrName.getText().toString();

        switch(view.getId()) {
            case R.id.button1:
                final Button Arabella = new Button(this);
                final Button GurGateway = new Button(this);
                final Button Myst = new Button(this);
                final Button Primanti = new Button(this);
                final Button HaileyRoad = new Button(this);

                Arabella.setId(R.id.Arabella);
                Arabella.setText("Arabella");
                GurGateway.setId(R.id.GurGateway);
                GurGateway.setText("GurGateway");
                Myst.setId(R.id.Myst);
                Myst.setText("Myst");
                Primanti.setId(R.id.Primanti);
                Primanti.setText("Primanti");
                HaileyRoad.setId(R.id.HaileyRoad);
                HaileyRoad.setText("HaileyRoad");

                LinearLayout THNorth = (LinearLayout)findViewById(R.id.activity_view_proj);
                THNorth.removeAllViewsInLayout();
                LinearLayout.LayoutParams THNpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                THNorth.addView(Arabella, THNpara);
                THNorth.addView(GurGateway, THNpara);
                THNorth.addView(Myst, THNpara);
                THNorth.addView(Primanti, THNpara);
                THNorth.addView(HaileyRoad, THNpara);

                Arabella.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Arabella.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                GurGateway.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",GurGateway.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Myst.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Myst.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Primanti.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Primanti.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                HaileyRoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",HaileyRoad.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button2:
                final Button Ariana = new Button(this);
                final Button Avenida = new Button(this);
                final Button EdenCourt = new Button(this);

                Ariana.setId(R.id.Ariana);
                Ariana.setText("Ariana");
                Avenida.setId(R.id.Avenida);
                Avenida.setText("Avenida");
                EdenCourt.setId(R.id.EdenCourt);
                EdenCourt.setText("Eden Court");

                LinearLayout THEast = (LinearLayout)findViewById(R.id.activity_view_proj);
                THEast.removeAllViewsInLayout();
                LinearLayout.LayoutParams THEpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                THEast.addView(Ariana, THEpara);
                THEast.addView(Avenida, THEpara);
                THEast.addView(EdenCourt, THEpara);

                Ariana.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Ariana.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Avenida.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Avenida.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                EdenCourt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",EdenCourt.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button3:
                final Button Amantra = new Button(this);
                final Button Aveza = new Button(this);
                final Button Infinium = new Button(this);
                final Button Prive = new Button(this);

                Amantra.setId(R.id.Amantra);
                Amantra.setText("Amantra");
                Aveza.setId(R.id.Aveza);
                Aveza.setText("Aveza");
                Infinium.setId(R.id.Infinium);
                Infinium.setText("Infinium");
                Prive.setId(R.id.Prive);
                Prive.setText("Prive");

                LinearLayout THWest = (LinearLayout)findViewById(R.id.activity_view_proj);
                THWest.removeAllViewsInLayout();
                LinearLayout.LayoutParams THWpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                THWest.addView(Amantra, THWpara);
                THWest.addView(Aveza, THWpara);
                THWest.addView(Infinium, THWpara);
                THWest.addView(Prive, THWpara);

                Amantra.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Amantra.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Aveza.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Aveza.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Infinium.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Infinium.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Prive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Prive.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button4:
                final Button AquilaHeights = new Button(this);
                final Button Promont = new Button(this);
                final Button Cascades = new Button(this);

                AquilaHeights.setId(R.id.AquilaHeights);
                AquilaHeights.setText("Aquila Heights");
                Promont.setId(R.id.Promont);
                Promont.setText("Promont");
                Cascades.setId(R.id.Cascades);
                Cascades.setText("Cascades");

                LinearLayout THSouth = (LinearLayout)findViewById(R.id.activity_view_proj);
                THSouth.removeAllViewsInLayout();
                LinearLayout.LayoutParams THSpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                THSouth.addView(AquilaHeights, THSpara);
                THSouth.addView(Cascades, THSpara);
                THSouth.addView(Promont, THSpara);

                AquilaHeights.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", AquilaHeights.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Promont.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Promont.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Cascades.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Cascades.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button5:
                final Button Bahadurgarh = new Button(this);

                Bahadurgarh.setId(R.id.Bahadurgarh);
                Bahadurgarh.setText("Bahadurgarh");

                LinearLayout TVHNorth = (LinearLayout)findViewById(R.id.activity_view_proj);
                TVHNorth.removeAllViewsInLayout();
                LinearLayout.LayoutParams TVHNpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TVHNorth.addView(Bahadurgarh, TVHNpara);

                Bahadurgarh.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Bahadurgarh.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button6:
                final Button BTRoad = new Button(this);

                BTRoad.setId(R.id.BTRoad);
                BTRoad.setText("BT Road");

                LinearLayout TVHEast = (LinearLayout)findViewById(R.id.activity_view_proj);
                TVHEast.removeAllViewsInLayout();
                LinearLayout.LayoutParams TVHEpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TVHEast.addView(BTRoad, TVHEpara);

                BTRoad.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", BTRoad.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button7:
                final Button AhmedabadNH = new Button(this);
                final Button AhmedabadNHC = new Button(this);
                final Button Boisar1 = new Button(this);
                final Button Boisar1Retail = new Button(this);
                final Button Boisar2NH = new Button(this);
                final Button Boisar2NHC = new Button(this);
                final Button InoraPark = new Button(this);
                final Button LaMontana = new Button(this);
                final Button Vasind = new Button(this);

                AhmedabadNH.setId(R.id.AhmedabadNH);
                AhmedabadNH.setText("Ahmedabad NH");
                AhmedabadNHC.setId(R.id.AhmedabadNHC);
                AhmedabadNHC.setText("Ahmedabad NHC");
                Boisar1.setId(R.id.Boisar1);
                Boisar1.setText("Boisar1");
                Boisar1Retail.setId(R.id.Boisar1Retail);
                Boisar1Retail.setText("Boisar1 Retail");
                Boisar2NH.setId(R.id.Boisar2NH);
                Boisar2NH.setText("Boisar2 NH");
                Boisar2NHC.setId(R.id.Boisar2NHC);
                Boisar2NHC.setText("Boisar2 NHC");
                InoraPark.setId(R.id.InoraPark);
                InoraPark.setText("Inora Park");
                LaMontana.setId(R.id.LaMontana);
                LaMontana.setText("La Montana");
                Vasind.setId(R.id.Vasind);
                Vasind.setText("Vasind");

                LinearLayout TVHWest = (LinearLayout)findViewById(R.id.activity_view_proj);
                TVHWest.removeAllViewsInLayout();
                LinearLayout.LayoutParams TVHWpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TVHWest.addView(AhmedabadNH, TVHWpara);
                TVHWest.addView(AhmedabadNHC, TVHWpara);
                TVHWest.addView(Boisar1, TVHWpara);
                TVHWest.addView(Boisar1Retail, TVHWpara);
                TVHWest.addView(Boisar2NH, TVHWpara);
                TVHWest.addView(Boisar2NHC, TVHWpara);
                TVHWest.addView(InoraPark, TVHWpara);
                TVHWest.addView(LaMontana, TVHWpara);
                TVHWest.addView(Vasind, TVHWpara);

                AhmedabadNH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", AhmedabadNH.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                AhmedabadNHC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", AhmedabadNHC.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Boisar1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Boisar1.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Boisar1Retail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Boisar1Retail.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Boisar2NH.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Boisar2NH.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Boisar2NHC.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Boisar2NHC.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                InoraPark.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", InoraPark.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                LaMontana.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", LaMontana.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Vasind.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Vasind.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
            case R.id.button8:
                final Button Peenya = new Button(this);
                final Button RIVA = new Button(this);
                final Button RibbonWalk = new Button(this);
                final Button Santorini = new Button(this);

                Peenya.setId(R.id.Peenya);
                Peenya.setText("Peenya");
                RIVA.setId(R.id.RIVA);
                RIVA.setText("RIVA");
                RibbonWalk.setId(R.id.RibbonWalk);
                RibbonWalk.setText("Ribbon Walk");
                Santorini.setId(R.id.Santorini);
                Santorini.setText("Santorini");

                LinearLayout TVHSouth = (LinearLayout)findViewById(R.id.activity_view_proj);
                TVHSouth.removeAllViewsInLayout();
                LinearLayout.LayoutParams TVHSpara = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                TVHSouth.addView(Peenya, TVHSpara);
                TVHSouth.addView(RIVA, TVHSpara);
                TVHSouth.addView(RibbonWalk, TVHSpara);
                TVHSouth.addView(Santorini, TVHSpara);

                Peenya.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name", Peenya.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                RIVA.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",RIVA.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                RibbonWalk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",RibbonWalk.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                Santorini.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent updateSales = new Intent(getBaseContext(), UpdateSales.class);
                        Bundle Bund_Arabella = new Bundle();
                        Bund_Arabella.putString("Project_Name",Santorini.getText().toString());
                        Bund_Arabella.putString("User_Name", userName);
                        updateSales.putExtras(Bund_Arabella);
                        startActivity(updateSales);
                    }
                });
                break;
        }
    }
}
