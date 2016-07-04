package com.touhidapps.myemployee;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.List;

public class EmployeeDetails extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    MyDBH myDBH;

    TextView textView_empName,
            textView_empPhone,
            textView_empType,
            textView_empAddress,
            textView_empDistrict,
            textView_empDepartment,
            textView_empDesignation,
            textView_empEducation,
            textView_empNominees,
            textView_empJobHistory,
            textView_empJoiningDate,
            textView_empEnableDisable,
            textView_empUserName,
            textView_empPass;

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

    Spinner spinner_userTypeUpdate,
            spinner_userDepartmentUpdate,
            spinner_userDesignationUpdate,
            spinner_empEnableDisableUpdate;

    Button button_edit,
            button_addDepartmentUpdate,
            button_addDesignationUpdate,
            button_cancelEmployeeUpdate,
            button_EmployeeDelete,
            button_saveEmployeeUpdate;

    LinearLayout linearLayout_info,
            linearLayout_update;

    Calendar calendar_joiningDateUpdate;
    DatePickerDialog.OnDateSetListener dateSetListener_joiningDateUpdate;

    SharedPreferences sharedPreferences_login;

    //    String[] id;
    String idFromListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize
        textView_empName = (TextView) findViewById(R.id.textView_empName);
        textView_empPhone = (TextView) findViewById(R.id.textView_empPhone);
        textView_empType = (TextView) findViewById(R.id.textView_empType);
        textView_empAddress = (TextView) findViewById(R.id.textView_empAddress);
        textView_empDistrict = (TextView) findViewById(R.id.textView_empDistrict);
        textView_empDepartment = (TextView) findViewById(R.id.textView_empDepartment);
        textView_empDesignation = (TextView) findViewById(R.id.textView_empDesignation);
        textView_empEducation = (TextView) findViewById(R.id.textView_empEducation);
        textView_empNominees = (TextView) findViewById(R.id.textView_empNominees);
        textView_empJobHistory = (TextView) findViewById(R.id.textView_empJobHistory);
        textView_empJoiningDate = (TextView) findViewById(R.id.textView_empJoiningDate);
        textView_empEnableDisable = (TextView) findViewById(R.id.textView_empEnableDisable);
        textView_empUserName = (TextView) findViewById(R.id.textView_empUserName);
        textView_empPass = (TextView) findViewById(R.id.textView_empPass);

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

        spinner_userTypeUpdate = (Spinner) findViewById(R.id.spinner_userTypeUpdate);
        spinner_userDepartmentUpdate = (Spinner) findViewById(R.id.spinner_userDepartmentUpdate);
        spinner_userDesignationUpdate = (Spinner) findViewById(R.id.spinner_userDesignationUpdate);
        spinner_empEnableDisableUpdate = (Spinner) findViewById(R.id.spinner_empEnableDisableUpdate);

        button_edit = (Button) findViewById(R.id.button_edit);
        button_edit.setOnClickListener(this);

        button_addDepartmentUpdate = (Button) findViewById(R.id.button_addDepartmentUpdate);
        button_addDepartmentUpdate.setOnClickListener(this);

        button_addDesignationUpdate = (Button) findViewById(R.id.button_addDesignationUpdate);
        button_addDesignationUpdate.setOnClickListener(this);

        button_cancelEmployeeUpdate = (Button) findViewById(R.id.button_cancelEmployeeUpdate);
        button_cancelEmployeeUpdate.setOnClickListener(this);

        button_EmployeeDelete = (Button) findViewById(R.id.button_EmployeeDelete);
        button_EmployeeDelete.setOnClickListener(this);

        button_saveEmployeeUpdate = (Button) findViewById(R.id.button_saveEmployeeUpdate);
        button_saveEmployeeUpdate.setOnClickListener(this);


        linearLayout_info = (LinearLayout) findViewById(R.id.linearLayout_info);
        linearLayout_update = (LinearLayout) findViewById(R.id.linearLayout_update);


        // while opening the activity edit/update part of layout will be visibility GONE
        linearLayout_update.setVisibility(View.GONE);


        myDBH = new MyDBH(this);


        // get employee data to get all info using id
        // Get the message2 from the intent
        idFromListItem = getIntent().getStringExtra(AdminPanel.EXTRA_MESSAGE2);

        List<String> employeeAllData = myDBH.getEmployeeAllData(idFromListItem);

        // set data on textView
