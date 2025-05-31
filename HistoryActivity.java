package com.example.android;

import android.content.SharedPreferences;
import android.os.Bundle;
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

        loadTransactionHistory();
    }

    private void loadTransactionHistory() {
        // Get student ID from SharedPreferences
        SharedPreferences sharedPref = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String studentId = sharedPref.getString("student_id", "");

        if (studentId.isEmpty()) {
            Toast.makeText(this, "Student ID not found. Please login again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://your-server-ip/api/get_history.php?student_id=" + studentId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> parseHistoryData(response),
                error -> {
                    Toast.makeText(this, "Failed to load history: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}