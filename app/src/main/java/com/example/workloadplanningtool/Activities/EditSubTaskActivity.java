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
import android.widget.TextView;
import android.widget.Toast;

import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Models.SubTask;

import java.util.Calendar;

public class EditSubTaskActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    EditText editAssignmentName, editDueDate, editPriority, editNotes;

    TextView editId;

    Button editButton;

    DatabaseHelper myDb;

    private DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_sub_task);

        myDb = new DatabaseHelper(EditSubTaskActivity.this);

        editId = findViewById(R.id.editIdText);
        editAssignmentName = findViewById(R.id.editAssignmentNameText);
        editDueDate = findViewById(R.id.editDueDateText);
        editPriority = findViewById(R.id.editPriorityText);
        editNotes = findViewById(R.id.editNotesText);
        editButton = findViewById(R.id.editButton);

        Bundle extras = getIntent().getExtras();

        final SubTask st = (SubTask) extras.get("subtask");

        editId.setText(Integer.toString(st.getId()));
        editAssignmentName.setText(st.getAssignmentName());
        editDueDate.setText(st.getDueDate());
        editPriority.setText(st.getPriority());
        editNotes.setText(st.getNotes());

        final String id = editId.getText().toString();
        final int intId = Integer.parseInt(id);








        editDueDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        EditSubTaskActivity.this,
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
                editDueDate.setText(date);

            }
        };









        editPriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(EditSubTaskActivity.this, view);
                popup.setOnMenuItemClickListener(EditSubTaskActivity.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });









        editButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final SubTask updatedSubTask = new SubTask(intId, editAssignmentName.getText().toString(), editDueDate.getText().toString(), editPriority.getText().toString(), editNotes.getText().toString());
                        boolean updated = myDb.updateSubTask(updatedSubTask);
                        if (updated) {
                            Toast.makeText(EditSubTaskActivity.this, "Sub task updated"
                                    , Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditSubTaskActivity.this, MainActivity.class);
                            startActivity(intent);
                        }

                        else {
                            Toast.makeText(EditSubTaskActivity.this, "Error: Sub task not updated"
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

            default:
                return false;
        }
    }


}

