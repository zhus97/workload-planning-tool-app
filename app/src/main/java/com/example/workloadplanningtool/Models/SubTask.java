package com.example.workloadplanningtool.Models;

import java.io.Serializable;

public class SubTask implements Serializable {
    private int id;
    private int assignmentId;
    private String assignmentName;
    private String dueDate;
    private String priority;
    private String notes;



    public SubTask(String assignmentName, String dueDate, String priority, String notes, int assignmentId) {
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.assignmentId = assignmentId;
    }




    public SubTask(int id, String assignmentName, String dueDate, String priority, String notes) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
    }


    public SubTask(int id, String assignmentName, String dueDate, String priority, String notes, int assignmentId) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.assignmentId = assignmentId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    

    public String toString() {
        return assignmentName + "    " + dueDate;
    }






}
