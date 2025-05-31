package com.example.android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

/**
 * Test activity to demonstrate the UI with placeholder data
 * Use this to test your layouts and adapters before connecting to real API
 */
public class TestPlaceholderActivity extends AppCompatActivity {

    private ListView historyListView;
    private HistoryAdapter adapter;
    private ArrayList<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history); // Reusing history layout

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Test Data");

        // Initialize views
        historyListView = findViewById(R.id.historyListView);

        // Load placeholder data
        loadPlaceholderData();
    }

    private void loadPlaceholderData() {
        // Generate sample transactions
        transactionList = PlaceholderDataGenerator.generateSampleTransactions(15);

        // Setup adapter
        adapter = new HistoryAdapter(this, transactionList);
        historyListView.setAdapter(adapter);

        // Show toast to indicate this is test data
        Toast.makeText(this, "Loaded " + transactionList.size() + " sample transactions", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to refresh with new placeholder data
     */
    private void refreshPlaceholderData() {
        transactionList.clear();
        transactionList.addAll(PlaceholderDataGenerator.generateSampleTransactions(10));
        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Refreshed with new sample data", Toast.LENGTH_SHORT).show();
    }

    /**
     * Method to add specific test scenarios
     */
    private void addTestScenarios() {
        transactionList.clear();

        // Add specific test cases
        transactionList.add(PlaceholderDataGenerator.getSampleTransaction());
        transactionList.add(PlaceholderDataGenerator.getCurrentlyBorrowedBook());
        transactionList.add(PlaceholderDataGenerator.getOverdueBook());

        // Add some random ones
        transactionList.addAll(PlaceholderDataGenerator.generateSampleTransactions(5));

        adapter.notifyDataSetChanged();
        Toast.makeText(this, "Added test scenarios", Toast.LENGTH_SHORT).show();
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