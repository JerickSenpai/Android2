package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        // Setup RecyclerView
        recyclerViewAttendance = findViewById(R.id.recyclerViewAttendance);
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));

        // Fetch data from real source (e.g., local database or API)
        loadAttendanceData();
    }

    private void loadAttendanceData() {
        // TODO: Replace with real data source (API, database, etc.)
        // Example using a local database or remote API

        // attendanceList = fetchFromDatabase(); OR fetchFromApi();
        // For now, initialize as empty
        attendanceList = new java.util.ArrayList<>();

        attendanceAdapter = new AttendanceAdapter(attendanceList);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(AttendanceRecordActivity.this, DashboardActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
