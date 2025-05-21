package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class AttendanceRecordActivity extends AppCompatActivity {

    RecyclerView recyclerViewAttendance;
    AttendanceAdapter attendanceAdapter;
    List<AttendanceModel> attendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbarAttendance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Initialize list with dummy data or real DB data
        attendanceList = new ArrayList<>();
        attendanceList.add(new AttendanceModel("08:00 AM", "05:00 PM", "May 20, 2025"));
        attendanceList.add(new AttendanceModel("08:15 AM", "05:05 PM", "May 21, 2025"));
        attendanceList.add(new AttendanceModel("08:05 AM", "05:02 PM", "May 22, 2025"));

        // Setup RecyclerView
        recyclerViewAttendance = findViewById(R.id.recyclerViewAttendance);
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));
        attendanceAdapter = new AttendanceAdapter(attendanceList);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(AttendanceRecordActivity.this, DashboardActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
