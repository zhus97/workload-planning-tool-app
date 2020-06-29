package com.example.workloadplanningtool.Fragments;


import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupMenu;

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
public class tabPriority extends Fragment implements PopupMenu.OnMenuItemClickListener,
        AssignmentAdapter.OnCardListener {

    private ArrayList<Assignment> assignments = new ArrayList<>();

    RecyclerView assignmentRecyclerView;
    AssignmentAdapter assignmentAdapter;
    EditText choosePriority;


    public tabPriority() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_priority, container, false);

        final FragmentActivity c = getActivity();

        assignmentRecyclerView = view.findViewById(R.id.recycler_priority);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        assignmentRecyclerView.setLayoutManager(layoutManager);

        assignmentAdapter = new AssignmentAdapter(c, assignments, this);
        assignmentRecyclerView.setAdapter(assignmentAdapter);


        assignments.clear();

        choosePriority = view.findViewById(R.id.setPriorityText);
        choosePriority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(c, view);
                popup.setOnMenuItemClickListener(tabPriority.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });


        getPriority(choosePriority.getText().toString());

        return view;

    }










    //User chooses what priority they want to see
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.highPriority:
                choosePriority.setText("High");
                getPriority("High");
                return true;

            case R.id.mediumPriority:
                choosePriority.setText("Medium");
                getPriority("Medium");
                return true;

            case R.id.lowPriority:
                choosePriority.setText("Low");
                getPriority("Low");
                return true;


        }

        return true;
    }












    //Method to select data based on priority
    private void getPriority(String priorityFilter) {

        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor data = db.getListOfPriorityAssignments(priorityFilter);

        assignments.clear();

        while (data.moveToNext()) {
            int id = data.getInt(0);
            String assignmentName = data.getString(1);
            String module = data.getString(2);
            String dueDate = data.getString(3);
            String priority = data.getString(4);
            String notes = data.getString(5);
            int completed = data.getInt(6);
            String dateCompare = data.getString(7);


            assignments.add(new Assignment(id, assignmentName, module,
                    dueDate, priority, notes, completed, dateCompare));
        }

        assignmentAdapter.notifyDataSetChanged();



    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("assignment", assignments.get(position));
        startActivity(intent);
    }
}
