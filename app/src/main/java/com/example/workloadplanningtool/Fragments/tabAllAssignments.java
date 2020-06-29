package com.example.workloadplanningtool.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workloadplanningtool.Activities.DetailsActivity;
import com.example.workloadplanningtool.Adapters.AssignmentAdapter;
import com.example.workloadplanningtool.Database.DatabaseHelper;
import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class tabAllAssignments extends Fragment implements AssignmentAdapter.OnCardListener {

    private ArrayList<Assignment> assignments = new ArrayList<>();

    RecyclerView assignmentRecyclerView;
    AssignmentAdapter assignmentAdapter;


    public tabAllAssignments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_all_assignments, container, false);
        FragmentActivity c = getActivity();

        assignmentRecyclerView = view.findViewById(R.id.recycler_all_assignments);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        assignmentRecyclerView.setLayoutManager(layoutManager);

        assignmentAdapter = new AssignmentAdapter(c, assignments, this);
        assignmentRecyclerView.setAdapter(assignmentAdapter);

        DatabaseHelper db = new DatabaseHelper(c);
        Cursor data = db.getListOfAssignments();

        assignments.clear();

        //Fill assignments array list with all assignments using getListOfAssignments() method
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


        return view;

    }


    //When card is clicked, send user to details activity
    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("assignment", assignments.get(position));
        startActivity(intent);
    }
}




