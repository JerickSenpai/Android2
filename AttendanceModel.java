package com.example.android;

public class AttendanceModel {
    private String timeIn;
    private String timeOut;
    private String date;

    public AttendanceModel(String timeIn, String timeOut, String date) {
        this.timeIn = timeIn;
        this.timeOut = timeOut;
        this.date = date;
    }

    public String getTimeIn() { return timeIn; }
    public String getTimeOut() { return timeOut; }
    public String getDate() { return date; }
}
