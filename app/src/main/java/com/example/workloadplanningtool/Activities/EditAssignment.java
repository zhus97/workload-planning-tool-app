package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;

import java.util.Calendar;

public class EditAssignment extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    EditText editAssignmentName, editType, editDueDate, editPriority, editNotes;

    String dateCompare;

    CheckBox completed;

    int numCompleted;

    TextView editId;

    Button editButton;

    DatabaseHelper myDb;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_assignment);

        myDb = new DatabaseHelper(EditAssignment.this);

        editId = findViewById(R.id.editIdText);
        editAssignmentName = findViewById(R.id.editAssignmentNameText);
        editType = findViewById(R.id.editTypeText);
        editDueDate = findViewById(R.id.editDueDateText);
        editPriority = findViewById(R.id.editPriorityText);
        editNotes = findViewById(R.id.editNotesText);
        editButton = findViewById(R.id.editButton);
        completed = findViewById(R.id.checkCompleted);

        Bundle extras = getIntent().getExtras();

        final Assignment a = (Assignment) extras.get("assignment");

        editId.setText(Integer.toString(a.getId()));
        editAssignmentName.setText(a.getAssignmentName());
        editType.setText(a.getType());
        editDueDate.setText(a.getDueDate());
        editPriority.setText(a.getPriority());
        editNotes.setText(a.getNotes());

        final String id = editId.getText().toString();
        final int intId = Integer.parseInt(id);


        //If the assignment is completed, set default state of check box to checked
        if(a.getCompleted() == 1) {
            completed.setChecked(true);
        }









        editType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(EditAssignment.this, v);
                popup.setOnMenuItemClickListener(EditAssignment.this);
                popup.inflate(R.menu.type_menu);
                popup.show();
            }
        });








        editDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditAssignment.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        dateSetListener,
                        year, month, day);
                dialog.show();
            }
        });

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
                editDueDate.setText(date);
                dateCompare = year + "-" + formattedMonth + "-" + formattedDayOfMonth;
                System.out.println(dateCompare);

            }
        };









        editPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(EditAssignment.this, view);
                popup.setOnMenuItemClickListener(EditAssignment.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });









        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(completed.isChecked()) {
                            numCompleted = 1;
                        } else {
                            numCompleted = 0;
                        }

                        final Assignment updatedAssignment = new Assignment(intId, editAssignmentName.getText().toString(),
                                editType.getText().toString(), editDueDate.getText().toString(),
                                editPriority.getText().toString(), editNotes.getText().toString(), numCompleted, dateCompare);
                        boolean updated = myDb.updateAssignment(updatedAssignment);
                        if (updated) {
                            Toast.makeText(EditAssignment.this, "Assignment updated"
                                    , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditAssignment.this, MainActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Toast.makeText(EditAssignment.this, "Error: Assignment not updated"
                                    , Toast.LENGTH_SHORT).show();
                        }

                    }


                });
    }










    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.highPriority:
                editPriority.setText("High");
                return true;

            case R.id.mediumPriority:
                editPriority.setText("Medium");
                return true;

            case R.id.lowPriority:
                editPriority.setText("Low");
                return true;




            //Type Menu
            case R.id.typeGeneral:
                editType.setText("General");
                return true;

            case R.id.typeAssessment:
                editType.setText("Assessment");
                return true;

            case R.id.typeCoursework:
                editType.setText("Coursework");
                return true;

            case R.id.typeExam:
                editType.setText("Exam");
                return true;

            case R.id.typeOther:
                editType.setText("Other");
                return true;

            default:
                return false;
        }
    }









}
