package com.example.workloadplanningtool.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;

import com.example.workloadplanningtool.Adapters.AssignmentAdapter;
import com.example.workloadplanningtool.Adapters.UpcomingEventAdapter;
import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class OverviewActivity extends AppCompatActivity implements AssignmentAdapter.OnCardListener, UpcomingEventAdapter.OnCardListener {

    private ArrayList<Assignment> assignments = new ArrayList<>();
    private ArrayList<Assignment> upcomingAssignments = new ArrayList<>();

    private ArrayList<Assignment> generalAssignments = new ArrayList<>();
    private ArrayList<Assignment> assessmentAssignments = new ArrayList<>();
    private ArrayList<Assignment> courseworkAssignments = new ArrayList<>();
    private ArrayList<Assignment> examAssignments = new ArrayList<>();
    private ArrayList<Assignment> otherAssignments = new ArrayList<>();




    RecyclerView assignmentRecyclerView;
    RecyclerView upcomingRecyclerView;
    AssignmentAdapter assignmentAdapter;
    UpcomingEventAdapter upcomingAdapter;

    PieChart pieChart;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        assignmentRecyclerView = findViewById(R.id.today_schedule_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(OverviewActivity.this);
        assignmentRecyclerView.setLayoutManager(layoutManager);

        assignmentAdapter = new AssignmentAdapter(OverviewActivity.this, assignments, this);
        assignmentRecyclerView.setAdapter(assignmentAdapter);

        DatabaseHelper db = new DatabaseHelper(OverviewActivity.this);
        Cursor data = db.getTodayAssignments();

        assignments.clear();

        while (data.moveToNext()) {
            int id = data.getInt(0);
            String assignmentName = data.getString(1);
            String type = data.getString(2);
            String dueDate = data.getString(3);
            String priority = data.getString(4);
            String notes = data.getString(5);
            int completed = data.getInt(6);
            String dateCompare = data.getString(7);


            assignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }

        assignmentAdapter.notifyDataSetChanged();









        upcomingRecyclerView = findViewById(R.id.upcoming_recycle);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(OverviewActivity.this);
        upcomingRecyclerView.setLayoutManager(layoutManager1);

        upcomingAdapter = new UpcomingEventAdapter(OverviewActivity.this, upcomingAssignments, this);
        upcomingRecyclerView.setAdapter(upcomingAdapter);

        Cursor data1 = db.getAssignmentsWithinMonth();

        upcomingAssignments.clear();

        while (data1.moveToNext()) {
            int id = data1.getInt(0);
            String assignmentName = data1.getString(1);
            String type = data1.getString(2);
            String dueDate = data1.getString(3);
            String priority = data1.getString(4);
            String notes = data1.getString(5);
            int completed = data1.getInt(6);
            String dateCompare = data1.getString(7);

            upcomingAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));

        }


        upcomingAdapter.notifyDataSetChanged();











        /*
            Code that represents data of pie chart where it uses the DatabaseHelper class to select
            data according to the type of assignment (General, Assessment, Coursework, Exam, Other)
         */

        Cursor data2 = db.getGeneralAssignments();

        generalAssignments.clear();

        while (data2.moveToNext()) {
            int id = data2.getInt(0);
            String assignmentName = data2.getString(1);
            String type = data2.getString(2);
            String dueDate = data2.getString(3);
            String priority = data2.getString(4);
            String notes = data2.getString(5);
            int completed = data2.getInt(6);
            String dateCompare = data2.getString(7);



            generalAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }






        Cursor data3 = db.getAssessmentAssignments();

        assessmentAssignments.clear();

        while (data3.moveToNext()) {
            int id = data3.getInt(0);
            String assignmentName = data3.getString(1);
            String type = data3.getString(2);
            String dueDate = data3.getString(3);
            String priority = data3.getString(4);
            String notes = data3.getString(5);
            int completed = data3.getInt(6);
            String dateCompare = data3.getString(7);



            assessmentAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }




        Cursor data4 = db.getCourseworkAssignments();

        courseworkAssignments.clear();

        while (data4.moveToNext()) {
            int id = data4.getInt(0);
            String assignmentName = data4.getString(1);
            String type = data4.getString(2);
            String dueDate = data4.getString(3);
            String priority = data4.getString(4);
            String notes = data4.getString(5);
            int completed = data4.getInt(6);
            String dateCompare = data4.getString(7);



            courseworkAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }


        Cursor data5 = db.getExamAssignments();

        examAssignments.clear();

        while (data5.moveToNext()) {
            int id = data5.getInt(0);
            String assignmentName = data5.getString(1);
            String type = data5.getString(2);
            String dueDate = data5.getString(3);
            String priority = data5.getString(4);
            String notes = data5.getString(5);
            int completed = data5.getInt(6);
            String dateCompare = data5.getString(7);



            examAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }







        Cursor data6 = db.getOtherAssignments();

        otherAssignments.clear();

        while (data6.moveToNext()) {
            int id = data6.getInt(0);
            String assignmentName = data6.getString(1);
            String type = data6.getString(2);
            String dueDate = data6.getString(3);
            String priority = data6.getString(4);
            String notes = data6.getString(5);
            int completed = data6.getInt(6);
            String dateCompare = data6.getString(7);



            otherAssignments.add(new Assignment(id, assignmentName, type,
                    dueDate, priority, notes, completed, dateCompare));
        }





        //Run pie chart method
        setupPieChart();



    }


    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("assignment", assignments.get(position));
        startActivity(intent);
    }



    @Override
    public void onCardClick1(int position) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra("assignment", upcomingAssignments.get(position));
        startActivity(intent);
    }





    //Implemented a library to display a pie chart

    public void setupPieChart() {
        pieChart = findViewById(R.id.pie_chart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(61f);

        ArrayList<PieEntry> yValues = new ArrayList<>();


        //Filling above array list with the size of each "type" array.

        yValues.add(new PieEntry(generalAssignments.size(), "General"));
        yValues.add(new PieEntry(assessmentAssignments.size(), "Assessment"));
        yValues.add(new PieEntry(courseworkAssignments.size(), "Coursework"));
        yValues.add(new PieEntry(examAssignments.size(), "Exam"));
        yValues.add(new PieEntry(otherAssignments.size(), "Other"));

        PieDataSet pieDataSet = new PieDataSet(yValues, "Types");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(10f);
        pieData.setValueTextColor(Color.WHITE);

        pieChart.setData(pieData);
    }



    





}
