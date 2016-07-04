package com.touhidapps.myemployee;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    MyDBH myDBH;
    public static final String MY_PREF_NAME = "touhid";
    public static final String MY_PREF_NAME_LOGIN = "login";
    public static final String MY_PREF_KEY_LOGIN_TYPE = "myLoginType";
    public static final String MY_PREF_KEY_LOGIN_NAME = "myLoginName";
    public static final String MY_PREF_KEY_LOGIN_PASSWORD = "myLoginPass";
    SharedPreferences sharedPreferences_login;

    Spinner spinner_employeeType;

    TextView textll, textView_default;
    EditText editText_userName, editText_password;
    Button button_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editText_userName = (EditText) findViewById(R.id.editText_userName);
        editText_password = (EditText) findViewById(R.id.editText_password);
        button_login = (Button) findViewById(R.id.button_login);
        button_login.setOnClickListener(this);

        sharedPreferences_login = getSharedPreferences(MY_PREF_NAME_LOGIN, MODE_PRIVATE);

        spinner_employeeType = (Spinner) findViewById(R.id.spinner_userType);
        //        spinner_employeeType.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.employee_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_employeeType.setAdapter(adapter1);


        textll = (TextView) findViewById(R.id.textll);
        textView_default = (TextView) findViewById(R.id.textView_default);


        myDBH = new MyDBH(this);


        SharedPreferences sp = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE);
        String s = sp.getString("one", null);


        if (s != null) {
            textView_default.setVisibility(View.GONE);
        } else {
            SharedPreferences.Editor editor = getSharedPreferences(MY_PREF_NAME, MODE_PRIVATE).edit();
            editor.putString("one", "touhid");
            editor.apply();

            // create admin on database
            boolean b = myDBH.InsertDefaultLoginValue();

            if (b) {
                Toast.makeText(getApplicationContext(), "Default value inserted", Toast.LENGTH_SHORT).show();
            }


        }


//        textll.setText(myDBH.getAll()); // show all data on the login page with pass and username


    } // end of onCreate


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_login:
                if (!editText_userName.getText().toString().isEmpty()) {
                    if (!editText_password.getText().toString().isEmpty()) {
                        int login = myDBH.getLoginData(spinner_employeeType.getSelectedItem().toString(), editText_userName.getText().toString(), editText_password.getText().toString());

                        if (login == 1) {

                            // save login data
                            SharedPreferences.Editor editor = sharedPreferences_login.edit();

                            editor.putString(MY_PREF_KEY_LOGIN_TYPE, spinner_employeeType.getSelectedItem().toString());
                            editor.putString(MY_PREF_KEY_LOGIN_NAME, editText_userName.getText().toString());
                            editor.putString(MY_PREF_KEY_LOGIN_PASSWORD, editText_password.getText().toString());
                            editor.apply();

                            // go to the activity
                            startActivity(new Intent(getApplicationContext(), AdminPanel.class));

                        } else if (login == 2) {

                            // save login data
                            SharedPreferences.Editor editor = sharedPreferences_login.edit();

                            editor.putString(MY_PREF_KEY_LOGIN_TYPE, spinner_employeeType.getSelectedItem().toString());
                            editor.putString(MY_PREF_KEY_LOGIN_NAME, editText_userName.getText().toString());
                            editor.putString(MY_PREF_KEY_LOGIN_PASSWORD, editText_password.getText().toString());
                            editor.apply();

                            // pass intent with id value, first get id from db
                            String loginEmpId = myDBH.getIdForLoginEmployeePanel(spinner_employeeType.getSelectedItem().toString(), editText_userName.getText().toString(), editText_password.getText().toString());

                            Intent intent = new Intent(getApplicationContext(), EmployeeDetails.class);
                            intent.putExtra(AdminPanel.EXTRA_MESSAGE2, loginEmpId);

                            startActivity(intent);


                        } else {
                            Toast.makeText(getApplicationContext(), "User name, password or type not match.", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User name or password should not empty.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "User name or password should not empty.", Toast.LENGTH_SHORT).show();
                }

                // Clear the text box of userName and password
                editText_userName.setText(null);
                editText_password.setText(null);

                break;

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

}
