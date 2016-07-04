package com.touhidapps.myemployee;

import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Touhid on 6/23/2016.
 */
public class MyDatePicker {
    public void clickToShowDatePicker(Context context, DatePickerDialog.OnDateSetListener dateSetListener, Calendar calendar) {
        new DatePickerDialog(context, dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    public void setDate(EditText editText, Calendar calendar, int year, int monthOfYear, int dayOfMonth) {
        calendar.set(calendar.YEAR, year);
        calendar.set(calendar.MONTH, monthOfYear);
        calendar.set(calendar.DAY_OF_MONTH, dayOfMonth);
//                Set Date
        String MyFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(MyFormat, Locale.US);
        editText.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
