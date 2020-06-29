package com.example.workloadplanningtool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.Fragments.DatePickerFragment;
import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Broadcaster.ReminderBroadcast;
import com.example.workloadplanningtool.Fragments.TimePickerFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;


public class AddAssignment extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener,
DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    EditText assignmentName, type, dueDate, priority, notes;

    String dateCompare;

    CheckBox completed;

    Switch reminderSwitch;

    int numCompleted;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    Button doneButton, setReminderButton;

    Calendar selecteddate;

    Assignment newAssignment;

    DatabaseHelper myDb = new DatabaseHelper(this);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_assignment);
        createNotificationChannel();



        assignmentName = findViewById(R.id.assignmentNameText);
        type = findViewById(R.id.typeText);
        dueDate = findViewById(R.id.dueDateText);
        priority = findViewById(R.id.priorityText);
        notes = findViewById(R.id.notesText);
        doneButton = findViewById(R.id.doneButton);
        completed = findViewById(R.id.checkCompleted);
        reminderSwitch = findViewById(R.id.reminderSwitch);
        setReminderButton = findViewById(R.id.setReminderButton);

        selecteddate = Calendar.getInstance();


        //Display popup menu when type edit text clicked

        type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(AddAssignment.this, v);
                popup.setOnMenuItemClickListener(AddAssignment.this);
                popup.inflate(R.menu.type_menu);
                popup.show();
            }
        });



        //Open datepickerdialog when duedate edit text clicked

        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddAssignment.this,
                        dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });


        //Set date when user has chosen a date

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String formattedMonth = "" + month;
                String formattedDayOfMonth = "" + day;

                if (month < 10) {
                    formattedMonth = "0" + month;
                }

                if (day < 10) {
                    formattedDayOfMonth = "0" + day;
                }

                String date = formattedDayOfMonth + "/" + formattedMonth + "/" + year;
                dueDate.setText(date);
                dateCompare = year + "-" + formattedMonth + "-" + formattedDayOfMonth;
                System.out.println(dateCompare);

              }
        };


        //open up popup menu when user clicks on priority

        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AddAssignment.this, view);
                popup.setOnMenuItemClickListener(AddAssignment.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });



        //If switch is checked, show set reminder button, otherwise don't show it

        reminderSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(reminderSwitch.isChecked()) {
                    setReminderButton.setVisibility(View.VISIBLE);
                } else {
                    setReminderButton.setVisibility(View.INVISIBLE);
                }
            }
        });


        //Shows both date and time pickers

        setReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");

            }


        });


        //When this is clicked, data from the above edit texts will be passed into database

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (completed.isChecked()) {
                    numCompleted = 1;
                } else {
                    numCompleted = 0;
                }

                newAssignment = new Assignment(assignmentName.getText().toString(),
                        type.getText().toString(), dueDate.getText().toString(),
                        priority.getText().toString(), notes.getText().toString(), numCompleted, dateCompare);

                if (myDb.insertAssignment(newAssignment)) {
                    Toast.makeText(AddAssignment.this, "Assignment successfully added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddAssignment.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AddAssignment.this, "Error: assignment not added", Toast.LENGTH_SHORT).show();
                }



            }
        });



        //Setting bottom navigation up
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);
        bottomNavigationView.getMenu().findItem(R.id.nav_new_assignment).setChecked(true);


    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        //Priority Menu
        switch (item.getItemId()) {
            case R.id.highPriority:
                priority.setText("High");
                return true;

            case R.id.mediumPriority:
                priority.setText("Medium");
                return true;

            case R.id.lowPriority:
                priority.setText("Low");
                return true;


        //Type Menu
            case R.id.typeGeneral:
                type.setText("General");
                return true;

            case R.id.typeAssessment:
                type.setText("Assessment");
                return true;

            case R.id.typeCoursework:
                type.setText("Coursework");
                return true;

            case R.id.typeExam:
                type.setText("Exam");
                return true;

            case R.id.typeOther:
                type.setText("Other");
                return true;

            default:
                return false;
        }


    }


    //Need to create a channel for each type of notification, only one type in this project

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ReminderChannel";
            String description = "Channel for reminders";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        // Set selected year, month and day in calendar object
        selecteddate.set(year, month, dayOfMonth);

        // Start Time dialog
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getSupportFragmentManager(), "time picker");
    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        selecteddate.set(Calendar.HOUR_OF_DAY, hourOfDay);
        selecteddate.set(Calendar.MINUTE, minute);

        setAlarm(selecteddate);
    }


    //When user sets alarm from date and time picker, a calendar object is sent to the alarm manager as a
    //pending intent, the ReminderBroadcast class handles the notification

    private void setAlarm(Calendar targetCal) {

        Intent intent = new Intent(AddAssignment.this, ReminderBroadcast.class);

        intent.putExtra("title", assignmentName.getText().toString());
        intent.putExtra("content", type.getText().toString());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(AddAssignment.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            Intent intent = new Intent(AddAssignment.this, MainActivity.class);
                            startActivity(intent);
                            break;

                        case R.id.nav_new_assignment:
                            Intent intent1 = new Intent(AddAssignment.this, AddAssignment.class);
                            startActivity(intent1);
                            break;

                        case R.id.nav_overview:
                            Intent intent2 = new Intent(AddAssignment.this, OverviewActivity.class);
                            startActivity(intent2);
                            break;
                    }

                    return true;
                }
            };

}








