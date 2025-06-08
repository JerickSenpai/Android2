package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AttendanceRecordActivity extends AppCompatActivity {

    private static final String TAG = "AttendanceRecord";

    private RecyclerView recyclerViewAttendance;
    private AttendanceAdapter attendanceAdapter;
    private List<AttendanceModel> attendanceList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ProgressBar progressBar;
    private LinearLayout tvNoData;

    // QR Footer components
    private Button btnGenerateQR;
    private Button btnScanQR;

    // Placeholder data constants
    private static final Random random = new Random();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_record);

        // Initialize views
        initializeViews();

        // Setup RecyclerView
        setupRecyclerView();

        // Setup QR footer
        setupQRFooter();

        // Load attendance data
        loadAttendanceData();
    }

    private void initializeViews() {
        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbarAttendance);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Attendance Records");
        }

        // Initialize main views
        recyclerViewAttendance = findViewById(R.id.recyclerViewAttendance);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        progressBar = findViewById(R.id.progressBar);
        tvNoData = findViewById(R.id.tvNoData);

        // Initialize QR footer buttons
        btnGenerateQR = findViewById(R.id.btnGenerateQR);
        btnScanQR = findViewById(R.id.btnScanQR);

        // Setup swipe refresh
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadAttendanceData();
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewAttendance.setLayoutManager(new LinearLayoutManager(this));
        attendanceList = new ArrayList<>();
        attendanceAdapter = new AttendanceAdapter(attendanceList, this);
        recyclerViewAttendance.setAdapter(attendanceAdapter);
    }

    private void setupQRFooter() {
        // Generate QR Button - Opens QR Generator for manual student ID input
        btnGenerateQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AttendanceRecordActivity.this, QRGeneratorActivity.class);
                startActivity(intent);
            }
        });

        // Scan QR Button - Opens QR Scanner to scan student QR codes
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: Implement QR Scanner Activity
                // Intent intent = new Intent(AttendanceRecordActivity.this, QRScannerActivity.class);
                // startActivity(intent);
                Toast.makeText(AttendanceRecordActivity.this,
                        "QR Scanner feature coming soon!",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadAttendanceData() {
        showLoading(true);

        SharedPrefManager prefManager = SharedPrefManager.getInstance(this);
        String studentIdStr = prefManager.getStudentId();

        Integer studentId = null;
        if (studentIdStr != null && !studentIdStr.isEmpty()) {
            try {
                studentId = Integer.parseInt(studentIdStr);
            } catch (NumberFormatException e) {
                Log.e(TAG, "Invalid student ID format: " + studentIdStr);
            }
        }

        AttendanceApiService.fetchAttendanceRecords(studentId, new AttendanceApiService.AttendanceCallback() {
            @Override
            public void onSuccess(List<AttendanceModel> attendanceListResult) {
                showLoading(false);
                attendanceList.clear();
                if (attendanceListResult != null && !attendanceListResult.isEmpty()) {
                    attendanceList.addAll(attendanceListResult);
                    attendanceAdapter.notifyDataSetChanged();
                    showNoDataMessage(false);
                } else {
                    showNoDataMessage(true);
                }
            }

            @Override
            public void onError(String error) {
                showLoading(false);
                showNoDataMessage(true);
                Toast.makeText(AttendanceRecordActivity.this, "Error: " + error, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void showLoading(boolean show) {
        if (progressBar != null) {
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }

    private void showNoDataMessage(boolean show) {
        if (tvNoData != null) {
            tvNoData.setVisibility(show ? View.VISIBLE : View.GONE);
        }
        if (recyclerViewAttendance != null) {
            recyclerViewAttendance.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // Back arrow click: close this activity
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
