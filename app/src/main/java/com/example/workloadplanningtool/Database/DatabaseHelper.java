package com.example.workloadplanningtool.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.workloadplanningtool.Models.Assignment;
import com.example.workloadplanningtool.Models.SubTask;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "assignment.db";


    //Assignments Table
    public static final String TABLE_NAME = "assignment_table";
    public static final String COL1 = "ID";
    public static final String COL2 = "ASSIGNMENT_NAME";
    public static final String COL3 = "TYPE";
    public static final String COL4 = "DUE_DATE";
    public static final String COL5 = "PRIORITY";
    public static final String COL6 = "NOTES";
    public static final String COL7 = "COMPLETED";
    public static final String COL8 = "DATE_COMPARE";



    //SubTasks Table
    public static final String TABLE_NAME_SUBTASK = "subtasks_table";
    public static final String COL1_SUBTASK = "ID";
    public static final String COL2_SUBTASK = "ASSIGNMENT_NAME";
    public static final String COL3_SUBTASK = "DUE_DATE";
    public static final String COL4_SUBTASK = "PRIORITY";
    public static final String COL5_SUBTASK = "NOTES";
    public static final String COL6_SUBTASK = "ASSIGNMENT_ID";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ASSIGNMENT_NAME TEXT, " + "TYPE TEXT, " + "DUE_DATE TEXT, " + "PRIORITY TEXT, " +
                "NOTES TEXT, " + "COMPLETED INTEGER, " + "DATE_COMPARE TEXT) ;";

        String createTableSubTasks = "CREATE TABLE " + TABLE_NAME_SUBTASK + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ASSIGNMENT_NAME TEXT, " +  "DUE_DATE TEXT, " + "PRIORITY TEXT, " +
                "NOTES TEXT, " + "ASSIGNMENT_ID INTEGER REFERENCES " + TABLE_NAME + " (ID))";

        db.execSQL(createTable);
        db.execSQL(createTableSubTasks);

    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_SUBTASK);
        onCreate(db);

    }






    //Code to get list of assignments

    public Cursor getListOfAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }



    //Code to get list of assignments based on selected priority

    public Cursor getListOfPriorityAssignments(String priority) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE PRIORITY = '" + priority + "'", null);
        return data;
    }




    //Code to get list of assignments based on selected type

    public Cursor getListOfTypeAssignments(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME +
                " WHERE TYPE = '" + type + "'", null);
        return data;
    }



    //Getting assignments by date in ascending order

    public Cursor getAssignmentsByDate() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " ORDER BY date" + "(" + COL8 + ") " + "ASC" , null);
        return data;
    }








    //Get assignments by the current date

    public Cursor getTodayAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL8 + "<= date('now','localtime', '0 day')", null);
        return data;
    }


    //Get assignments that are within 30 days of the current month
    public Cursor getAssignmentsWithinMonth() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL7 + " = '0'" + " AND " + COL8 + "<= date('now','localtime', '30 day') " +
                "ORDER BY " + COL8 + " ASC" , null);
        return data;
    }











    //Data for Pie Chart

    public Cursor getGeneralAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = 'General'"
                + " AND " + "date_compare " + " BETWEEN " + "'2020-01-01'" + "and" + "'2020-12-12'"
                + " AND " + COL7 + " = '0'", null);
        return data;
    }


    public Cursor getAssessmentAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = 'Assessment'"
                + " AND " + "date_compare" + " BETWEEN " + "'2020-01-01'" + "and" + "'2020-12-12'"
                + " AND " + COL7 + " = '0'", null);
        return data;
    }

    public Cursor getCourseworkAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = 'Coursework'"
                + " AND " + "date_compare" + " BETWEEN " + "'2020-01-01'" + "and" + "'2020-12-12'"
                + " AND " + COL7 + " = '0'", null);
        return data;
    }

    public Cursor getExamAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = 'Exam'"
                + " AND " + "date_compare" + " BETWEEN " + "'2020-01-01'" + "and" + "'2020-12-12'"
                + " AND " + COL7 + " = '0'", null);
        return data;
    }

    public Cursor getOtherAssignments() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL3 + " = 'Other'"
                + " AND " + COL7 + " = '0'", null);
        return data;
    }










    //Code to get list of subtasks for a specific assignment

    public Cursor getListOfSubTasks(int assignmentId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME_SUBTASK + " WHERE " +
                COL6_SUBTASK + " = " + assignmentId, null);
        return data;
    }








    //Code to insert an assignment into database

    public boolean insertAssignment(Assignment a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2, a.getAssignmentName());
        contentValues.put(COL3, a.getType());
        contentValues.put(COL4, a.getDueDate());
        contentValues.put(COL5, a.getPriority());
        contentValues.put(COL6, a.getNotes());
        contentValues.put(COL7, a.getCompleted());
        contentValues.put(COL8, a.getDateCompare());

        long result = db.insert(TABLE_NAME, COL1, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }



    //Code to insert a sub task into database

    public boolean insertSubTask(SubTask st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COL2_SUBTASK, st.getAssignmentName());
        contentValues.put(COL3_SUBTASK, st.getDueDate());
        contentValues.put(COL4_SUBTASK, st.getPriority());
        contentValues.put(COL5_SUBTASK, st.getNotes());
        contentValues.put(COL6_SUBTASK, st.getAssignmentId());

        long result = db.insert(TABLE_NAME_SUBTASK, COL1_SUBTASK, contentValues);
        if (result == -1)
            return false;
        else
            return true;

    }










    //Code to update assignment

    public boolean updateAssignment(Assignment a) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL1, a.getId());
        contentValues.put(COL2, a.getAssignmentName());
        contentValues.put(COL3, a.getType());
        contentValues.put(COL4, a.getDueDate());
        contentValues.put(COL5, a.getPriority());
        contentValues.put(COL6, a.getNotes());
        contentValues.put(COL7, a.getCompleted());
        contentValues.put(COL8, a.getDateCompare());

        db.update(TABLE_NAME, contentValues, "ID = ? ", new String[] {Integer.toString(a.getId())});

        return true;
    }




    //Code to update sub task

    public boolean updateSubTask(SubTask st) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        contentValues.put(COL1, st.getId());
        contentValues.put(COL2, st.getAssignmentName());
        contentValues.put(COL4, st.getDueDate());
        contentValues.put(COL5, st.getPriority());
        contentValues.put(COL6, st.getNotes());

        db.update(TABLE_NAME_SUBTASK, contentValues, "ID = ? ", new String[] {Integer.toString(st.getId())});

        return true;
    }









    //Code to delete assignment

    public void deleteAssignment(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE " +
                COL1 + " = '" + id + "'";
        db.execSQL(query);
    }



    //Code to delete subtask

    public void deleteSubTask(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME_SUBTASK + " WHERE " +
                COL1_SUBTASK + " = '" + id + "'";
        db.execSQL(query);
    }


}
