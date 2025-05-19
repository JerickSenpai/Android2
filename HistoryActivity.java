package com.example.android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
        transactionList.add(new Transaction("Java Programming", "2025-04-10", "Returned"));
        transactionList.add(new Transaction("Data Structures", "2025-03-28", "Overdue"));

        adapter = new HistoryAdapter(this, transactionList);
        historyListView.setAdapter(adapter);
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
