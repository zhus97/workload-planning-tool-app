package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Models.SubTask;

public class SubTaskDetailsActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    TextView id;
    TextView assignmentName;
    TextView dueDate;
    TextView priority;
    TextView notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_task_details);

        myDb = new DatabaseHelper(SubTaskDetailsActivity.this);

        id = findViewById(R.id.idTextView);
        assignmentName = findViewById(R.id.assignmentNameTextView);
        dueDate = findViewById(R.id.dueDateTextView);
        priority = findViewById(R.id.priorityTextView);
        notes = findViewById(R.id.notesTextView);

        Button deleteButton = findViewById(R.id.deleteButton);
        Button editButton = findViewById(R.id.editButton);


        Bundle extras = getIntent().getExtras();
        final SubTask st = (SubTask) extras.get("subtask");


        id.setText(Integer.toString(st.getId()));
        assignmentName.setText(st.getAssignmentName());
        dueDate.setText("Due date: " + st.getDueDate());
        priority.setText("Priority: " + st.getPriority());
        notes.setText("Notes: " + st.getNotes());






        //Code to delete assignment from database and list view
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialog();
                    }
                });




        //Code to send user to EditSubTaskActivity when editButton clicked
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SubTaskDetailsActivity.this, EditSubTaskActivity.class);
                intent.putExtra("subtask", st);

                startActivity(intent);
            }

        });
    }












    //Method to show an alert dialog when user clicks delete button

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SubTaskDetailsActivity.this);

        builder.setTitle("Delete Sub Task")

                .setMessage("Do you want to delete this sub task?")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if cancel button clicked, do nothing
                    }

                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDb.deleteSubTask(id.getText().toString());

                        Toast.makeText(SubTaskDetailsActivity.this, assignmentName.getText() + " deleted"
                                , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(SubTaskDetailsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }
}
