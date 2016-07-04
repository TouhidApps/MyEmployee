package com.touhidapps.myemployee;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import java.util.List;

public class AdminPanel extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    public final static String EXTRA_MESSAGE = "com.touhidapps.myemployee.MESSAGE";
    public final static String EXTRA_MESSAGE2 = "com.touhidapps.myemployee.MESSAGE2";

    SharedPreferences sharedPreferences_login;

    MyDBH myDBH;
    MyCustomAdapter myCustomAdapter;
    ListView listView_employee;

    Button button_newEmployee,
            button_report,
            button_myProfile;


//    ImageButton imageButton_refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button_newEmployee = (Button) findViewById(R.id.button_newEmployee);
        button_report = (Button) findViewById(R.id.button_report);
        button_myProfile = (Button) findViewById(R.id.button_myProfile);
        button_newEmployee.setOnClickListener(this);
        button_report.setOnClickListener(this);
        button_myProfile.setOnClickListener(this);

        listView_employee = (ListView) findViewById(R.id.listView_employee);

        myDBH = new MyDBH(this);

        List<String> employeeData = myDBH.getAllEmployeeList();

        String[] id = new String[employeeData.size() / 3]; // divided how much data slot are coming, extra size will make unnecessary blank list items
        String[] name = new String[employeeData.size() / 3];
        String[] phone = new String[employeeData.size() / 3];

        boolean boolId = true,
                boolName = false,
                boolPhone = false;
        int intId = 0,
                intName = 0,
                intPhone = 0;

        for (int i = 0; i < employeeData.size(); i++) {

            if (boolId) {
                id[intId] = employeeData.get(i);
                intId++;
                boolId = false;
                boolName = true;
                boolPhone = false;
                continue;
            } else if (boolName) {
                name[intName] = employeeData.get(i);
                intName++;
                boolId = false;
                boolName = false;
                boolPhone = true;
                continue;
            } else if (boolPhone) {
                phone[intPhone] = employeeData.get(i);
                intPhone++;
                boolId = true;
                boolName = false;
                boolPhone = false;
//                continue;
            }

        }


        myCustomAdapter = new MyCustomAdapter(this, id, name, phone);
        listView_employee.setAdapter(myCustomAdapter);

        // item click
        listView_employee.setOnItemClickListener(this);



    } // end of onCreate


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_newEmployee:
                startActivity(new Intent(getApplicationContext(), NewEmployee.class));

                // make it finish to make refresh list
                finish();
                break;

            case R.id.button_report:

                startActivity(new Intent(this, ReportPanel.class));

                break;


            case R.id.button_myProfile:
                sharedPreferences_login = getSharedPreferences(MainActivity.MY_PREF_NAME_LOGIN, MODE_PRIVATE);

                String type = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_TYPE, "");
                String name = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_NAME, "");
                String pass = sharedPreferences_login.getString(MainActivity.MY_PREF_KEY_LOGIN_PASSWORD, "");

                final List<String> me = myDBH.seeMyProfile(type, name, pass);
                AlertDialog.Builder b = new AlertDialog.Builder(this);
                b.setMessage("ID: " + me.get(0) + "\n" + "Name: " + me.get(1) + "\n" + "Phone: " + me.get(2) + "\n" + "Type: " + me.get(3) + "\n" + "Address: " + me.get(4) + "\n" +
                        "District: " + me.get(5) + "\n" + "Department: " + me.get(6) + "\n" + "Designation: " + me.get(7) + "\n" + "Education: " + me.get(8) + "\n" + "Nominee: " + me.get(9) + "\n" +
                        "Job History: " + me.get(10) + "\n" + "Joining Date: " + me.get(11) + "\n" + "Enable/Disable: " + me.get(12) + "\n" + "Username: " + me.get(13)); // no need to show pass


                b.setNegativeButton("Ok", null);
                b.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String[] dd = {me.get(0), me.get(1), me.get(2), me.get(3), me.get(4),me.get(5), me.get(6), me.get(7), me.get(8), me.get(9),
                                me.get(10), me.get(11), me.get(12), me.get(13), me.get(14)}; // with password no 14
                        Intent intent = new Intent(getApplicationContext(), UpdateInformation.class);
                        intent.putExtra(EXTRA_MESSAGE, dd);
                        startActivity(intent);
                    }
                });
                b.setCancelable(true);
                b.create().show();

                break;



        }
    }


    @Override
    protected void onRestart() {
        // to make refresh the list view
        finish();
        startActivity(new Intent(getApplicationContext(), AdminPanel.class));


        super.onRestart();
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


        // get employee id from the listView item, (Kept hidden, don't need to show it on the list item)
        TextView empID = (TextView) view.findViewById(R.id.textView_list_item_id);
        String getEmpID = empID.getText().toString();


        Intent intent = new Intent(getApplicationContext(), EmployeeDetails.class);
        intent.putExtra(EXTRA_MESSAGE2, getEmpID);

        startActivity(intent);


    }





}
