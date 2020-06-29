package com.example.workloadplanningtool.Models;

import java.io.Serializable;

public class Assignment implements Serializable {
    private int id;
    private String assignmentName;
    private String type;
    private String dueDate;
    private String priority;
    private String notes;
    private int completed;
    private String dateCompare;
    private Boolean completed1 = false;
    private int daysUntil;

    public Assignment(String assignmentName, String type, String dueDate,
                      String priority, String notes, int completed, String dateCompare) {
        this.assignmentName = assignmentName;
        this.type = type;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.completed = completed;
        this.dateCompare = dateCompare;
    }




    public Assignment(int id, String assignmentName, String type, String dueDate,
                      String priority, String notes, int completed, String dateCompare) {
        this.id = id;
        this.assignmentName = assignmentName;
        this.type = type;
        this.dueDate = dueDate;
        this.priority = priority;
        this.notes = notes;
        this.completed = completed;
        this.dateCompare = dateCompare;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssignmentName() {
        return assignmentName;
    }

    public void setAssignmentName(String assignmentName) {
        this.assignmentName = assignmentName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public int getCompleted() {
        return completed;
    }

    public void setCompleted(int completed) {
        this.completed = completed;
    }

    public String getDateCompare() {
        return dateCompare;
    }

    public void setDateCompare(String dateCompare) {
        this.dateCompare = dateCompare;
    }

    public Boolean getCompleted1() {
        return completed1;
    }

    public void setCompleted1(Boolean completed1) {
        this.completed1 = completed1;
    }


    public int getDaysUntil() {
        return daysUntil;
    }

    public void setDaysUntil(int daysUntil) {
        this.daysUntil = daysUntil;
    }

    public String toString() {
        return assignmentName + "    " + dueDate;
    }



}
