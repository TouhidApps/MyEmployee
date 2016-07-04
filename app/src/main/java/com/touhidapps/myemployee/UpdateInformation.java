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

public class UpdateInformation extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    MyDBH myDbh;
    String[] id;




    EditText editText_empNameUpdate,
            editText_empPhoneUpdate,
            editText_empAddressUpdate,
            editText_empDistrictUpdate,
            editText_empEducationUpdate,
            editText_empNomineesUpdate,
            editText_empJobHistoryUpdate,
            editText_empJoiningDateUpdate,
            editText_empUserNameUpdate,
            editText_empPasswordUpdate;

    Button button_addDepartmentUpdate,
            button_addDesignationUpdate,
            button_saveEmployeeUpdate;

    Spinner spinner_userTypeUpdate,
            spinner_userDepartmentUpdate,
            spinner_userDesignationUpdate,
            spinner_empEnableDisableUpdate;

    Calendar calendar_joiningDateUpdate;
    DatePickerDialog.OnDateSetListener dateSetListener_joiningDateUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_information);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_empNameUpdate = (EditText) findViewById(R.id.editText_empNameUpdate);
        editText_empPhoneUpdate = (EditText) findViewById(R.id.editText_empPhoneUpdate);
        editText_empAddressUpdate = (EditText) findViewById(R.id.editText_empAddressUpdate);
        editText_empDistrictUpdate = (EditText) findViewById(R.id.editText_empDistrictUpdate);
        editText_empEducationUpdate = (EditText) findViewById(R.id.editText_empEducationUpdate);
        editText_empNomineesUpdate = (EditText) findViewById(R.id.editText_empNomineesUpdate);
        editText_empJobHistoryUpdate = (EditText) findViewById(R.id.editText_empJobHistoryUpdate);
        editText_empJoiningDateUpdate = (EditText) findViewById(R.id.editText_empJoiningDateUpdate);
        editText_empUserNameUpdate = (EditText) findViewById(R.id.editText_empUserNameUpdate);
        editText_empPasswordUpdate = (EditText) findViewById(R.id.editText_empPasswordUpdate);

        button_addDepartmentUpdate = (Button) findViewById(R.id.button_addDepartmentUpdate);
        button_addDesignationUpdate = (Button) findViewById(R.id.button_addDesignationUpdate);
        button_saveEmployeeUpdate = (Button) findViewById(R.id.button_saveEmployeeUpdate);
        button_addDepartmentUpdate.setOnClickListener(this);
        button_addDesignationUpdate.setOnClickListener(this);
        button_saveEmployeeUpdate.setOnClickListener(this);


        myDbh = new MyDBH(this);

        // spinners
        spinner_userTypeUpdate = (Spinner) findViewById(R.id.spinner_userTypeUpdate);
        spinner_userDepartmentUpdate = (Spinner) findViewById(R.id.spinner_userDepartmentUpdate);
        spinner_userDesignationUpdate = (Spinner) findViewById(R.id.spinner_userDesignationUpdate);
        spinner_empEnableDisableUpdate = (Spinner) findViewById(R.id.spinner_empEnableDisableUpdate);
        // spinner_employeeType.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterType = ArrayAdapter.createFromResource(this, R.array.employee_type_array, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> adapterEnDs = ArrayAdapter.createFromResource(this, R.array.enable_disable_array, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterEnDs.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner_userTypeUpdate.setAdapter(adapterType);
        spinner_empEnableDisableUpdate.setAdapter(adapterEnDs);

        loadSpinnerDepartmentUpdate(); // from db
        loadSpinnerDesignationUpdate(); // from db

        joiningDatePickerUpdate();

        // Get the message from the intent
        Intent intent = getIntent();
        String[] message = intent.getStringArrayExtra(AdminPanel.EXTRA_MESSAGE);


        id = new String[]{message[0]};
        editText_empNameUpdate.setText(message[1]);
        editText_empPhoneUpdate.setText(message[2]);

        spinner_userTypeUpdate.setSelection(((ArrayAdapter)spinner_userTypeUpdate.getAdapter()).getPosition(message[3])); // spinner selection from text value

        editText_empAddressUpdate.setText(message[4]);
        editText_empDistrictUpdate.setText(message[5]);

        spinner_userDepartmentUpdate.setSelection(((ArrayAdapter)spinner_userDepartmentUpdate.getAdapter()).getPosition(message[6])); // spinner selection from text value
        spinner_userDesignationUpdate.setSelection(((ArrayAdapter)spinner_userDesignationUpdate.getAdapter()).getPosition(message[7])); // spinner selection from text value

        editText_empEducationUpdate.setText(message[8]);
        editText_empNomineesUpdate.setText(message[9]);
        editText_empJobHistoryUpdate.setText(message[10]);
        editText_empJoiningDateUpdate.setText(message[11]);

        spinner_empEnableDisableUpdate.setSelection(((ArrayAdapter)spinner_empEnableDisableUpdate.getAdapter()).getPosition(message[12])); // spinner selection from text value

        editText_empUserNameUpdate.setText(message[13]);
        editText_empPasswordUpdate.setText(message[14]);







    } // end of onCreate

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_saveEmployeeUpdate:

                String empName = editText_empNameUpdate.getText().toString();
                String empPhone = editText_empPhoneUpdate.getText().toString();
                String empType = spinner_userTypeUpdate.getSelectedItem().toString();
                String empAddress = editText_empAddressUpdate.getText().toString();
                String empDistrict = editText_empDistrictUpdate.getText().toString();
                String empDepartment = spinner_userDepartmentUpdate.getSelectedItem().toString();
                String empDesignation = spinner_userDesignationUpdate.getSelectedItem().toString();
                String empEducation = editText_empEducationUpdate.getText().toString();
                String empNominees = editText_empNomineesUpdate.getText().toString();
                String empJobHistory = editText_empJobHistoryUpdate.getText().toString();
                String empJoiningDate = editText_empJoiningDateUpdate.getText().toString();
                String empEnableDisable = spinner_empEnableDisableUpdate.getSelectedItem().toString();
                String empUserName = editText_empUserNameUpdate.getText().toString();
                String empPassword = editText_empPasswordUpdate.getText().toString();

                if (empName.isEmpty() || empPhone.isEmpty() || empUserName.isEmpty() || empPassword.isEmpty()) {

                    Toast.makeText(getApplicationContext(), "Fill the empty field.", Toast.LENGTH_LONG).show();

                } else {

                    long rowAffected = myDbh.updateInfo(id, empName, empPhone, empType, empAddress, empDistrict, empDepartment, empDesignation, empEducation,
                            empNominees, empJobHistory, empJoiningDate, empEnableDisable, empUserName, empPassword);

                    if (rowAffected > 0) {
                        Toast.makeText(getApplicationContext(), "Done! " + rowAffected + " Row Affected.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Done! " + rowAffected + " Row Affected.", Toast.LENGTH_LONG).show();
                    }

                }



                break;

            case R.id.button_addDesignationUpdate:

                startActivity(new Intent(getApplicationContext(), DesignationDetails.class));

                break;

            case R.id.button_addDepartmentUpdate:

                startActivity(new Intent(getApplicationContext(), DepartmenDetails.class));

                break;

        }
    }





    @Override
    protected void onRestart() {

        // to refresh data while back after inserting new dept or desig
        loadSpinnerDepartmentUpdate(); // from db
        loadSpinnerDesignationUpdate(); // from db


        super.onRestart();
    }

    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerDepartmentUpdate() {


        // Spinner click listener
        spinner_userDepartmentUpdate.setOnItemSelectedListener(this);

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
            spinner_userDepartmentUpdate.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, deptData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDepartmentUpdate.setAdapter(dataAdapter);
        }


    }


    /**
     * Function to load the spinner data from SQLite database
     */
    private void loadSpinnerDesignationUpdate() {


        // Spinner click listener
        spinner_userDesignationUpdate.setOnItemSelectedListener(this);

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
            spinner_userDesignationUpdate.setAdapter(dataAdapter);
        } else {  // else section is normal section to load data from db to spinner, if section can be avoided if no need to insert data to db
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, desigData);
            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinner_userDesignationUpdate.setAdapter(dataAdapter);
        }


    }



    public void joiningDatePickerUpdate() {

        editText_empJoiningDateUpdate = (EditText) findViewById(R.id.editText_empJoiningDateUpdate);
        calendar_joiningDateUpdate = Calendar.getInstance();
        dateSetListener_joiningDateUpdate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.setDate(editText_empJoiningDateUpdate, calendar_joiningDateUpdate, year, monthOfYear, dayOfMonth);
            }
        };

        editText_empJoiningDateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatePicker myDatePicker = new MyDatePicker();
                myDatePicker.clickToShowDatePicker(UpdateInformation.this, dateSetListener_joiningDateUpdate, calendar_joiningDateUpdate);
            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


}
