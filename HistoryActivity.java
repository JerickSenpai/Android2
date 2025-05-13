package com.example.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView recyclerHistory;
    private HistoryAdapter historyAdapter;
    private ArrayList<Transaction> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toolbar toolbar = findViewById(R.id.toolbarHistory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerHistory = findViewById(R.id.recyclerHistory);
        recyclerHistory.setLayoutManager(new LinearLayoutManager(this));

        transactionList = new ArrayList<>();

        // Dummy data
        transactionList.add(new Transaction("Borrowed", "Harry Potter", "April 25, 2025"));
        transactionList.add(new Transaction("Returned", "Science 101", "April 26, 2025"));
        transactionList.add(new Transaction("Borrowed", "Math for Dummies", "April 26, 2025"));
        transactionList.add(new Transaction("Returned", "English 101", "April 27, 2025"));

        historyAdapter = new HistoryAdapter(transactionList);
        recyclerHistory.setAdapter(historyAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(HistoryActivity.this, DashboardActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
