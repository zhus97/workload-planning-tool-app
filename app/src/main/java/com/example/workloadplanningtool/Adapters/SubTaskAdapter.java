package com.example.workloadplanningtool.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.workloadplanningtool.R;
import com.example.workloadplanningtool.Models.SubTask;

import java.util.ArrayList;

public class SubTaskAdapter extends RecyclerView.Adapter<SubTaskAdapter.ViewHolder> {

    private ArrayList<SubTask> subTasks;
    Context mContext;
    private OnCardListener mOnCardListener;




    public SubTaskAdapter(@NonNull Context context, ArrayList<SubTask> subTasks, OnCardListener onCardListener) {
        this.mContext = context;
        this.subTasks = subTasks;
        this.mOnCardListener = onCardListener;
    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subtask_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view, mOnCardListener);
        return holder;
    }







    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.assignmentName.setText(subTasks.get(position).getAssignmentName());
        holder.dueDate.setText(subTasks.get(position).getDueDate());
    }







    @Override
    public int getItemCount() {
        return subTasks.size();
    }








    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView assignmentName;
        TextView dueDate;
        RelativeLayout subTaskLayout;

        OnCardListener onCardListener;


        public ViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            assignmentName = itemView.findViewById(R.id.subtask_name);
            dueDate = itemView.findViewById(R.id.subtask_due_date);
            subTaskLayout = itemView.findViewById(R.id.subtask_layout);
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
