package com.example.libraryapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

// This is the Attendance Record screen (Fancy Cards)
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

        // Dummy Attendance Data (PLACEHOLDER for real database later)
        attendanceList = new ArrayList<>();
        attendanceList.add(new AttendanceModel("April 25, 2025", "Present"));
        attendanceList.add(new AttendanceModel("April 26, 2025", "Present"));
        attendanceList.add(new AttendanceModel("April 27, 2025", "Absent"));
        attendanceList.add(new AttendanceModel("April 28, 2025", "Present"));

        // Set Adapter
        attendanceAdapter = new AttendanceAdapter(attendanceList);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
    }

    // Back button in toolbar returns to Dashboard
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
