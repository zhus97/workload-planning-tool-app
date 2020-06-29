package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.workloadplanningtool.Adapters.SubTaskAdapter;
import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Models.SubTask;

import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity implements SubTaskAdapter.OnCardListener {

    DatabaseHelper myDb;
    TextView id;
    TextView assignmentName;
    TextView type;
    TextView dueDate;
    TextView priority;
    TextView notes;

    private ArrayList<SubTask> subTasks = new ArrayList<>();

    RecyclerView subTaskRecyclerView;
    SubTaskAdapter subTaskAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        myDb = new DatabaseHelper(DetailsActivity.this);

        id = findViewById(R.id.idTextView);
        assignmentName = findViewById(R.id.assignmentNameTextView);
        type = findViewById(R.id.typeTextView);
        dueDate = findViewById(R.id.dueDateTextView);
        priority = findViewById(R.id.priorityTextView);
        notes = findViewById(R.id.notesTextView);

        Button deleteButton = findViewById(R.id.deleteButton);
        Button editButton = findViewById(R.id.editButton);
        Button addSubTask = findViewById(R.id.subTaskButton);





        Bundle extras = getIntent().getExtras();
        final Assignment a = (Assignment) extras.get("assignment");



        //Code to set the text fields in the details activity as the assignments' (assignment user has clicked on) corresponding details

        id.setText(Integer.toString(a.getId()));
        assignmentName.setText(a.getAssignmentName());
        type.setText("Type: " + a.getType());
        dueDate.setText("Due date: " + a.getDueDate());
        priority.setText("Priority: " + a.getPriority());
        notes.setText("Notes: " + a.getNotes());







        //Code to delete assignment from database and list view
        deleteButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showAlertDialog();
                    }
                });








        //Code to send user to editAssignment Activity when editButton clicked
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, EditAssignment.class);
                intent.putExtra("assignment", a);

                startActivity(intent);
            }

        });











        //Code to send user to add sub task activity
        addSubTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsActivity.this, AddSubTaskActivity.class);
                intent.putExtra("assignment", a);

                startActivity(intent);
            }

        });












        //Setting up the subtask recycler view

        subTaskRecyclerView = findViewById(R.id.subTaskRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        subTaskRecyclerView.setLayoutManager(layoutManager);

        subTaskAdapter = new SubTaskAdapter(getApplicationContext(), subTasks, this);
        subTaskRecyclerView.setAdapter(subTaskAdapter);



        DatabaseHelper db = new DatabaseHelper(DetailsActivity.this);
        Cursor data = db.getListOfSubTasks(a.getId());

        subTasks.clear();

        while (data.moveToNext()) {
            int id = data.getInt(0);
            String assignmentName = data.getString(1);
            String dueDate = data.getString(2);
            String priority = data.getString(3);
            String notes = data.getString(4);

            subTasks.add(new SubTask(id, assignmentName,
                    dueDate, priority, notes));
        }

        subTaskAdapter.notifyDataSetChanged();


    }






    //Method to show an alert dialog when user clicks delete button

    public void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailsActivity.this);

        builder.setTitle("Delete Assignment")

                .setMessage("Do you want to delete this assignment?")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //if cancel button clicked, do nothing
                    }

                })
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDb.deleteAssignment(id.getText().toString());

                        Toast.makeText(DetailsActivity.this, assignmentName.getText() + " deleted"
                                , Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(DetailsActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        builder.create().show();
    }


    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(DetailsActivity.this, SubTaskDetailsActivity.class);
        intent.putExtra("subtask", subTasks.get(position));
        startActivity(intent);
    }
}

