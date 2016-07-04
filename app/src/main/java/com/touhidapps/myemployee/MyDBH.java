package com.touhidapps.myemployee;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Touhid on 6/15/2016.
 */
public class MyDBH extends SQLiteOpenHelper {

    long insert, delete;
    String a = "", b = "", c = "";
    String mySelectQuery;


    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "employee.db";

    public static final String TABLE_LOGIN = "login";
    private static final String TABLE_DEPARTMENT = "department";
    private static final String TABLE_DESIGNATION = "designation";

    public static final String COLUMN_ID = "id",
            COLUMN_EMPLOYEE_NAME = "name",
            COLUMN_EMPLOYEE_PHONE = "phone",
            COLUMN_EMPLOYEE_TYPE = "type",
            COLUMN_EMPLOYEE_ADDRESS = "address",
            COLUMN_EMPLOYEE_DISTRICT = "district",
            COLUMN_EMPLOYEE_DEPARTMENT = "department",
            COLUMN_EMPLOYEE_DESIGNATION = "designation",
            COLUMN_EMPLOYEE_EDUCATION = "education",
            COLUMN_EMPLOYEE_NOMINEES = "nominees",
            COLUMN_EMPLOYEE_JOB_HISTORY = "job_history",
            COLUMN_EMPLOYEE_JOINING_DATE = "joining_date",
            COLUMN_EMPLOYEE_ENABLE_DISABLE = "enable_disable",
            COLUMN_USERNAME = "username",
            COLUMN_PASSWORD = "passWord";


    private static final String SQL_CreateLoginTable = "CREATE TABLE " + TABLE_LOGIN + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EMPLOYEE_NAME + " TEXT, " +
            COLUMN_EMPLOYEE_PHONE + " TEXT, " +
            COLUMN_EMPLOYEE_TYPE + " TEXT, " +
            COLUMN_EMPLOYEE_ADDRESS + " TEXT, " +
            COLUMN_EMPLOYEE_DISTRICT + " TEXT, " +
            COLUMN_EMPLOYEE_DEPARTMENT + " TEXT, " +
            COLUMN_EMPLOYEE_DESIGNATION + " TEXT, " +
            COLUMN_EMPLOYEE_EDUCATION + " TEXT, " +
            COLUMN_EMPLOYEE_NOMINEES + " TEXT, " +
            COLUMN_EMPLOYEE_JOB_HISTORY + " TEXT, " +
            COLUMN_EMPLOYEE_JOINING_DATE + " TEXT, " +
            COLUMN_EMPLOYEE_ENABLE_DISABLE + " TEXT, " +
            COLUMN_USERNAME + " TEXT, " +
            COLUMN_PASSWORD + " TEXT)";


    private static final String SQL_CreateDepartmentTable = "CREATE TABLE " + TABLE_DEPARTMENT + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EMPLOYEE_DEPARTMENT + " TEXT UNIQUE, " +
            COLUMN_EMPLOYEE_ENABLE_DISABLE + " TEXT)";

    private static final String SQL_CreateDesignationTable = "CREATE TABLE " + TABLE_DESIGNATION + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_EMPLOYEE_DESIGNATION + " TEXT UNIQUE, " +
            COLUMN_EMPLOYEE_ENABLE_DISABLE + " TEXT)";


    public MyDBH(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        // create tables
        db.execSQL(SQL_CreateLoginTable);
        db.execSQL(SQL_CreateDepartmentTable);
        db.execSQL(SQL_CreateDesignationTable);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DEPARTMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIGNATION);

