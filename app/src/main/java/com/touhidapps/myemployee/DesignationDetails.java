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

public class DesignationDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    LinearLayout linearLayout_desig_home,
            linearLayout_desig_new,
            linearLayout_desig_edit;

    Spinner spinner_designation,
            spinner_desigEnableDisableNew,
            spinner_desigEnableDisableEdit;

    Button button_desigNew,
            button_desigEdit,
            button_desigCancelNew,
            button_desigSave,
            button_desigCancelEdit,
            button_desigDelete,
            button_desigUpdate;

    EditText editText_desigNameSave,
            editText_desigNameEdit;

    SharedPreferences sharedPreferences_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designation_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialization of LinearLayout
        linearLayout_desig_home = (LinearLayout) findViewById(R.id.linearLayout_desig_home);
        linearLayout_desig_new = (LinearLayout) findViewById(R.id.linearLayout_desig_new);
        linearLayout_desig_edit = (LinearLayout) findViewById(R.id.linearLayout_desig_edit);

        // set visibility
        linearLayout_desig_new.setVisibility(View.GONE);
        linearLayout_desig_edit.setVisibility(View.GONE);


        // initialization of spinner
        spinner_designation = (Spinner) findViewById(R.id.spinner_designation);
        spinner_desigEnableDisableNew = (Spinner) findViewById(R.id.spinner_desigEnableDisableNew);
        spinner_desigEnableDisableEdit = (Spinner) findViewById(R.id.spinner_desigEnableDisableEdit);


        // initialization of button
        button_desigNew = (Button) findViewById(R.id.button_desigNew);
        button_desigEdit = (Button) findViewById(R.id.button_desigEdit);
        button_desigCancelNew = (Button) findViewById(R.id.button_desigCancelNew);
        button_desigSave = (Button) findViewById(R.id.button_desigSave);
        button_desigCancelEdit = (Button) findViewById(R.id.button_desigCancelEdit);
        button_desigDelete = (Button) findViewById(R.id.button_desigDelete);
        button_desigUpdate = (Button) findViewById(R.id.button_desigUpdate);


        // click listener on button
        button_desigNew.setOnClickListener(this);
        button_desigEdit.setOnClickListener(this);
        button_desigCancelNew.setOnClickListener(this);
        button_desigSave.setOnClickListener(this);
        button_desigCancelEdit.setOnClickListener(this);
        button_desigDelete.setOnClickListener(this);
        button_desigUpdate.setOnClickListener(this);

        // initialization of EditText
        editText_desigNameSave = (EditText) findViewById(R.id.editText_desigNameSave);
        editText_desigNameEdit = (EditText) findViewById(R.id.editText_desigNameEdit);


        // load data to spinner
        designationSpinnerDataFromDb();
        designationSpinnerDataFromString();


        // hide delete button if employee log in
        sharedPreferences_login = getSharedPreferences(MainActivity.MY_PREF_NAME_LOGIN, MODE_PRIVATE);

        String empType = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_TYPE, "");
        if (empType.equals("Employee")) {
            button_desigDelete.setVisibility(View.GONE);
        }

    } // end of onCreate

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_desigNew:

                linearLayout_desig_home.setVisibility(View.GONE);
                linearLayout_desig_new.setVisibility(View.VISIBLE);

                break;

            case R.id.button_desigEdit:

                if (spinner_designation.getSelectedItem().toString().equals("Select")) {
                    Toast.makeText(getApplicationContext(), "No Designation, Make New One", Toast.LENGTH_LONG).show();
                } else {
                    linearLayout_desig_home.setVisibility(View.GONE);
                    linearLayout_desig_edit.setVisibility(View.VISIBLE);
                    linearLayout_desig_new.setVisibility(View.GONE);

//                textView_selectedDepartment.setText(spinner_department.getSelectedItem().toString()); // LOAD DESIGNATION NAME FROM SPINNER
                    editText_desigNameEdit.setText(spinner_designation.getSelectedItem().toString()); // LOAD DESIGNATION NAME FROM SPINNER
                }

                break;

            case R.id.button_desigCancelNew:
                linearLayout_desig_home.setVisibility(View.VISIBLE);
                linearLayout_desig_edit.setVisibility(View.GONE);
                linearLayout_desig_new.setVisibility(View.GONE);

                designationSpinnerDataFromDb(); // to refresh the spinner data

                break;

            case R.id.button_desigSave:

                if (editText_desigNameSave.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Insert Designation Name", Toast.LENGTH_LONG).show();
                } else {
                    MyDBH myDBH = new MyDBH(getApplicationContext());

                    boolean ok = myDBH.saveDesignation(editText_desigNameSave.getText().toString(), spinner_desigEnableDisableNew.getSelectedItem().toString());
                    if (ok) {
                        Toast.makeText(this, "Success", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Designation already exists.", Toast.LENGTH_LONG).show();
                    }
                }

                break;

            case R.id.button_desigCancelEdit:

                linearLayout_desig_home.setVisibility(View.VISIBLE);
                linearLayout_desig_edit.setVisibility(View.GONE);
                linearLayout_desig_new.setVisibility(View.GONE);

                designationSpinnerDataFromDb(); // to refresh the spinner data

                break;

            case R.id.button_desigDelete:
                // while selecting to delete a designation I get data from selected spinner because edit text data can changed by user without updating database
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Are you sure that you want to delete (" + spinner_designation.getSelectedItem().toString() + ") from the database?");

                b.setNegativeButton("No", null);
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDBH myDBH = new MyDBH(getApplicationContext());
                        long confirmDelete = myDBH.deleteDesignation(spinner_designation.getSelectedItem().toString());
                        Toast.makeText(getApplicationContext(), "Deleted! " + confirmDelete + "row affected", Toast.LENGTH_LONG).show();

                        // ok and view home part of department
                        linearLayout_desig_home.setVisibility(View.VISIBLE);
                        linearLayout_desig_edit.setVisibility(View.GONE);
                        linearLayout_desig_new.setVisibility(View.GONE);

                        // to refresh the spinner data
                        designationSpinnerDataFromDb();
                    }
                });
                b.setCancelable(true);
                b.create().show();

                break;

            case R.id.button_desigUpdate:
                if (editText_desigNameEdit.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Insert Designation", Toast.LENGTH_LONG).show();
                } else {
                    String[] convOld = {spinner_designation.getSelectedItem().toString()};
                    String[] convNew = {editText_desigNameEdit.getText().toString()};
                    // database handler
                    MyDBH db = new MyDBH(getApplicationContext());

                    long r = db.updateDesignation(convOld, convNew, spinner_desigEnableDisableEdit.getSelectedItem().toString());
                    if (r > 0) {
                        Toast.makeText(this, "Success " + r + " Row affected!", Toast.LENGTH_LONG).show();

                        // ok and view home part of department
                        linearLayout_desig_home.setVisibility(View.VISIBLE);
                        linearLayout_desig_edit.setVisibility(View.GONE);
                        linearLayout_desig_new.setVisibility(View.GONE);

                        // to refresh the spinner data
                        designationSpinnerDataFromDb();

                    } else {
                        Toast.makeText(this, "Error " + r + " Row affected!\nTry another name.", Toast.LENGTH_LONG).show();
                    }
                }

                break;


        }

    }

    private void designationSpinnerDataFromDb() {
        // database handler
        MyDBH db = new MyDBH(getApplicationContext());

        // Spinner click listener
        spinner_designation.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> deptData = db.getAllDesignation();


        // Creating adapter for spinner
        if (deptData.isEmpty()) { // I did if empty an empty array from string will load; i faced error while empty value from db
            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                    R.array.blank_array, android.R.layout.simple_spinner_item);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_designation.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, deptData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_designation.setAdapter(dataAdapter);
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void designationSpinnerDataFromString() {

        ArrayAdapter<CharSequence> adapterEnDs = ArrayAdapter.createFromResource(this, R.array.enable_disable_array, android.R.layout.simple_spinner_item);
        adapterEnDs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_desigEnableDisableEdit.setAdapter(adapterEnDs);
        spinner_desigEnableDisableNew.setAdapter(adapterEnDs);

    }
}
