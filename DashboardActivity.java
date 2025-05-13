    package com.example.android;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.widget.Button;
    import android.widget.Toast;
    import com.example.android.DuesActivity;
    import com.example.android.HistoryActivity;
    import com.example.android.ScanQRActivity;
    import com.example.android.AttendanceRecordActivity;


    import androidx.appcompat.app.AppCompatActivity;
    import androidx.appcompat.widget.Toolbar;

    public class DashboardActivity extends AppCompatActivity {

        Button btnDue, btnHistory, btnScanQR, btnAttendance;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dashboard);

            // Set up custom toolbar with STI title and menu
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("STI"); // Center STI text
            getSupportActionBar().setDisplayShowTitleEnabled(true);

            // Initialize buttons
            btnDue = findViewById(R.id.btnDue);
            btnHistory = findViewById(R.id.btnHistory);
            btnScanQR = findViewById(R.id.btnScanQR);
            btnAttendance = findViewById(R.id.btnAttendance);

            // Navigate to Due screen
            btnDue.setOnClickListener(view -> {
                Intent intent = new Intent(DashboardActivity.this, DuesActivity.class);
                startActivity(intent);
            });

            // Navigate to History screen
            btnHistory.setOnClickListener(view -> {
                Intent intent = new Intent(DashboardActivity.this, HistoryActivity.class);
                startActivity(intent);
            });

            // Navigate to Scan QR screen
            btnScanQR.setOnClickListener(view -> {
                Intent intent = new Intent(DashboardActivity.this,  ScanQRActivity.class);
                startActivity(intent);
            });

            // Navigate to Attendance screen
            btnAttendance.setOnClickListener(view -> {
                Intent intent = new Intent(DashboardActivity.this, AttendanceRecordActivity.class);
                startActivity(intent);
            });
        }

        // Inflate top-right menu (logout)
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);  // Loads res/menu/main_menu.xml
            return true;
        }

        // Handle menu item clicks
        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId() == R.id.action_logout) {
                // You can replace this with actual logout logic
                Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DashboardActivity.this, com.example.android.LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
