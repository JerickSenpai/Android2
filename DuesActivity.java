package com.example.android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import java.util.ArrayList;

public class DuesActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> duesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dues);

        Toolbar toolbar = findViewById(R.id.toolbarDues);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.duesListView);

        // Dummy data for now
        duesList = new ArrayList<>();
        duesList.add("Book: Java Basics | Due: May 15, 2025 | Penalty: ₱200");
        duesList.add("Book: Database Systems | Due: May 20, 2025 | Penalty: ₱300");
        duesList.add("Book: Manuscripts | Due: May 20, 2025 | Penalty: ₱300");
        duesList.add("Book: Management Systems | Due: May 23, 2025 | Penalty: ₱200");
        duesList.add("Book: POS Systems | Due: May 23, 2025 | Penalty: ₱200");
        duesList.add("Book: Web Development | Due: May 26, 2025 | Penalty: ₱250");


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, duesList);
        listView.setAdapter(adapter);
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
