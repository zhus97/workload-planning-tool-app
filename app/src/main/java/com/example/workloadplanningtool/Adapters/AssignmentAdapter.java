package com.example.workloadplanningtool.Adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.R;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {

    private ArrayList<Assignment> assignments;
    Context mContext;
    private OnCardListener mOnCardListener;

    public AssignmentAdapter(@NonNull Context context, ArrayList<Assignment> assignments, OnCardListener onCardListener) {
        this.mContext = context;
        this.assignments = assignments;
        this.mOnCardListener = onCardListener;
    }

    //Inflate the view using assignment_layout file along with mOnCardListener

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view, mOnCardListener);
        return holder;
    }

    //This method is used to fill in the recyclerview when the activity is loaded
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assignmentName.setText(assignments.get(position).getAssignmentName());
        holder.dueDate.setText(assignments.get(position).getDueDate());

        if(assignments.get(position).getCompleted() == 1) {

            assignments.get(position).setCompleted1(true);

            if (assignments.get(position).getCompleted1()) {
                holder.assignmentName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.dueDate.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }

    }


    @Override
    public int getItemCount() {
        return assignments.size();
    }








    //Initialise details that will be used in recyclerview

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView assignmentName;
        TextView dueDate;
        RelativeLayout assignmentLayout;

        OnCardListener onCardListener;


        public ViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.assignment_name);
            dueDate = itemView.findViewById(R.id.due_date);
            assignmentLayout = itemView.findViewById(R.id.assignment_layout);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick(getAdapterPosition());
        }
    }





    public interface OnCardListener {
        void onCardClick(int position);

    }
}
