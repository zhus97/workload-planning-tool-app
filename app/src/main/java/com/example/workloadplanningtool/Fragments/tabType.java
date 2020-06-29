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
public class tabType extends Fragment implements PopupMenu.OnMenuItemClickListener,
        AssignmentAdapter.OnCardListener {

    private ArrayList<Assignment> assignments = new ArrayList<>();

    RecyclerView assignmentRecyclerView;
    AssignmentAdapter assignmentAdapter;
    EditText chooseType;


    public tabType() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tab_type, container, false);

        final FragmentActivity c = getActivity();

        assignmentRecyclerView = view.findViewById(R.id.recycler_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        assignmentRecyclerView.setLayoutManager(layoutManager);

        assignmentAdapter = new AssignmentAdapter(c, assignments, this);
        assignmentRecyclerView.setAdapter(assignmentAdapter);



        chooseType = view.findViewById(R.id.setTypeText);
        chooseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(c, view);
                popup.setOnMenuItemClickListener(tabType.this);
                popup.inflate(R.menu.type_menu);
                popup.show();
            }
        });


        getType(chooseType.getText().toString());

        assignmentAdapter.notifyDataSetChanged();


        return view;

    }









    //User chooses what type of assignment they want to see
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.typeGeneral:
                chooseType.setText("General");
                getType(chooseType.getText().toString());
                return true;

            case R.id.typeAssessment:
                chooseType.setText("Assessment");
                getType(chooseType.getText().toString());
                return true;

            case R.id.typeCoursework:
                chooseType.setText("Coursework");
                getType(chooseType.getText().toString());
                return true;

            case R.id.typeExam:
                chooseType.setText("Exam");
                getType(chooseType.getText().toString());
                return true;

            case R.id.typeOther:
                chooseType.setText("Other");
                getType(chooseType.getText().toString());
                return true;

            default:
                return false;
        }
    }







    //Method to select data from database based on type
    private void getType(String typeFilter) {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        Cursor data = db.getListOfTypeAssignments(typeFilter);

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

    }

    @Override
    public void onCardClick(int position) {
        Intent intent = new Intent(getActivity(), DetailsActivity.class);
        intent.putExtra("assignment", assignments.get(position));
        startActivity(intent);
    }
}
