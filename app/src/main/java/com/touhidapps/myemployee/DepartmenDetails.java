package com.touhidapps.myemployee;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class DepartmenDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    LinearLayout linearLayout_home,
            linearLayout_new,
            linearLayout_edit;


    Spinner spinner_department,
            spinner_deptEnableDisableNew,
            spinner_deptEnableDisableEdit;

    Button button_deptNew,
            button_deptEdit,
            button_deptSave,
            button_deptCancelNew,
            button_deptCancelEdit,
            button_deptDelete,
            button_deptUpdate;

    EditText editText_deptNameSave,
            editText_deptNameEdit;

    SharedPreferences sharedPreferences_login;

//    TextView textView_selectedDepartment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_departmen_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialization of LinearLayout
        linearLayout_home = (LinearLayout) findViewById(R.id.linearLayout_home);
        linearLayout_new = (LinearLayout) findViewById(R.id.linearLayout_new);
        linearLayout_edit = (LinearLayout) findViewById(R.id.linearLayout_edit);

        // set visibility
        linearLayout_new.setVisibility(View.GONE);
        linearLayout_edit.setVisibility(View.GONE);


        // initialization of spinner
        spinner_department = (Spinner) findViewById(R.id.spinner_department);
        spinner_deptEnableDisableNew = (Spinner) findViewById(R.id.spinner_deptEnableDisableNew);
        spinner_deptEnableDisableEdit = (Spinner) findViewById(R.id.spinner_deptEnableDisableEdit);


        // initialization of button
        button_deptNew = (Button) findViewById(R.id.button_deptNew);
        button_deptEdit = (Button) findViewById(R.id.button_deptEdit);
        button_deptSave = (Button) findViewById(R.id.button_deptSave);
        button_deptCancelNew = (Button) findViewById(R.id.button_deptCancelNew);
        button_deptCancelEdit = (Button) findViewById(R.id.button_deptCancelEdit);
        button_deptDelete = (Button) findViewById(R.id.button_deptDelete);
        button_deptUpdate = (Button) findViewById(R.id.button_deptUpdate);


        // click listener on button
        button_deptNew.setOnClickListener(this);
        button_deptEdit.setOnClickListener(this);
        button_deptSave.setOnClickListener(this);
        button_deptCancelNew.setOnClickListener(this);
        button_deptCancelEdit.setOnClickListener(this);
        button_deptDelete.setOnClickListener(this);
        button_deptUpdate.setOnClickListener(this);

        // initialization of EditText
        editText_deptNameSave = (EditText) findViewById(R.id.editText_deptNameSave);
        editText_deptNameEdit = (EditText) findViewById(R.id.editText_deptNameEdit);


        // load data to spinner
        departmentSpinnerDataFromDb();
        departmentSpinnerDataFromString();


        // hide delete button if employee log in
        sharedPreferences_login = getSharedPreferences(MainActivity.MY_PREF_NAME_LOGIN, MODE_PRIVATE);

        String empType = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_TYPE, "");
        if (empType.equals("Employee")) {
            button_deptDelete.setVisibility(View.GONE);
        }


    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_deptNew:
                linearLayout_home.setVisibility(View.GONE);
                linearLayout_new.setVisibility(View.VISIBLE);


                break;

            case R.id.button_deptEdit:
                if (spinner_department.getSelectedItem().toString().equals("Select")) {
                    Toast.makeText(getApplicationContext(), "No Department, Make New One", Toast.LENGTH_LONG).show();
                } else {
                    linearLayout_home.setVisibility(View.GONE);
                    linearLayout_edit.setVisibility(View.VISIBLE);
                    linearLayout_new.setVisibility(View.GONE);

                    editText_deptNameEdit.setText(spinner_department.getSelectedItem().toString()); // LOAD DESIGNATION NAME FROM SPINNER
                }


                break;

            case R.id.button_deptSave:

                if (editText_deptNameSave.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Insert Department Name", Toast.LENGTH_LONG).show();
                } else {
                    MyDBH myDBH = new MyDBH(this);

                    boolean ok = myDBH.saveDepartment(editText_deptNameSave.getText().toString(), spinner_deptEnableDisableNew.getSelectedItem().toString());
                    if (ok) {
                        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Department already exists.", Toast.LENGTH_LONG).show();
                    }
                }


                break;

            case R.id.button_deptCancelNew:
                linearLayout_home.setVisibility(View.VISIBLE);
                linearLayout_edit.setVisibility(View.GONE);
                linearLayout_new.setVisibility(View.GONE);

                departmentSpinnerDataFromDb(); // to refresh the spinner data

                break;

            case R.id.button_deptCancelEdit:

                    linearLayout_home.setVisibility(View.VISIBLE);
                    linearLayout_edit.setVisibility(View.GONE);
                    linearLayout_new.setVisibility(View.GONE);

                    departmentSpinnerDataFromDb(); // to refresh the spinner data



                break;

            case R.id.button_deptDelete:

                // while selecting to delete a department I get data from selected spinner because edit text data can changed by user without updating database
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Are you sure that you want to delete (" + spinner_department.getSelectedItem().toString() + ") from the database?");

                b.setNegativeButton("No", null);
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBH myDBH = new MyDBH(getApplicationContext());
                        long confirmDelete = myDBH.deleteDepartment(spinner_department.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Deleted! " + confirmDelete + "row affected", Toast.LENGTH_LONG).show();

                        // ok and view home part of department
                        linearLayout_home.setVisibility(View.VISIBLE);
                        linearLayout_edit.setVisibility(View.GONE);
                        linearLayout_new.setVisibility(View.GONE);

                        // to refresh the spinner data
                        departmentSpinnerDataFromDb();
                    }
                });
                b.setCancelable(true);
                b.create().show();


                break;

            case R.id.button_deptUpdate:

                if (editText_deptNameEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Insert Designation", Toast.LENGTH_LONG).show();
                } else {
                    String[] convOld = {spinner_department.getSelectedItem().toString()};
                    String[] convNew = {editText_deptNameEdit.getText().toString()};
                    // database handler
                    MyDBH db = new MyDBH(getApplicationContext());

                    long r = db.updateDepartment(convOld, convNew, spinner_deptEnableDisableEdit.getSelectedItem().toString());
                    if (r > 0) {
                        Toast.makeText(this, "Success " + r + " Row affected!", Toast.LENGTH_LONG).show();

                        // ok and view home part of department
                        linearLayout_home.setVisibility(View.VISIBLE);
                        linearLayout_edit.setVisibility(View.GONE);
                        linearLayout_new.setVisibility(View.GONE);

                        // to refresh the spinner data
                        departmentSpinnerDataFromDb();

                    } else {
                        Toast.makeText(this, "Error " + r + " Row affected!\nTry another name.", Toast.LENGTH_LONG).show();
                    }
                }

                break;


        }
    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void departmentSpinnerDataFromDb() {
        // database handler
        MyDBH db = new MyDBH(getApplicationContext());

        // Spinner click listener
        spinner_department.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> deptData = db.getAllDepartment();


        // Creating adapter for spinner
        if (deptData.isEmpty()) { // I did if empty an empty array from string will load; i faced error while empty value from db
            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                    R.array.blank_array, android.R.layout.simple_spinner_item);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_department.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, deptData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_department.setAdapter(dataAdapter);
        }


    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void departmentSpinnerDataFromString() {

        ArrayAdapter<CharSequence> adapterEnDs = ArrayAdapter.createFromResource(this, R.array.enable_disable_array, android.R.layout.simple_spinner_item);
        adapterEnDs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_deptEnableDisableEdit.setAdapter(adapterEnDs);
        spinner_deptEnableDisableNew.setAdapter(adapterEnDs);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
