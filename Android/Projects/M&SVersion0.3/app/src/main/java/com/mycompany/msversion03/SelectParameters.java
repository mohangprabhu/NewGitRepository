package com.mycompany.msversion03;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;

public class SelectParameters extends ActionBarActivity {

    public final static String SP = "com.mycompany.msversion03.SP";
    public final static String COL = "com.mycompany.msversion03.COL";
    public final static String FD = "com.mycompany.msversion03.FD";
    public final static String TD = "com.mycompany.msversion03.TD";
    public final static String SEL = "com.mycompany.msversion03.SEL";

    private TextView from_date;
    private TextView to_date;
    private Button frmDate;
    private Button tDate;
    private int year;
    private int month;
    private int day;
    static final int DATE_DIALOG_FROM = 101;
    static final int DATE_DIALOG_TO = 102;

    public ArrayList<String> selectedProjects = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_parameters);
        setCurrentDate();
        addButtonListener();
        setDefault();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_parameters, menu);
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
    public void setCurrentDate() {
        from_date = (TextView) findViewById(R.id.frm_dte);
        to_date = (TextView) findViewById(R.id.to_dte);
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        from_date.setText(new StringBuilder()
                .append(day).append("-")
                .append(month + 1).append("-")
                .append(year).append(" "));
        to_date.setText(new StringBuilder()
                .append(day).append("-")
                .append(month + 1).append("-")
                .append(year).append(" "));
    }
    public void addButtonListener() {
        frmDate = (Button) findViewById(R.id.selectFrom);
        frmDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_FROM);
            }
        });
        tDate = (Button) findViewById(R.id.selectTo);
        tDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_TO);
            }
        });
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_FROM: {
                return new DatePickerDialog(this, datePickerListenerFrom, year, month, day);
            }
            case DATE_DIALOG_TO: {
                return new DatePickerDialog(this, datePickerListenerTo, year, month, day);
            }
        }
        return null;
    }
    private DatePickerDialog.OnDateSetListener datePickerListenerFrom = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            from_date.setText(new StringBuilder().append(day).append("-").append(month + 1)
                    .append("-").append(year));
        }
    };
    private DatePickerDialog.OnDateSetListener datePickerListenerTo = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;
            to_date.setText(new StringBuilder().append(day).append("-").append(month + 1)
                    .append("-").append(year));
        }
    };
    public void setDefault(){
        RadioButton rdbutton = (RadioButton) findViewById(R.id.radioAll);
        rdbutton.setChecked(true);
        TableRow selProjRow = (TableRow) findViewById(R.id.selProjRow);
        TableRow thNorth1 =(TableRow) findViewById(R.id.thNorth1);
        TableRow thNorth2 =(TableRow) findViewById(R.id.thNorth2);
        TableRow tvhNorth =(TableRow) findViewById(R.id.tvhNorth);
        TableRow thEast =(TableRow) findViewById(R.id.thEast);
        TableRow tvhEast =(TableRow) findViewById(R.id.tvhEast);
        TableRow thWest =(TableRow) findViewById(R.id.thWest);
        TableRow tvhWest1 =(TableRow) findViewById(R.id.tvhWest1);
        TableRow tvhWest2 =(TableRow) findViewById(R.id.tvhWest2);
        TableRow thSouth =(TableRow) findViewById(R.id.thSouth);
        TableRow tvhSouth1 =(TableRow) findViewById(R.id.tvhSouth1);
        TableRow tvhSouth2 =(TableRow) findViewById(R.id.tvhSouth2);
        selProjRow.setVisibility(View.GONE);
        thNorth1.setVisibility(View.GONE);
        thNorth2.setVisibility(View.GONE);
        tvhNorth.setVisibility(View.GONE);
        thEast.setVisibility(View.GONE);
        tvhEast.setVisibility(View.GONE);
        thWest.setVisibility(View.GONE);
        tvhWest1.setVisibility(View.GONE);
        tvhWest2.setVisibility(View.GONE);
        thSouth.setVisibility(View.GONE);
        tvhSouth1.setVisibility(View.GONE);
        tvhSouth2.setVisibility(View.GONE);
        uncheckAllProjects();
    }
    public void showProjects(View v){
        //Toast.makeText(getApplicationContext(),"Called",Toast.LENGTH_LONG).show();
        uncheckAllProjects();
        TableRow selProjRow = (TableRow) findViewById(R.id.selProjRow);
        CheckBox chkAll = (CheckBox) findViewById(R.id.chkAll);

        TableRow thNorth1 =(TableRow) findViewById(R.id.thNorth1);
        TableRow thNorth2 =(TableRow) findViewById(R.id.thNorth2);
        TableRow tvhNorth =(TableRow) findViewById(R.id.tvhNorth);
        TableRow thEast =(TableRow) findViewById(R.id.thEast);
        TableRow tvhEast =(TableRow) findViewById(R.id.tvhEast);
        TableRow thWest =(TableRow) findViewById(R.id.thWest);
        TableRow tvhWest1 =(TableRow) findViewById(R.id.tvhWest1);
        TableRow tvhWest2 =(TableRow) findViewById(R.id.tvhWest2);
        TableRow thSouth =(TableRow) findViewById(R.id.thSouth);
        TableRow tvhSouth1 =(TableRow) findViewById(R.id.tvhSouth1);
        TableRow tvhSouth2 =(TableRow) findViewById(R.id.tvhSouth2);
        selProjRow.setVisibility(View.GONE);
        thNorth1.setVisibility(View.GONE);
        thNorth2.setVisibility(View.GONE);
        tvhNorth.setVisibility(View.GONE);
        thEast.setVisibility(View.GONE);
        tvhEast.setVisibility(View.GONE);
        thWest.setVisibility(View.GONE);
        tvhWest1.setVisibility(View.GONE);
        tvhWest2.setVisibility(View.GONE);
        thSouth.setVisibility(View.GONE);
        tvhSouth1.setVisibility(View.GONE);
        tvhSouth2.setVisibility(View.GONE);
        RadioButton rdbAll = (RadioButton) findViewById(R.id.radioAll);
        RadioButton rdbTH = (RadioButton) findViewById(R.id.radioTH);
        RadioButton rdbTVH = (RadioButton) findViewById(R.id.radioTV);
        CheckBox chkNorth = (CheckBox)findViewById(R.id.chkNorth);
        CheckBox chkEast = (CheckBox)findViewById(R.id.chkEast);
        CheckBox chkWest = (CheckBox)findViewById(R.id.chkWest);
        CheckBox chkSouth = (CheckBox)findViewById(R.id.chkSouth);
        Button dashboard = (Button)findViewById(R.id.butDashboard);
        dashboard.setEnabled(false);

        if (chkNorth.isChecked()){
            dashboard.setEnabled(true);
            selProjRow.setVisibility(View.VISIBLE);
            if (rdbAll.isChecked()){
                thNorth1.setVisibility(View.VISIBLE);
                thNorth2.setVisibility(View.VISIBLE);
                tvhNorth.setVisibility(View.VISIBLE);
            }else if (rdbTH.isChecked()){
                thNorth1.setVisibility(View.VISIBLE);
                thNorth2.setVisibility(View.VISIBLE);
            }else if (rdbTVH.isChecked()){
                tvhNorth.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(),"Error - Contact Mohan",Toast.LENGTH_LONG).show();
            }
        }
        if (chkEast.isChecked()){
            dashboard.setEnabled(true);
            selProjRow.setVisibility(View.VISIBLE);
            if (rdbAll.isChecked()){
                thEast.setVisibility(View.VISIBLE);
                tvhEast.setVisibility(View.VISIBLE);
            }else if (rdbTH.isChecked()){
                thEast.setVisibility(View.VISIBLE);
            }else if (rdbTVH.isChecked()){
                tvhEast.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(),"Error - Contact Mohan",Toast.LENGTH_LONG).show();
            }
        }
        if (chkWest.isChecked()){
            dashboard.setEnabled(true);
            selProjRow.setVisibility(View.VISIBLE);
            if (rdbAll.isChecked()){
                thWest.setVisibility(View.VISIBLE);
                tvhWest1.setVisibility(View.VISIBLE);
                tvhWest2.setVisibility(View.VISIBLE);
            }else if (rdbTH.isChecked()){
                thWest.setVisibility(View.VISIBLE);
            }else if (rdbTVH.isChecked()){
                tvhWest1.setVisibility(View.VISIBLE);
                tvhWest2.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(),"Error - Contact Mohan",Toast.LENGTH_LONG).show();
            }
        }
        if (chkSouth.isChecked()){
            dashboard.setEnabled(true);
            selProjRow.setVisibility(View.VISIBLE);
            if (rdbAll.isChecked()){
                thSouth.setVisibility(View.VISIBLE);
                tvhSouth1.setVisibility(View.VISIBLE);
                tvhSouth2.setVisibility(View.VISIBLE);
            }else if (rdbTH.isChecked()){
                thSouth.setVisibility(View.VISIBLE);
            }else if (rdbTVH.isChecked()){
                tvhSouth1.setVisibility(View.VISIBLE);
                tvhSouth2.setVisibility(View.VISIBLE);
            }else{
                Toast.makeText(getApplicationContext(),"Error - Contact Mohan",Toast.LENGTH_LONG).show();
            }
        }
        if (chkAll.isChecked()){
            CheckBox cbArabella = (CheckBox)findViewById(R.id.chkArabella);
            CheckBox cbMyst = (CheckBox)findViewById(R.id.chkMyst);
            CheckBox cbPrimanti = (CheckBox)findViewById(R.id.chkPrimanti);
            CheckBox cbGurGateway = (CheckBox)findViewById(R.id.chkGurGateway);
            CheckBox cbHaileyRoad = (CheckBox)findViewById(R.id.chkHaileyRoad);
            CheckBox cbBahadurgarh = (CheckBox)findViewById(R.id.chkBahadurgarh);
            CheckBox cbAriana = (CheckBox)findViewById(R.id.chkAriana);
            CheckBox cbAvenida = (CheckBox)findViewById(R.id.chkAvenida);
            CheckBox cbEdenCourt = (CheckBox)findViewById(R.id.chkEdenCourt);
            CheckBox cbBTRoad = (CheckBox)findViewById(R.id.chkBTRoad);
            CheckBox cbAmantra = (CheckBox)findViewById(R.id.chkAmantra);
            CheckBox cbAveza = (CheckBox)findViewById(R.id.chkAveza);
            CheckBox cbInfinium = (CheckBox)findViewById(R.id.chkInfinium);
            CheckBox cbPrive = (CheckBox)findViewById(R.id.chkPrive);
            CheckBox cbAhmedabad = (CheckBox)findViewById(R.id.chkAhmedabad);
            CheckBox cbBoisar1 = (CheckBox)findViewById(R.id.chkBoisar1);
            CheckBox cbBoisar2 = (CheckBox)findViewById(R.id.chkBoisar2);
            CheckBox cbInoraPark = (CheckBox)findViewById(R.id.chkInoraPark);
            CheckBox cbLaMontana = (CheckBox)findViewById(R.id.chkLaMontana);
            CheckBox cbVasind = (CheckBox)findViewById(R.id.chkVasind);
            CheckBox cbAquila = (CheckBox)findViewById(R.id.chkAquilaHeights);
            CheckBox cbCascades = (CheckBox)findViewById(R.id.chkCascades);
            CheckBox cbPromont = (CheckBox)findViewById(R.id.chkPromont);
            CheckBox cbPeenya = (CheckBox)findViewById(R.id.chkPeenya);
            CheckBox cbRIVA = (CheckBox)findViewById(R.id.chkRIVA);
            CheckBox cbSantorini = (CheckBox)findViewById(R.id.chkSantorini);
            CheckBox cbNHRW = (CheckBox)findViewById(R.id.chkNHRW);
            if (thNorth1.getVisibility()== View.VISIBLE){
                cbArabella.setChecked(true);
                cbMyst.setChecked(true);
                cbPrimanti.setChecked(true);
                cbGurGateway.setChecked(true);
                cbHaileyRoad.setChecked(true);
            }
            if (tvhNorth.getVisibility()== View.VISIBLE){
                cbBahadurgarh.setChecked(true);
            }
            if(thEast.getVisibility()== View.VISIBLE){
                cbAriana.setChecked(true);
                cbAvenida.setChecked(true);
                cbEdenCourt.setChecked(true);
            }
            if(tvhEast.getVisibility()== View.VISIBLE){
                cbBTRoad.setChecked(true);
            }
            if(thWest.getVisibility()== View.VISIBLE){
                cbAmantra.setChecked(true);
                cbAveza.setChecked(true);
                cbInfinium.setChecked(true);
                cbPrive.setChecked(true);
            }
            if(tvhWest1.getVisibility()== View.VISIBLE){
                cbAhmedabad.setChecked(true);
                cbBoisar1.setChecked(true);
                cbBoisar2.setChecked(true);
                cbInoraPark.setChecked(true);
                cbLaMontana.setChecked(true);
                cbVasind.setChecked(true);
            }
            if(thSouth.getVisibility()== View.VISIBLE){
                cbAquila.setChecked(true);
                cbCascades.setChecked(true);
                cbPromont.setChecked(true);
            }
            if(tvhSouth1.getVisibility()== View.VISIBLE){
                cbPeenya.setChecked(true);
                cbRIVA.setChecked(true);
                cbSantorini.setChecked(true);
                cbNHRW.setChecked(true);
            }
        }
    }
    public void uncheckAllProjects(){
        CheckBox cbArabella = (CheckBox)findViewById(R.id.chkArabella);
        CheckBox cbMyst = (CheckBox)findViewById(R.id.chkMyst);
        CheckBox cbPrimanti = (CheckBox)findViewById(R.id.chkPrimanti);
        CheckBox cbGurGateway = (CheckBox)findViewById(R.id.chkGurGateway);
        CheckBox cbHaileyRoad = (CheckBox)findViewById(R.id.chkHaileyRoad);
        CheckBox cbBahadurgarh = (CheckBox)findViewById(R.id.chkBahadurgarh);
        CheckBox cbAriana = (CheckBox)findViewById(R.id.chkAriana);
        CheckBox cbAvenida = (CheckBox)findViewById(R.id.chkAvenida);
        CheckBox cbEdenCourt = (CheckBox)findViewById(R.id.chkEdenCourt);
        CheckBox cbBTRoad = (CheckBox)findViewById(R.id.chkBTRoad);
        CheckBox cbAmantra = (CheckBox)findViewById(R.id.chkAmantra);
        CheckBox cbAveza = (CheckBox)findViewById(R.id.chkAveza);
        CheckBox cbInfinium = (CheckBox)findViewById(R.id.chkInfinium);
        CheckBox cbPrive = (CheckBox)findViewById(R.id.chkPrive);
        CheckBox cbAhmedabad = (CheckBox)findViewById(R.id.chkAhmedabad);
        CheckBox cbBoisar1 = (CheckBox)findViewById(R.id.chkBoisar1);
        CheckBox cbBoisar2 = (CheckBox)findViewById(R.id.chkBoisar2);
        CheckBox cbInoraPark = (CheckBox)findViewById(R.id.chkInoraPark);
        CheckBox cbLaMontana = (CheckBox)findViewById(R.id.chkLaMontana);
        CheckBox cbVasind = (CheckBox)findViewById(R.id.chkVasind);
        CheckBox cbAquila = (CheckBox)findViewById(R.id.chkAquilaHeights);
        CheckBox cbCascades = (CheckBox)findViewById(R.id.chkCascades);
        CheckBox cbPromont = (CheckBox)findViewById(R.id.chkPromont);
        CheckBox cbPeenya = (CheckBox)findViewById(R.id.chkPeenya);
        CheckBox cbRIVA = (CheckBox)findViewById(R.id.chkRIVA);
        CheckBox cbSantorini = (CheckBox)findViewById(R.id.chkSantorini);
        CheckBox cbNHRW = (CheckBox)findViewById(R.id.chkNHRW);
        cbArabella.setChecked(false);
        cbMyst.setChecked(false);
        cbPrimanti.setChecked(false);
        cbGurGateway.setChecked(false);
        cbHaileyRoad.setChecked(false);
        cbBahadurgarh.setChecked(false);
        cbAriana.setChecked(false);
        cbAvenida.setChecked(false);
        cbEdenCourt.setChecked(false);
        cbBTRoad.setChecked(false);
        cbAmantra.setChecked(false);
        cbAveza.setChecked(false);
        cbInfinium.setChecked(false);
        cbPrive.setChecked(false);
        cbAhmedabad.setChecked(false);
        cbBoisar1.setChecked(false);
        cbBoisar2.setChecked(false);
        cbInoraPark.setChecked(false);
        cbLaMontana.setChecked(false);
        cbVasind.setChecked(false);
        cbAquila.setChecked(false);
        cbCascades.setChecked(false);
        cbPromont.setChecked(false);
        cbPeenya.setChecked(false);
        cbRIVA.setChecked(false);
        cbSantorini.setChecked(false);
        cbNHRW.setChecked(false);
    }
    public void showDashboard (View view) {
        ArrayList<String> selectedProjects = new ArrayList<String>();
        CheckBox cbArabella = (CheckBox)findViewById(R.id.chkArabella);
        CheckBox cbMyst = (CheckBox)findViewById(R.id.chkMyst);
        CheckBox cbPrimanti = (CheckBox)findViewById(R.id.chkPrimanti);
        CheckBox cbGurGateway = (CheckBox)findViewById(R.id.chkGurGateway);
        CheckBox cbHaileyRoad = (CheckBox)findViewById(R.id.chkHaileyRoad);
        CheckBox cbBahadurgarh = (CheckBox)findViewById(R.id.chkBahadurgarh);
        CheckBox cbAriana = (CheckBox)findViewById(R.id.chkAriana);
        CheckBox cbAvenida = (CheckBox)findViewById(R.id.chkAvenida);
        CheckBox cbEdenCourt = (CheckBox)findViewById(R.id.chkEdenCourt);
        CheckBox cbBTRoad = (CheckBox)findViewById(R.id.chkBTRoad);
        CheckBox cbAmantra = (CheckBox)findViewById(R.id.chkAmantra);
        CheckBox cbAveza = (CheckBox)findViewById(R.id.chkAveza);
        CheckBox cbInfinium = (CheckBox)findViewById(R.id.chkInfinium);
        CheckBox cbPrive = (CheckBox)findViewById(R.id.chkPrive);
        CheckBox cbAhmedabad = (CheckBox)findViewById(R.id.chkAhmedabad);
        CheckBox cbBoisar1 = (CheckBox)findViewById(R.id.chkBoisar1);
        CheckBox cbBoisar2 = (CheckBox)findViewById(R.id.chkBoisar2);
        CheckBox cbInoraPark = (CheckBox)findViewById(R.id.chkInoraPark);
        CheckBox cbLaMontana = (CheckBox)findViewById(R.id.chkLaMontana);
        CheckBox cbVasind = (CheckBox)findViewById(R.id.chkVasind);
        CheckBox cbAquila = (CheckBox)findViewById(R.id.chkAquilaHeights);
        CheckBox cbCascades = (CheckBox)findViewById(R.id.chkCascades);
        CheckBox cbPromont = (CheckBox)findViewById(R.id.chkPromont);
        CheckBox cbPeenya = (CheckBox)findViewById(R.id.chkPeenya);
        CheckBox cbRIVA = (CheckBox)findViewById(R.id.chkRIVA);
        CheckBox cbSantorini = (CheckBox)findViewById(R.id.chkSantorini);
        CheckBox cbNHRW = (CheckBox)findViewById(R.id.chkNHRW);
        //selectedProjects.removeAll(selectedProjects);
        if (cbArabella.isChecked()) {
            selectedProjects.add(cbArabella.getText().toString());
        }
        if (cbMyst.isChecked()){
            selectedProjects.add(cbMyst.getText().toString());
        }
        if (cbPrimanti.isChecked()){
            selectedProjects.add(cbPrimanti.getText().toString());
        }
        if (cbGurGateway.isChecked()){
            selectedProjects.add(cbGurGateway.getText().toString());
        }
        if (cbHaileyRoad.isChecked()){
            selectedProjects.add(cbHaileyRoad.getText().toString());
        }
        if (cbBahadurgarh.isChecked()){
            selectedProjects.add(cbBahadurgarh.getText().toString());
        }
        if (cbAriana.isChecked()){
            selectedProjects.add(cbAriana.getText().toString());
        }
        if (cbAvenida.isChecked()){
            selectedProjects.add(cbAvenida.getText().toString());
        }
        if (cbEdenCourt.isChecked()){
            selectedProjects.add(cbEdenCourt.getText().toString());
        }
        if (cbBTRoad.isChecked()){
            selectedProjects.add(cbBTRoad.getText().toString());
        }
        if (cbAmantra.isChecked()){
            selectedProjects.add(cbAmantra.getText().toString());
        }
        if (cbAveza.isChecked()){
            selectedProjects.add(cbAveza.getText().toString());
        }
        if (cbInfinium.isChecked()){
            selectedProjects.add(cbInfinium.getText().toString());
        }
        if (cbPrive.isChecked()){
            selectedProjects.add(cbPrive.getText().toString());
        }
        if (cbAhmedabad.isChecked()){
            selectedProjects.add(cbAhmedabad.getText().toString());
        }
        if (cbBoisar1.isChecked()){
            selectedProjects.add(cbBoisar1.getText().toString());
        }
        if (cbBoisar2.isChecked()){
            selectedProjects.add(cbBoisar2.getText().toString());
        }
        if (cbInoraPark.isChecked()){
            selectedProjects.add(cbInoraPark.getText().toString());
        }
        if (cbLaMontana.isChecked()){
            selectedProjects.add(cbLaMontana.getText().toString());
        }
        if (cbVasind.isChecked()){
            selectedProjects.add(cbVasind.getText().toString());
        }
        if (cbAquila.isChecked()){
            selectedProjects.add(cbAquila.getText().toString());
        }
        if (cbCascades.isChecked()){
            selectedProjects.add(cbCascades.getText().toString());
        }
        if (cbPromont.isChecked()){
            selectedProjects.add(cbPromont.getText().toString());
        }
        if (cbPeenya.isChecked()){
            selectedProjects.add(cbPeenya.getText().toString());
        }
        if (cbRIVA.isChecked()){
            selectedProjects.add(cbRIVA.getText().toString());
        }
        if (cbSantorini.isChecked()){
            selectedProjects.add(cbSantorini.getText().toString());
        }
        if (cbNHRW.isChecked()){
            selectedProjects.add(cbNHRW.getText().toString());
        }
//        int size = selectedProjects.size();
//        Toast.makeText(getApplicationContext(),size,Toast.LENGTH_SHORT).show();
//
//        String[] columns;
//        String selection = "project In ?";
//        if (size>4){
//            columns = new String[]{"brand", "project", "salesperson", "unitssold", "value"};
//        }else{
//            columns = new String[]{"project", "salesperson", "unitssold", "value", "custsrc", "custocc"};
//        }
//        Bundle parameters = new Bundle();
//        parameters.putStringArrayList(SP,selectedProjects);
//        parameters.putStringArray(COL, columns);
//        parameters.putString(FD, from_date.getText().toString());
//        parameters.putString(TD, to_date.getText().toString());
//        parameters.putString(SEL,selection);

        Intent intent = new Intent(this, Dashboard.class);
//        intent.putExtras(parameters);
        startActivity(intent);
    }
}
