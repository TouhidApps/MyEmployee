package com.touhidapps.myemployee;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class ReportPanel extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    MyDBH myDBH;

    TextView textView_reportName;

    LinearLayout linearLayout_report_holder;
    private SQLiteDatabase readableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_panel);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        myDBH = new MyDBH(this);

        textView_reportName = (TextView) findViewById(R.id.textView_reportName);

        linearLayout_report_holder = (LinearLayout) findViewById(R.id.linearLayout_report_holder);


    } // end of onCreate

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.report_panel, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ascending) {

            // Create report table
            myReportGenerate("Report on Ascending Order", 0);

            // Create and save pdf report
            try {
                createPdfReport("Report on Ascending Order", 0);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_descending) {

            // Create report table
            myReportGenerate("Report on Descending Order", 1);

            // Create and save pdf report
            try {
                createPdfReport("Report on Descending Order", 1);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_department) {

            // Create report table
            myReportGenerate("Report Based on Department", 2);

            // Create and save pdf report
            try {
                createPdfReport("Report Based on Department", 2);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_designation) {

            // Create report table
            myReportGenerate("Report Based on Designation", 3);

            // Create and save pdf report
            try {
                createPdfReport("Report Based on Designation", 3);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_district) {

            // Create report table
            myReportGenerate("Report Based on District", 4);


            // Create and save pdf report
            try {
                createPdfReport("Report Based on District", 4);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        } else if (id == R.id.nav_joiningDate) {

            // Create report table
            myReportGenerate("Report Based on Joining Date", 5);

            // Create and save pdf report
            try {
                createPdfReport("Report Based on Joining Date", 5);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void myReportGenerate(String reportName, int reportSqlSequence) {

        // set title of the report
        textView_reportName.setText(reportName);


        Cursor cursorEmp = myDBH.getAnyReport(reportSqlSequence);


        // using table layout

        TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout_main);

        // clear previous views from linearLayout
        tableLayout.removeAllViews();

        TableRow tbrow0 = new TableRow(this);

        // Serial
        TextView tv0 = new TextView(this);
        tv0.setText("Sl.No. ");
        tv0.setTextColor(Color.BLUE);
        tv0.setGravity(Gravity.CENTER);
        tbrow0.addView(tv0);


        // Name
        TextView tv1 = new TextView(this);
        tv1.setText(" Name ");
        tv1.setTextColor(Color.BLUE);
        tv1.setGravity(Gravity.CENTER);
        tbrow0.addView(tv1);


        // Phone
        TextView tv2 = new TextView(this);
        tv2.setText(" Phone ");
        tv2.setTextColor(Color.BLUE);
        tv2.setGravity(Gravity.CENTER);
        tbrow0.addView(tv2);


//        // Type
//        TextView tv3 = new TextView(this);
//        tv3.setText(" Type ");
//        tv3.setTextColor(Color.BLUE);
//        tv3.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv3);


        // Address
        TextView tv4 = new TextView(this);
        tv4.setText(" Address ");
        tv4.setTextColor(Color.BLUE);
        tv4.setGravity(Gravity.CENTER);
        tbrow0.addView(tv4);


//        // District
//        TextView tv5 = new TextView(this);
//        tv5.setText(" District ");
//        tv5.setTextColor(Color.BLUE);
//        tv5.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv5);


        // Department
        TextView tv6 = new TextView(this);
        tv6.setText(" Department ");
        tv6.setTextColor(Color.BLUE);
        tv6.setGravity(Gravity.CENTER);
        tbrow0.addView(tv6);


        // Designation
        TextView tv7 = new TextView(this);
        tv7.setText(" Designation ");
        tv7.setTextColor(Color.BLUE);
        tv7.setGravity(Gravity.CENTER);
        tbrow0.addView(tv7);


//        // Education
//        TextView tv8 = new TextView(this);
//        tv8.setText(" Education ");
//        tv8.setTextColor(Color.BLUE);
//        tv8.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv8);
//
//
//        // Nominee
//        TextView tv9 = new TextView(this);
//        tv9.setText(" Nominee ");
//        tv9.setTextColor(Color.BLUE);
//        tv9.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv9);
//
//        // Job History
//        TextView tv10 = new TextView(this);
//        tv10.setText(" Job History ");
//        tv10.setTextColor(Color.BLUE);
//        tv10.setGravity(Gravity.CENTER);
//        tbrow0.addView(tv10);


        // Joining Date
        TextView tv11 = new TextView(this);
        tv11.setText(" Joining Date");
        tv11.setTextColor(Color.BLUE);
        tv11.setGravity(Gravity.CENTER);
        tbrow0.addView(tv11);


        tableLayout.addView(tbrow0);

        int j = 1;
        while (cursorEmp.moveToNext()) {
            int i = 0;

            TableRow tableRow = new TableRow(this);

            // Serial no.
            TextView textView_sn = new TextView(this);
            textView_sn.setText("" + j);// serial no is not related with database, it's just serial, here j is only for serial no
            j++;
            i++;
            textView_sn.setTextColor(Color.BLACK);
            textView_sn.setGravity(Gravity.CENTER);

            tableRow.addView(textView_sn);

            // Name
            TextView textView_name = new TextView(this);
            textView_name.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_name.setTextColor(Color.BLACK);
            textView_name.setGravity(Gravity.CENTER);

            tableRow.addView(textView_name);


            // Phone
            TextView textView_phone = new TextView(this);
            textView_phone.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_phone.setTextColor(Color.BLACK);
            textView_phone.setGravity(Gravity.CENTER);

            tableRow.addView(textView_phone);

//            // Type
//            TextView textView_type = new TextView(this);
//            textView_type.setText(" " + cursorEmp.getString(i) + " ");
            i++;
//            textView_type.setTextColor(Color.BLACK);
//            textView_type.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_type);

            // Address
            TextView textView_address = new TextView(this);
            textView_address.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_address.setTextColor(Color.BLACK);
            textView_address.setGravity(Gravity.CENTER);

            tableRow.addView(textView_address);

//            // District
//            TextView textView_district = new TextView(this);
//            textView_district.setText(" " + cursorEmp.getString(i) + " ");
            i++;
//            textView_district.setTextColor(Color.BLACK);
//            textView_district.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_district);


            // Department
            TextView textView_dept = new TextView(this);
            textView_dept.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_dept.setTextColor(Color.BLACK);
            textView_dept.setGravity(Gravity.CENTER);

            tableRow.addView(textView_dept);

            // Designation
            TextView textView_desig = new TextView(this);
            textView_desig.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_desig.setTextColor(Color.BLACK);
            textView_desig.setGravity(Gravity.CENTER);

            tableRow.addView(textView_desig);

//            // Edu
//            TextView textView_edu = new TextView(this);
//            textView_edu.setText(" " + cursorEmp.getString(i) + " ");
            i++;
//            textView_edu.setTextColor(Color.BLACK);
//            textView_edu.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_edu);
//
//            // Nominee
//            TextView textView_nominee = new TextView(this);
//            textView_nominee.setText(" " + cursorEmp.getString(i) + " ");
            i++;
//            textView_nominee.setTextColor(Color.BLACK);
//            textView_nominee.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_nominee);
//
//
//            // Job history
//            TextView textView_jobHist = new TextView(this);
//            textView_jobHist.setText(" " + cursorEmp.getString(i) + " ");
            i++;
//            textView_jobHist.setTextColor(Color.BLACK);
//            textView_jobHist.setGravity(Gravity.CENTER);
//
//            tableRow.addView(textView_jobHist);

            // Joining Date
            TextView textView_joiningDate = new TextView(this);
            textView_joiningDate.setText(" " + cursorEmp.getString(i) + " ");
            i++;
            textView_joiningDate.setTextColor(Color.BLACK);
            textView_joiningDate.setGravity(Gravity.CENTER);

            tableRow.addView(textView_joiningDate);


            // add the row on the table
            tableLayout.addView(tableRow);


        }


    }


    /**
     * Making pdf of the reports with itextpdf library to a new created folder
     */

    public void createPdfReport(String myReportName, int myReportSqlIndex) throws FileNotFoundException, DocumentException {

        String dir = Environment.getExternalStorageDirectory() + File.separator + "Touhid Apps PDF Report"; // here string is a folder name, which will be created on sd card
        File folder = new File(dir);
        folder.mkdirs();

        File file = new File(dir, myReportName + ".pdf");
        Document document = new Document(); // Create the document
        PdfWriter.getInstance(document, new FileOutputStream(file));
        document.open();

        Paragraph paragraphReportTitle = new Paragraph();
        paragraphReportTitle.add(myReportName + "\n\n");
        paragraphReportTitle.setAlignment(Element.ALIGN_CENTER);
        document.add(paragraphReportTitle);


        PdfPTable pdfPTable = new PdfPTable(8); // Each 8 cell will be a row
        pdfPTable.addCell("Serial");
        pdfPTable.addCell("Name");
        pdfPTable.addCell("Phone");
//        pdfPTable.addCell("Type");
//        pdfPTable.addCell("Address");
        pdfPTable.addCell("District");
        pdfPTable.addCell("Department");
        pdfPTable.addCell("Designation");
        pdfPTable.addCell("Education");
//        pdfPTable.addCell("Nominee");
//        pdfPTable.addCell("Job History");
        pdfPTable.addCell("Joining Date");


        MyDBH myDBH = new MyDBH(this);
        Cursor cursor = myDBH.getAnyReport(myReportSqlIndex);

        int k = 1;
        while (cursor.moveToNext()) {
            int i = 0;

        //    pdfPTable.addCell(cursor.getString(i)); // serial(id) from db, no need here, serial will be normal sequence
            i++;

            pdfPTable.addCell("" + k); // used serial, not from db
            k++;

            pdfPTable.addCell(cursor.getString(i)); // name
            i++;

            pdfPTable.addCell(cursor.getString(i)); // phone
            i++;

//            pdfPTable.addCell(cursor.getString(i)); // type
            i++;

//            pdfPTable.addCell(cursor.getString(i)); // address
            i++;

            pdfPTable.addCell(cursor.getString(i)); // district
            i++;

            pdfPTable.addCell(cursor.getString(i)); // department
            i++;

            pdfPTable.addCell(cursor.getString(i)); // designation
            i++;

            pdfPTable.addCell(cursor.getString(i)); // education
            i++;

//            pdfPTable.addCell(cursor.getString(i)); // nominee
            i++;

//            pdfPTable.addCell(cursor.getString(i)); // job history
            i++;

            pdfPTable.addCell(cursor.getString(i)); // joining date



        }


        //   closing connection
        cursor.close();


        document.add(pdfPTable);
        document.addCreationDate();
        document.close();


    }


}
