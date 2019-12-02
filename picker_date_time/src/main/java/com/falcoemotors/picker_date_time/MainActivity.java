package com.falcoemotors.picker_date_time;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnSet;
    FloatingActionButton floating;
    TextView txvTime, txvDate, display;
    DatePicker datePick;
    TimePicker timePick;

    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txvTime = findViewById(R.id.txvTime);
        txvDate = findViewById(R.id.txvDate);
        display = findViewById(R.id.txvDisplay);
        datePick = findViewById(R.id.datePick);
        timePick = findViewById(R.id.timePick);
        btnSet = findViewById(R.id.btnSet);
        floating = findViewById(R.id.floating);

        datePick.setVisibility(View.INVISIBLE);
        timePick.setVisibility(View.INVISIBLE);

        txvDate.setOnClickListener(this::onDate);
        txvTime.setOnClickListener(this::onTime);
        btnSet.setOnClickListener(this::onSet);
        floating.setOnClickListener(this::onClear);

        calendar = Calendar.getInstance();
    }

    private void onSet(View view) {

        if (datePick.isActivated())
            display.setText(
                    datePick.getDayOfMonth()+"-"+
                            (datePick.getMonth()+1)+"-"+
                            datePick.getYear()
            );

        if (timePick.isActivated())
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                display.setText(
                        timePick.getHour() + "-" + timePick.getMinute()
                );
            }

    }

    private void onClear(View view) {
        datePick.setVisibility(View.INVISIBLE);
        timePick.setVisibility(View.INVISIBLE);

        txvDate.setActivated(true);
        txvDate.setVisibility(View.VISIBLE);

        txvTime.setActivated(true);
        txvTime.setVisibility(View.VISIBLE);
    }

    private void onTime(View view) {

        /*txvDate.setActivated(false);
        txvDate.setVisibility(View.INVISIBLE);

        timePick.setVisibility(View.VISIBLE);
        timePick.setActivated(true);

        datePick.setActivated(false);*/

        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, timeSetListener, hour,minute,false);
        timePickerDialog.show();

    }

    private void onDate(View view) {

        /*txvTime.setActivated(false);
        txvTime.setVisibility(View.INVISIBLE);

        datePick.setVisibility(View.VISIBLE);
        datePick.setActivated(true);

        timePick.setActivated(false);*/

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,dateSetListener, year,month,day);
        datePickerDialog.show();
    }

    DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            display.setText(dayOfMonth + "-" + (month + 1) + "-" + year);
        }

    };

    TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            display.setText(hourOfDay +"-"+ minute);
        }
    };


}
