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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by User on 19.04.2018.
 */

public class EditProjectActivity extends AppCompatActivity {
    private Button startDateButton;
    private Button startTimeButton;
    private Button stopDateButton;
    private Button stopTimeButton;
    private Calendar calendarStart;
    private Calendar calendarStop;
    private SimpleDateFormat dateFormatter;
    private SimpleDateFormat timeFormatter;
    private String projectId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        if (getIntent().hasExtra("projectId")) {
            projectId = getIntent().getExtras().getString("projectId");
        }

        dateFormatter = new SimpleDateFormat("EE, dd.MM.yyyy", Locale.GERMAN);
        timeFormatter = new SimpleDateFormat("HH:mm");

        calendarStart = Calendar.getInstance();

        startDateButton = (Button) findViewById(R.id.startdate);
        startDateButton.setText(dateFormatter.format(calendarStart.getTime()));
        startDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(startDateButton, true);
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

        calendarStop = Calendar.getInstance();

        stopDateButton = (Button) findViewById(R.id.enddate);
        stopDateButton.setText(dateFormatter.format(calendarStop.getTime()));
        stopDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDatePickerDialog(stopDateButton, false);
            }
        });

        stopTimeButton = (Button) findViewById(R.id.endtime);
        calendarStop.set(Calendar.HOUR_OF_DAY, 0);
        calendarStop.set(Calendar.MINUTE, 0);
        calendarStop.set(Calendar.SECOND, 0);
        stopTimeButton.setText(timeFormatter.format(calendarStop.getTime()));
        stopTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimePickerDialog(stopTimeButton, false);
            }
        });
    }

    public void btnCancel_Click(View v) {
        showAlertDialogCanelEditProject();
    }

    public void btnSave_Click(View v) {
        saveProject();
    }

    public void saveProject() {
        if (calendarStart.getTime().after(calendarStop.getTime())) {
            Toast.makeText(this, "Startpunkt muss vor Endpunkt liegen", Toast.LENGTH_LONG).show();
        } else if (calendarStart.getTime().equals(calendarStop.getTime())) {
            Toast.makeText(this, "Startpunkt und Endpunkt müssen unterschiedlich sein", Toast.LENGTH_LONG).show();
        } else if (calendarStart.getTime().after(Calendar.getInstance().getTime()) || calendarStop.getTime().after(Calendar.getInstance().getTime())) {
            Toast.makeText(this, "Zeiten, die in der Zukunft liegen, können nicht hinzugefügt werden", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Zeit hinzugefügt", Toast.LENGTH_LONG).show();

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(projectId).child("timestamps").push().getKey();
            Timestamp timestamp = new Timestamp(id, calendarStart.getTime(), calendarStop.getTime());
            FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(projectId).child("timestamps").child(timestamp.getTimestampId()).setValue(timestamp);
            startActivity(new Intent(EditProjectActivity.this, OverviewActivity.class));
            finish();
        }
    }

    public void btnDelete_Click(View v) {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Projekt löschen")
                .setMessage("Bist du sicher, dass du das Projekt löschen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteProject();
                        startActivity(new Intent(EditProjectActivity.this, OverviewActivity.class));
                        finish();
                    }
                })
                .setNegativeButton("Nein", null)
                .show();
    }

    public void deleteProject() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(projectId).getKey();
        FirebaseDatabase.getInstance().getReference().child(user.getUid()).child(id).removeValue();
        startActivity(new Intent(EditProjectActivity.this, OverviewActivity.class));
        finish();
    }

    private void openDatePickerDialog(Button btn, boolean isStart) {
        DatePickerDialog datePickerDialog;
        if (isStart) {
            datePickerDialog = new DatePickerDialog(EditProjectActivity.this, new DateSetListener(btn, isStart), calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DATE));

        } else {
            datePickerDialog = new DatePickerDialog(EditProjectActivity.this, new DateSetListener(btn, isStart), calendarStop.get(Calendar.YEAR), calendarStop.get(Calendar.MONTH), calendarStop.get(Calendar.DATE));
        }
        datePickerDialog.show();
    }

    private void openTimePickerDialog(Button btn, boolean isStart) {
        TimePickerDialog timePickerDialog;
        if (isStart) {
            timePickerDialog = new TimePickerDialog(EditProjectActivity.this, new TimeSetListener(btn, isStart), calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE), true);
        } else {
            timePickerDialog = new TimePickerDialog(EditProjectActivity.this, new TimeSetListener(btn, isStart), calendarStop.get(Calendar.HOUR_OF_DAY), calendarStop.get(Calendar.MINUTE), true);
        }
        timePickerDialog.show();
    }

    class DateSetListener implements DatePickerDialog.OnDateSetListener {
        private Button btn;
        private boolean isStartTime;

        public DateSetListener(Button btn, boolean isStartTime) {
            this.btn = btn;
            this.isStartTime = isStartTime;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            if (isStartTime) {
                calendarStart.set(year, month, day);
                btn.setText(dateFormatter.format(calendarStart.getTime()));
            } else {
                calendarStop.set(year, month, day);
                btn.setText(dateFormatter.format(calendarStop.getTime()));
            }
        }
    }

    class TimeSetListener implements TimePickerDialog.OnTimeSetListener {
        private Button btn;
        private boolean isStart;

        public TimeSetListener(Button btn, boolean isStart) {
            this.btn = btn;
            this.isStart = isStart;
        }

        @Override
        public void onTimeSet(TimePicker timePicker, int hours, int minutes) {
            if (isStart) {
                calendarStart.set(Calendar.HOUR_OF_DAY, hours);
                calendarStart.set(Calendar.MINUTE, minutes);
                btn.setText(timeFormatter.format(calendarStart.getTime()));
            } else {
                calendarStop.set(Calendar.HOUR_OF_DAY, hours);
                calendarStop.set(Calendar.MINUTE, minutes);
                btn.setText(timeFormatter.format(calendarStop.getTime()));
            }
        }
    }

    //dialog if back button is pressed
    @Override
    public void onBackPressed() {
        showAlertDialogCanelEditProject();
    }

    public void showAlertDialogCanelEditProject() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Projektbearbeitung abbrechen")
                .setMessage("Bist du sicher, dass du die Bearbeitung des Projektes abbrechen möchtest?")
                .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(EditProjectActivity.this, OverviewActivity.class));
                        finish();
                    }

                })
                .setNegativeButton("Nein", null)
                .show();
    }

}
