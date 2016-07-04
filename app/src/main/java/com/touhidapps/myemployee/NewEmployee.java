package com.touhidapps.myemployee;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class NewEmployee extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    MyDBH myDbh;

    EditText editText_empName,
            editText_empPhone,
            editText_empAddress,
            editText_empDistrict,
            editText_empEducation,
            editText_empNominees,
            editText_empJobHistory,
            editText_empJoiningDate,
            editText_empUserName,
            editText_empPassword;

    Button button_addDepartment, button_addDesignation, button_saveNewEmployee;

    Spinner spinner_userType,
            spinner_userDepartment,
            spinner_userDesignation,
            spinner_empEnableDisable;

    Calendar calendar_joiningDate;
    DatePickerDialog.OnDateSetListener dateSetListener_joiningDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_employee);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_empName = (EditText) findViewById(R.id.editText_empName);
        editText_empPhone = (EditText) findViewById(R.id.editText_empPhone);
        editText_empAddress = (EditText) findViewById(R.id.editText_empAddress);
        editText_empDistrict = (EditText) findViewById(R.id.editText_empDistrict);
        editText_empEducation = (EditText) findViewById(R.id.editText_empEducation);
        editText_empNominees = (EditText) findViewById(R.id.editText_empNominees);
        editText_empJobHistory = (EditText) findViewById(R.id.editText_empJobHistory);
        editText_empJoiningDate = (EditText) findViewById(R.id.editText_empJoiningDate);
        editText_empUserName = (EditText) findViewById(R.id.editText_empUserName);
        editText_empPassword = (EditText) findViewById(R.id.editText_empPassword);


        button_addDepartment = (Button) findViewById(R.id.button_addDepartment);
        button_addDesignation = (Button) findViewById(R.id.button_addDesignation);
        button_saveNewEmployee = (Button) findViewById(R.id.button_saveNewEmployee);
        button_addDepartment.setOnClickListener(this);
        button_addDesignation.setOnClickListener(this);
        button_saveNewEmployee.setOnClickListener(this);

        myDbh = new MyDBH(this);


        // spinners
        spinner_userType = (Spinner) findViewById(R.id.spinner_userType);
        spinner_userDepartment = (Spinner) findViewById(R.id.spinner_userDepartment);
        spinner_userDesignation = (Spinner) findViewById(R.id.spinner_userDesignation);
        spinner_empEnableDisable = (Spinner) findViewById(R.id.spinner_empEnableDisable);
        // spinner_employeeType.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.employee_type_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterEnDs = ArrayAdapter.createFromResource(this, R.array.enable_disable_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEnDs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_userType.setAdapter(adapterType);
        spinner_empEnableDisable.setAdapter(adapterEnDs);

        loadSpinnerDepartment(); // from db
        loadSpinnerDesignation(); // from db

        joiningDatePicker();


    } // end of onCreate

    @Override
    protected void onRestart() {

        // to refresh data while back after inserting new dept or desig
        loadSpinnerDepartment(); // from db
        loadSpinnerDesignation(); // from db


        super.onRestart();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_addDepartment:

                startActivity(new Intent(this, DepartmenDetails.class));


                break;


            case R.id.button_addDesignation:

                startActivity(new Intent(this, DesignationDetails.class));


                break;

            case R.id.button_saveNewEmployee:

                String empName = editText_empName.getText().toString();
                String empPhone = editText_empPhone.getText().toString();
                String empType = spinner_userType.getSelectedItem().toString();
                String empAddress = editText_empAddress.getText().toString();
                String empDistrice = editText_empDistrict.getText().toString();
                String empDepartment = spinner_userDepartment.getSelectedItem().toString();
                String empDesignation = spinner_userDesignation.getSelectedItem().toString();
                String empEducation = editText_empEducation.getText().toString();
                String empNominees = editText_empNominees.getText().toString();
                String empJobHistory = editText_empJobHistory.getText().toString();
                String empJoiningDate = editText_empJoiningDate.getText().toString();
                String empEnableDisable = spinner_empEnableDisable.getSelectedItem().toString();
                String empUserName = editText_empUserName.getText().toString();
                String empPassword = editText_empPassword.getText().toString();

                if (empName.isEmpty() || empPhone.isEmpty() || empUserName.isEmpty() || empPassword.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Fill the empty field.", Toast.LENGTH_LONG).show();

                } else {

                    boolean b = myDbh.saveNewEmployeeData(empName, empPhone, empType, empAddress, empDistrice, empDepartment, empDesignation, empEducation,
                            empNominees, empJobHistory, empJoiningDate, empEnableDisable, empUserName, empPassword);

                    if (b) {
                        Toast.makeText(getApplicationContext(), "Data inserted successfully.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not successful!", Toast.LENGTH_LONG).show();
                    }
                }


                break;
        }
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerDepartment() {


        // Spinner click listener
        spinner_userDepartment.setOnItemSelectedListener(this);

        // database handler
        MyDBH db = new MyDBH(getApplicationContext());

        // Spinner Drop down elements
        List<String> deptData = db.getAllDepartmentWithoutDisabled();


        // Creating adapter for spinner
        if (deptData.isEmpty()) { // I did if empty an empty array from string will load; i faced error while empty value from db
            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                    R.array.blank_array, android.R.layout.simple_spinner_item);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDepartment.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, deptData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDepartment.setAdapter(dataAdapter);
        }


    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerDesignation() {


        // Spinner click listener
        spinner_userDesignation.setOnItemSelectedListener(this);

        // database handler
        MyDBH db = new MyDBH(getApplicationContext());

        // Spinner Drop down elements
        List<String> desigData = db.getAllDesignationWithoutDisabled();


        // Creating adapter for spinner
        if (desigData.isEmpty()) { // I did if empty an empty array from string will load; i faced error while empty value from db
            ArrayAdapter<CharSequence> dataAdapter = ArrayAdapter.createFromResource(this,
                    R.array.blank_array, android.R.layout.simple_spinner_item);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDesignation.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, desigData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDesignation.setAdapter(dataAdapter);
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onBackPressed() {


        // to make refresh the list view
        finish();

        startActivity(new Intent(getApplicationContext(), AdminPanel.class));

        super.onBackPressed();

    }


    public void joiningDatePicker() {

        editText_empJoiningDate = (EditText) findViewById(R.id.editText_empJoiningDate);
        calendar_joiningDate = Calendar.getInstance();
        dateSetListener_joiningDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.setDate(editText_empJoiningDate, calendar_joiningDate, year, monthOfYear, dayOfMonth);
            }
        };

        editText_empJoiningDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.clickToShowDatePicker(NewEmployee.this, dateSetListener_joiningDate, calendar_joiningDate);
            }
        });

    }


}
