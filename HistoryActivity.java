package com.example.android;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private ListView historyListView;
    private HistoryAdapter adapter;
    private ArrayList<Transaction> transactionList;
    private boolean useTestData = false; // Flag to switch between real and test data

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        historyListView = findViewById(R.id.historyListView);
        transactionList = new ArrayList<>();
        adapter = new HistoryAdapter(this, transactionList);
        historyListView.setAdapter(adapter);

        // Check if we should use test data (for development/testing)
        if (getIntent().getBooleanExtra("use_test_data", false)) {
            useTestData = true;
            getSupportActionBar().setTitle("History (Test Data)");
            loadPlaceholderData();
        } else {
            loadTransactionHistory();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Add menu item to toggle test data
        menu.add(0, 1, 0, useTestData ? "Use Real Data" : "Use Test Data");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else if (item.getItemId() == 1) {
            // Toggle between test and real data
            useTestData = !useTestData;
            invalidateOptionsMenu(); // Refresh menu

            if (useTestData) {
                getSupportActionBar().setTitle("History (Test Data)");
                loadPlaceholderData();
            } else {
                getSupportActionBar().setTitle("History");
                loadTransactionHistory();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void loadTransactionHistory() {
        // Get student ID from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String studentId = sharedPref.getString("student_id", "");

        if (studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found. Loading test data instead.", Toast.LENGTH_SHORT).show();
            loadPlaceholderData();
            return;
        }

        String url = "http://your-server-ip/api/get_history.php?student_id=" + studentId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> parseHistoryData(response),
                error -> {
                    Toast.makeText(this, "Failed to load history. Loading test data instead.", Toast.LENGTH_SHORT).show();
                    loadPlaceholderData(); // Fallback to test data on network error
                }
        );

        Volley.newRequestQueue(this).add(request);
    }

    private void parseHistoryData(JSONArray response) {
        try {
            transactionList.clear(); // Clear existing data

            for (int i = 0; i < response.length(); i++) {
                JSONObject obj = response.getJSONObject(i);
                String title = obj.getString("book_title");
                String borrowDate = obj.getString("borrow_date");
                String returnDate = obj.optString("return_date", "Not returned");
                String status = obj.getString("status");

                // Format the return date based on status
                if (returnDate.equals("null") || returnDate.isEmpty()) {
                    if (status.equals("approved")) {
                        returnDate = "Currently borrowed";
                    } else {
                        returnDate = "Not returned";
                    }
                }

                transactionList.add(new Transaction(title, borrowDate, returnDate));
            }

            adapter.notifyDataSetChanged();

            if (transactionList.isEmpty()) {
                Toast.makeText(this, "No transaction history found", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this, "Error parsing history: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            loadPlaceholderData(); // Fallback to test data on parsing error
        }
    }

    /**
     * Load placeholder data for testing and development
     */
    private void loadPlaceholderData() {
        transactionList.clear();
        transactionList.addAll(PlaceholderDataGenerator.generateSampleTransactions(12));
        adapter.notifyDataSetChanged();

        Toast.makeText(this, "Loaded " + transactionList.size() + " sample transactions", Toast.LENGTH_SHORT).show();
    }
}