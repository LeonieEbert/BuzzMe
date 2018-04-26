package com.example.buzzme;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 19.04.2018.
 */

public class AddTimeActivity extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;
    Button startDateButton;
    Button startTimeButton;
    Button endDateButton;
    Button endTimeButton;
    Calendar calendarStart;
    Calendar calendarStop;
    SimpleDateFormat dateFormatter;
    SimpleDateFormat timeFormatter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_time);

        calendarStart = Calendar.getInstance();
        calendarStop = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("EE, dd.MM.yyyy", Locale.GERMAN);
        timeFormatter = new SimpleDateFormat("HH:mm");


        startDateButton = (Button) findViewById(R.id.startdate);
        startDateButton.setText(dateFormatter.format(calendarStart.getTime()));
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(startDateButton, true);
            }
        });

        endDateButton = (Button) findViewById(R.id.enddate);
        endDateButton.setText(dateFormatter.format(calendarStart.getTime()));
        endDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(endDateButton, false);
            }
        });

        startTimeButton = (Button) findViewById(R.id.starttime);
        calendarStart.set(Calendar.HOUR_OF_DAY, 0);
        calendarStart.set(Calendar.MINUTE, 0);
        calendarStart.set(Calendar.SECOND, 0);
        startTimeButton.setText(timeFormatter.format(calendarStart.getTime()));
        startTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerDialog(startTimeButton, true);
            }
        });

        endTimeButton = (Button) findViewById(R.id.endtime);
        calendarStop.set(Calendar.HOUR_OF_DAY, 0);
        calendarStop.set(Calendar.MINUTE, 0);
        calendarStop.set(Calendar.SECOND, 0);
        endTimeButton.setText(timeFormatter.format(calendarStop.getTime()));
        endTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerDialog(endTimeButton, false);
            }
        });
    }

    public void btnCancel_Click (View v) {
        AlertDialog.Builder cancelAddProjekt = new AlertDialog.Builder(AddTimeActivity.this);
        cancelAddProjekt.setMessage("Willst du das Hinzufügen einer Zeit wirklich abbrechen?");
        cancelAddProjekt.setCancelable(true);

        cancelAddProjekt.setPositiveButton(
                "JA",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        startActivity(new Intent(AddTimeActivity.this, OverviewActivity.class));
                    }
                });

        cancelAddProjekt.setNegativeButton(
                "NEIN",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = cancelAddProjekt.create();
        alert11.show();
    }

    public void btnSave_Click (View v) {
        saveProject();
    }

    public void saveProject(){
        System.out.println("Startzeit: " + calendarStart.getTime().toString());
        System.out.println("Stoppzeit: " + calendarStop.getTime().toString());
        if (calendarStart.after(calendarStop)) {
            Toast.makeText(this, "Startpunkt muss vor Endpunkt liegen",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Zeit hinzugefügt",Toast.LENGTH_LONG).show();
            startActivity(new Intent(AddTimeActivity.this, OverviewActivity.class));
        }
    }

    private void openDatePickerDialog(Button btn, boolean isStart){
        if (isStart){
            datePickerDialog = new DatePickerDialog(AddTimeActivity.this, new DateSetListener(btn, isStart), calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE));

        }else{
            datePickerDialog = new DatePickerDialog(AddTimeActivity.this, new DateSetListener(btn, isStart), calendarStop.get(Calendar.YEAR), calendarStop.get(Calendar.MONTH), calendarStop.get(Calendar.DATE));
        }
        datePickerDialog.show();
    }

    private void openTimePickerDialog(Button btn, boolean isStart){
        if (isStart){
            timePickerDialog = new TimePickerDialog(AddTimeActivity.this, new TimeSetListener(btn, isStart), calendarStart.get(Calendar.HOUR_OF_DAY),calendarStart.get(Calendar.MINUTE),true);
        }else{
            timePickerDialog = new TimePickerDialog(AddTimeActivity.this, new TimeSetListener(btn, isStart), calendarStop.get(Calendar.HOUR_OF_DAY),calendarStop.get(Calendar.MINUTE),true);
        }
        timePickerDialog.show();
    }

    class DateSetListener implements DatePickerDialog.OnDateSetListener{
        Button btn;
        boolean isStartTime;

        public DateSetListener(Button btn, boolean isStartTime) {
            this.btn = btn;
            this.isStartTime = isStartTime;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if (isStartTime){
                calendarStart.set(year, month, day);
                btn.setText(dateFormatter.format(calendarStart.getTime()));
            }else{
                calendarStop.set(year, month, day);
                btn.setText(dateFormatter.format(calendarStop.getTime()));
            }
        }
    }

    class TimeSetListener implements TimePickerDialog.OnTimeSetListener{
        Button btn;
        boolean isStart;

        public TimeSetListener(Button btn, boolean isStart) {
            this.btn = btn;
            this.isStart = isStart;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
            if (isStart){
                calendarStart.set(Calendar.HOUR_OF_DAY, hours);
                calendarStart.set(Calendar.MINUTE, minutes);
                btn.setText(timeFormatter.format(calendarStart.getTime()));
            }else{
                calendarStop.set(Calendar.HOUR_OF_DAY, hours);
                calendarStop.set(Calendar.MINUTE, minutes);
                btn.setText(timeFormatter.format(calendarStop.getTime()));
            }
        }
    }

}
