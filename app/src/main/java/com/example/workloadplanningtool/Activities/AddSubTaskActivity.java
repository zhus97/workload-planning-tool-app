package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Models.SubTask;

import java.util.Calendar;

public class AddSubTaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {



    EditText assignmentName, dueDate, priority, notes, assignmentId;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    DatabaseHelper myDb = new DatabaseHelper(this);

    Button subTaskDoneButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sub_task);

        subTaskDoneButton = findViewById(R.id.subTaskDoneButton);

        assignmentName = findViewById(R.id.subTaskNameText);
        dueDate = findViewById(R.id.subDueDateText);
        priority = findViewById(R.id.subPriorityText);
        notes = findViewById(R.id.subNotesText);
        assignmentId = findViewById(R.id.idEditText);

        Bundle extras = getIntent().getExtras();
        final Assignment a = (Assignment) extras.get("assignment");

        if (extras != null)
        {
            assignmentId.setText(Integer.toString(a.getId()));
        }




        dueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddSubTaskActivity.this,
                        dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });










        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                String date = day + "/" + month + "/" + year;
                dueDate.setText(date);

            }
        };







        priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(AddSubTaskActivity.this, view);
                popup.setOnMenuItemClickListener(AddSubTaskActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });









        subTaskDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v) {


                SubTask newSubTask = new SubTask(assignmentName.getText().toString(),
                        dueDate.getText().toString(),
                        priority.getText().toString(), notes.getText().toString(), Integer.parseInt(assignmentId.getText().toString()));

                if(myDb.insertSubTask(newSubTask)) {
                    Toast.makeText(AddSubTaskActivity.this, "Sub task successfully added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddSubTaskActivity.this, MainActivity.class);
                    startActivity(intent);
                }

                else {
                    Toast.makeText(AddSubTaskActivity.this, "Error: assignment not added", Toast.LENGTH_SHORT).show();
                }



            }
        });


    }






    @Override
    public boolean onMenuItemClick(MenuItem item) {
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

            default:
                return false;
        }
    }


}