//        textView_empId.setText(employeeAllData.get(0)); // no need
        textView_empName.setText(employeeAllData.get(1));
        textView_empPhone.setText(employeeAllData.get(2));
        textView_empType.setText(employeeAllData.get(3));
        textView_empAddress.setText(employeeAllData.get(4));
        textView_empDistrict.setText(employeeAllData.get(5));
        textView_empDepartment.setText(employeeAllData.get(6));
        textView_empDesignation.setText(employeeAllData.get(7));
        textView_empEducation.setText(employeeAllData.get(8));
        textView_empNominees.setText(employeeAllData.get(9));
        textView_empJobHistory.setText(employeeAllData.get(10));
        textView_empJoiningDate.setText(employeeAllData.get(11));
        textView_empEnableDisable.setText(employeeAllData.get(12));
        textView_empUserName.setText(employeeAllData.get(13));
        textView_empPass.setText(employeeAllData.get(14));


        // spinner load
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


        // hide delete button if employee log in
        sharedPreferences_login = getSharedPreferences(MainActivity.MY_PREF_NAME_LOGIN, MODE_PRIVATE);

        String empType = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_TYPE, "");
        if (empType.equals("Employee")) {
            button_EmployeeDelete.setVisibility(View.GONE);
        }


    } // end of onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_edit:
                linearLayout_info.setVisibility(View.GONE);
                linearLayout_update.setVisibility(View.VISIBLE);

                // set data to edit text
                editText_empNameUpdate.setText(textView_empName.getText().toString());
                editText_empPhoneUpdate.setText(textView_empPhone.getText().toString());

                spinner_userTypeUpdate.setSelection(((ArrayAdapter) spinner_userTypeUpdate.getAdapter()).getPosition(textView_empType.getText().toString())); // spinner selection from text value

                editText_empAddressUpdate.setText(textView_empAddress.getText().toString());
                editText_empDistrictUpdate.setText(textView_empDistrict.getText().toString());

                spinner_userDepartmentUpdate.setSelection(((ArrayAdapter) spinner_userDepartmentUpdate.getAdapter()).getPosition(textView_empDepartment.getText().toString())); // spinner selection from text value
                spinner_userDesignationUpdate.setSelection(((ArrayAdapter) spinner_userDesignationUpdate.getAdapter()).getPosition(textView_empDesignation.getText().toString())); // spinner selection from text value

                editText_empEducationUpdate.setText(textView_empEducation.getText().toString());
                editText_empNomineesUpdate.setText(textView_empNominees.getText().toString());
                editText_empJobHistoryUpdate.setText(textView_empJobHistory.getText().toString());
                editText_empJoiningDateUpdate.setText(textView_empJoiningDate.getText().toString());

                spinner_empEnableDisableUpdate.setSelection(((ArrayAdapter) spinner_empEnableDisableUpdate.getAdapter()).getPosition(textView_empEnableDisable.getText().toString())); // spinner selection from text value

                editText_empUserNameUpdate.setText(textView_empUserName.getText().toString());
                editText_empPasswordUpdate.setText(textView_empPass.getText().toString());

                break;

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

                    String[] convertId = {idFromListItem};
                    long rowAffected = myDBH.updateInfo(convertId, empName, empPhone, empType, empAddress, empDistrict, empDepartment, empDesignation, empEducation,
                            empNominees, empJobHistory, empJoiningDate, empEnableDisable, empUserName, empPassword);

                    if (rowAffected > 0) {
                        Toast.makeText(getApplicationContext(), "Done! " + rowAffected + " Row Affected.", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Not Done! " + rowAffected + " Row Affected.", Toast.LENGTH_LONG).show();
                    }

                }


                break;


            case R.id.button_EmployeeDelete:

                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("Are you sure that you want to delete " + editText_empNameUpdate.getText().toString() + " from the database?");

                b.setNegativeButton("No", null);
                b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        long confirmDelete = myDBH.deleteEmployee(idFromListItem);
                        Toast.makeText(getApplicationContext(), "Deleted! " + confirmDelete + "row affected", Toast.LENGTH_LONG).show();
                    }
                });
                b.setCancelable(true);
                b.create().show();


                break;

            case R.id.button_cancelEmployeeUpdate:

                linearLayout_info.setVisibility(View.VISIBLE);
                linearLayout_update.setVisibility(View.GONE);

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

//        editText_empJoiningDateUpdate = (EditText) findViewById(R.id.editText_empJoiningDateUpdate);
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
                myDatePicker.clickToShowDatePicker(EmployeeDetails.this, dateSetListener_joiningDateUpdate, calendar_joiningDateUpdate);
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
