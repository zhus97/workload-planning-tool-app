package com.example.workloadplanningtool.Adapters;

import android.content.Context;
import android.graphics.Color;
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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class UpcomingEventAdapter extends RecyclerView.Adapter<UpcomingEventAdapter.ViewHolder> {

    private ArrayList<Assignment> assignments;
    Context mContext;
    private OnCardListener mOnCardListener;
    Date date1;
    Date date2;
    int date3;




    public UpcomingEventAdapter(@NonNull Context context, ArrayList<Assignment> assignments, OnCardListener onCardListener) {
        this.mContext = context;
        this.assignments = assignments;
        this.mOnCardListener = onCardListener;
    }






    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_event_layout,
                parent, false);
        ViewHolder holder = new ViewHolder(view, mOnCardListener);
        return holder;
    }







    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        System.out.println(date);



        //Get current date, date of assignment and get the difference in number of days
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date1 = format.parse(date);
            date2 = format.parse(assignments.get(position).getDateCompare());
            date3 = (int)((date2.getTime() - date1.getTime()) / (1000*60*60*24l));

        } catch (ParseException e) {
            e.printStackTrace();
        }


        //Set the amount of days left on assignment
        assignments.get(position).setDaysUntil(date3);
        String deadlineDay = String.valueOf(assignments.get(position).getDaysUntil());

        holder.eventName.setText(assignments.get(position).getAssignmentName());
        holder.dueDate.setText(assignments.get(position).getDueDate());
        holder.dateCompare.setText(assignments.get(position).getDateCompare());
        holder.daysUntil.setText(deadlineDay + " Days");

        if(assignments.get(position).getDaysUntil() == 1) {
            holder.daysUntil.setText(R.string.tomorrow_text);
            holder.daysUntil.setTextColor(Color.RED);
        }

        if(assignments.get(position).getDaysUntil() <= 0) {
            holder.daysUntil.setText(R.string.today_text);
            holder.daysUntil.setTextColor(Color.RED);
        }

        if(assignments.get(position).getCompleted() == 1) {

            assignments.get(position).setCompleted1(true);

            if (assignments.get(position).getCompleted1()) {
                holder.eventName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.dueDate.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        }


    }







    @Override
    public int getItemCount() {
        return assignments.size();
    }








    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView eventName;
        TextView dueDate;
        TextView dateCompare;
        TextView daysUntil;
        RelativeLayout assignmentLayout;

        OnCardListener onCardListener;


        public ViewHolder(@NonNull View itemView, OnCardListener onCardListener) {
            super(itemView);
            eventName = itemView.findViewById(R.id.event_name);
            dueDate = itemView.findViewById(R.id.event_due_date);
            dateCompare = itemView.findViewById(R.id.date_compare);
            daysUntil = itemView.findViewById(R.id.days_until);

            assignmentLayout = itemView.findViewById(R.id.upcoming_event);
            this.onCardListener = onCardListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onCardListener.onCardClick1(getAdapterPosition());
        }
    }





    public interface OnCardListener {
        void onCardClick1(int position);

    }
}
