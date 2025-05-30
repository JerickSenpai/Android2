package com.example.android;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DuesActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> duesList;
    ArrayAdapter<String> adapter;
    String URL = "http://192.168.x.x/myapi/dues.php"; // Replace with your IP/path

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dues);

        Toolbar toolbar = findViewById(R.id.toolbarDues);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = findViewById(R.id.duesListView);
        duesList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, duesList);
        listView.setAdapter(adapter);

        fetchDuesFromServer();
    }

    private void fetchDuesFromServer() {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            JSONArray duesArray = response.getJSONArray("dues");
                            duesList.clear();
                            for (int i = 0; i < duesArray.length(); i++) {
                                duesList.add(duesArray.getString(i));
                            }
                            adapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(this, "Failed to load dues", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Parsing error", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    error.printStackTrace();
                    Toast.makeText(this, "Network error: " + error.toString(), Toast.LENGTH_SHORT).show();
                });

        queue.add(request);
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