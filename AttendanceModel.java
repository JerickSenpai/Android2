package com.example.libraryapp;

// Model class to represent Attendance data
public class AttendanceModel {
    String date;
    String status;

    public AttendanceModel(String date, String status) {
        this.date = date;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }
}