        onCreate(db); // After dropping db this will create new database
    }


    public boolean InsertDefaultLoginValue() {

        SQLiteDatabase dbb = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, "Md. Touhidul Islam");
        values.put(COLUMN_EMPLOYEE_PHONE, "01786 110 000");
        values.put(COLUMN_EMPLOYEE_TYPE, "Admin");
        values.put(COLUMN_EMPLOYEE_ADDRESS, "Dhaka");
        values.put(COLUMN_EMPLOYEE_DISTRICT, "Mymensingh");
        values.put(COLUMN_EMPLOYEE_DEPARTMENT, "Mobile App");
        values.put(COLUMN_EMPLOYEE_DESIGNATION, "Developer");
        values.put(COLUMN_EMPLOYEE_EDUCATION, "PG. Dip.");
        values.put(COLUMN_EMPLOYEE_NOMINEES, "Omuk");
        values.put(COLUMN_EMPLOYEE_JOB_HISTORY, "None");
        values.put(COLUMN_EMPLOYEE_JOINING_DATE, "6/6/2016");
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, "Enable");
        values.put(COLUMN_USERNAME, "admin");
        values.put(COLUMN_PASSWORD, "0000");


        //  Inserting Row
        insert = dbb.insert(TABLE_LOGIN, null, values);

        dbb.close();


        if (insert == -1) {
            return false;
        } else {
            return true;
        }


    }


    public String getAll() {

        String b = " ";


        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                b = b + cursor.getString(0) + cursor.getString(1) + cursor.getString(2) + cursor.getString(3) + cursor.getString(4) + cursor.getString(5) + "\n";
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning labels
        return b;
    }

    public int getLoginData(String adminEmp, String userName, String password) {


//        return 0 means error
//        return 1 means admin
//        return 2 means employee


        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_TYPE + "= " + "'" + adminEmp + "'" + " AND " + COLUMN_USERNAME + "='" + userName + "'" + " AND " + COLUMN_PASSWORD + "='" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getString(3);
                    b = cursor.getString(13);
                    c = cursor.getString(14);

                } while (cursor.moveToNext());
            }

            // closing connection
            cursor.close();

        }
        db.close();

        // returning

        if (a.equals(adminEmp) && b.equals(userName) && c.equals(password)) {
            if (a.equals("Admin")) {
                return 1;
            } else {
                return 2;
            }
        } else {
            return 0;
        }


    }


    public String getIdForLoginEmployeePanel(String adminEmp, String userName, String password) {


        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_TYPE + "= " + "'" + adminEmp + "'" + " AND " + COLUMN_USERNAME + "='" + userName + "'" + " AND " + COLUMN_PASSWORD + "='" + password + "'";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    a = cursor.getString(0);

                } while (cursor.moveToNext());
            }

            // closing connection
            cursor.close();

        }
        db.close();

        // returning

        return a;


    }


    public boolean saveNewEmployeeData(String empName, String empPhone, String empType, String empAddress,
                                       String empDistrict, String empDepartment, String empDesignation, String empEducation,
                                       String empNominees, String empJobHistory, String empJoiningDate, String empEnableDisable,
                                       String empUserName, String empPassword) {

        SQLiteDatabase dbe = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, empName);
        values.put(COLUMN_EMPLOYEE_PHONE, empPhone);
        values.put(COLUMN_EMPLOYEE_TYPE, empType);
        values.put(COLUMN_EMPLOYEE_ADDRESS, empAddress);
        values.put(COLUMN_EMPLOYEE_DISTRICT, empDistrict);
        values.put(COLUMN_EMPLOYEE_DEPARTMENT, empDepartment);
        values.put(COLUMN_EMPLOYEE_DESIGNATION, empDesignation);
        values.put(COLUMN_EMPLOYEE_EDUCATION, empEducation);
        values.put(COLUMN_EMPLOYEE_NOMINEES, empNominees);
        values.put(COLUMN_EMPLOYEE_JOB_HISTORY, empJobHistory);
        values.put(COLUMN_EMPLOYEE_JOINING_DATE, empJoiningDate);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, empEnableDisable);
        values.put(COLUMN_USERNAME, empUserName);
        values.put(COLUMN_PASSWORD, empPassword);


        //  Inserting Row
        insert = dbe.insert(TABLE_LOGIN, null, values);

        dbe.close();


        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getAllEmployeeList() {


        List<String> employeeIdName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_TYPE + " != 'Admin'" + " ORDER BY " + COLUMN_EMPLOYEE_NAME + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                employeeIdName.add(cursor.getString(0));//id
                employeeIdName.add(cursor.getString(1));//name
                employeeIdName.add(cursor.getString(2));//phone

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        // returning labels
        return employeeIdName;
    }


    // see profile, admin and employee both
    public List<String> seeMyProfile(String type, String name, String pass) {
        List<String> me = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_TYPE + " = '" + type + "' AND " + COLUMN_USERNAME + " = '" + name + "' AND " + COLUMN_PASSWORD + " = '" + pass + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                me.add(cursor.getString(0)); //id
                me.add(cursor.getString(1)); //name
                me.add(cursor.getString(2)); //phone
                me.add(cursor.getString(3)); //type
                me.add(cursor.getString(4)); //address
                me.add(cursor.getString(5)); //district
                me.add(cursor.getString(6)); //department
                me.add(cursor.getString(7)); //designation
                me.add(cursor.getString(8)); //edu
                me.add(cursor.getString(9)); //nominee
                me.add(cursor.getString(10)); //job history
                me.add(cursor.getString(11)); //joining date
                me.add(cursor.getString(12)); //enable disable
                me.add(cursor.getString(13)); //username
                me.add(cursor.getString(14)); //pass

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        // returning labels
        return me;
    }

    public long updateInfo(String[] id, String empName, String empPhone, String empType, String empAddress, String empDistrict, String empDepartment, String empDesignation, String empEducation,
                           String empNominees, String empJobHistory, String empJoiningDate, String empEnableDisable, String empUserName, String empPassword) {
        SQLiteDatabase dbu = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_NAME, empName);
        values.put(COLUMN_EMPLOYEE_PHONE, empPhone);
        values.put(COLUMN_EMPLOYEE_TYPE, empType);
        values.put(COLUMN_EMPLOYEE_ADDRESS, empAddress);
        values.put(COLUMN_EMPLOYEE_DISTRICT, empDistrict);
        values.put(COLUMN_EMPLOYEE_DEPARTMENT, empDepartment);
        values.put(COLUMN_EMPLOYEE_DESIGNATION, empDesignation);
        values.put(COLUMN_EMPLOYEE_EDUCATION, empEducation);
        values.put(COLUMN_EMPLOYEE_NOMINEES, empNominees);
        values.put(COLUMN_EMPLOYEE_JOB_HISTORY, empJobHistory);
        values.put(COLUMN_EMPLOYEE_JOINING_DATE, empJoiningDate);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, empEnableDisable);
        values.put(COLUMN_USERNAME, empUserName);
        values.put(COLUMN_PASSWORD, empPassword);


        //  Update
        long rowAffected = dbu.update(TABLE_LOGIN, values, COLUMN_ID + " LIKE ?", id);


        dbu.close();

        return rowAffected;

    }

    public List<String> getEmployeeAllData(String id) {
        List<String> emp = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_ID + " = '" + id + "'";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                emp.add(cursor.getString(0)); //id
                emp.add(cursor.getString(1)); //name
                emp.add(cursor.getString(2)); //phone
                emp.add(cursor.getString(3)); //type
                emp.add(cursor.getString(4)); //address
                emp.add(cursor.getString(5)); //district
                emp.add(cursor.getString(6)); //department
                emp.add(cursor.getString(7)); //designation
                emp.add(cursor.getString(8)); //edu
                emp.add(cursor.getString(9)); //nominee
                emp.add(cursor.getString(10)); //job history
                emp.add(cursor.getString(11)); //joining date
                emp.add(cursor.getString(12)); //enable disable
                emp.add(cursor.getString(13)); //username
                emp.add(cursor.getString(14)); //pass

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();


        // returning labels
        return emp;
    }

    public long deleteEmployee(String employeeId) {
        SQLiteDatabase dbd = this.getWritableDatabase(); // Open db as writable mode


        String[] convert = {employeeId};
        //  Inserting Row
        delete = dbd.delete(TABLE_LOGIN, COLUMN_ID + " LIKE ?", convert);

        dbd.close();


        return delete;
    }

    public List<String> getAllDepartment() { // to spinner
        List<String> departmentName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DEPARTMENT + " ORDER BY " + COLUMN_EMPLOYEE_DEPARTMENT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                departmentName.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning designation
        return departmentName;
    }


    public List<String> getAllDepartmentWithoutDisabled() { // to spinner
        List<String> departmentName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DEPARTMENT + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' ORDER BY " + COLUMN_EMPLOYEE_DEPARTMENT + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                departmentName.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning designation
        return departmentName;
    }

    public boolean saveDepartment(String dept, String eD) { // to department table
        SQLiteDatabase dbe = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_DEPARTMENT, dept);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, eD);


        //  Inserting Row
        insert = dbe.insert(TABLE_DEPARTMENT, null, values);

        dbe.close();


        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public List<String> getAllDesignationWithoutDisabled() { // to spinner
        List<String> designationName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DESIGNATION + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' ORDER BY " + COLUMN_EMPLOYEE_DESIGNATION + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                designationName.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning designation
        return designationName;
    }

    public List<String> getAllDesignation() { // to spinner
        List<String> designationName = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_DESIGNATION + " ORDER BY " + COLUMN_EMPLOYEE_DESIGNATION + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                designationName.add(cursor.getString(1));

            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning designation
        return designationName;
    }

    public long updateDepartment(String[] oldDeptName, String[] newDeptName, String enableDisable) {
        SQLiteDatabase dbu = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        String convert = newDeptName[0]; // convert to match data type
        values.put(COLUMN_EMPLOYEE_DEPARTMENT, convert);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, enableDisable);

        //  Update
        long rowAffected = dbu.update(TABLE_DEPARTMENT, values, COLUMN_EMPLOYEE_DEPARTMENT + " LIKE ?", oldDeptName); // department name is unique so no need id


        dbu.close();

        return rowAffected;


    }

    public long deleteDepartment(String departmentName) {
        SQLiteDatabase dbd = this.getWritableDatabase(); // Open db as writable mode


        String[] convert = {departmentName};
        //  Inserting Row
        delete = dbd.delete(TABLE_DEPARTMENT, COLUMN_EMPLOYEE_DEPARTMENT + " LIKE ?", convert);

        dbd.close();


        return delete;
    }

    public long updateDesignation(String[] oldDesigName, String[] newDesigName, String enableDisable) {
        SQLiteDatabase dbu = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        String convert = newDesigName[0]; // convert to match data type
        values.put(COLUMN_EMPLOYEE_DESIGNATION, convert);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, enableDisable);

        //  Update
        long rowAffected = dbu.update(TABLE_DESIGNATION, values, COLUMN_EMPLOYEE_DESIGNATION + " LIKE ?", oldDesigName); // designation name is unique so no need id


        dbu.close();

        return rowAffected;


    }

    public boolean saveDesignation(String desig, String eD) { // to desig table
        SQLiteDatabase dbd = this.getWritableDatabase(); // Open db as writable mode
        ContentValues values = new ContentValues();
        values.put(COLUMN_EMPLOYEE_DESIGNATION, desig);
        values.put(COLUMN_EMPLOYEE_ENABLE_DISABLE, eD);


        //  Inserting Row
        insert = dbd.insert(TABLE_DESIGNATION, null, values);

        dbd.close();


        if (insert == -1) {
            return false;
        } else {
            return true;
        }
    }

    public long deleteDesignation(String designationName) {
        SQLiteDatabase dbd = this.getWritableDatabase(); // Open db as writable mode


        String[] convert = {designationName};
        //  Inserting Row
        delete = dbd.delete(TABLE_DESIGNATION, COLUMN_EMPLOYEE_DESIGNATION + " LIKE ?", convert);

        dbd.close();


        return delete;
    }


    /**
     * Report: Ascending order, also used to make pdf
     */
    public Cursor getAnyReport(int sqlSequence) {

        /**
         * sqlSequence 0 means ascending
         * sqlSequence 1 means descending
         * sqlSequence 2 means department
         * sqlSequence 3 means designation
         * sqlSequence 4 means district
         * sqlSequence 5 means joining date
         */

        // Select All Query
        if (sqlSequence == 0) {
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_NAME + " ASC";
        } else if (sqlSequence == 1) {
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_NAME + " DESC";
        } else if (sqlSequence == 2) {
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_DEPARTMENT + " ASC";
        } else if (sqlSequence == 3) {
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_DESIGNATION + " ASC";
        } else if (sqlSequence == 4) {
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_DISTRICT + " DESC";
        } else if (sqlSequence == 5) {

            // this sql is not appropriate for date on SQLite, TODO more research for SQLite date
            mySelectQuery = "SELECT * FROM " + TABLE_LOGIN + " WHERE " + COLUMN_EMPLOYEE_ENABLE_DISABLE + " = 'Enable' AND " + COLUMN_EMPLOYEE_TYPE + "='Employee'" + " ORDER BY " + COLUMN_EMPLOYEE_JOINING_DATE + " DESC";
        }

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(mySelectQuery, null);

        // returning cursor
        return cursor;
    }






}

