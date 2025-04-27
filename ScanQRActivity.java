package com.example.libraryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

// This is the Scan QR screen
public class ScanQRActivity extends AppCompatActivity {

    Button btnScanQR; // Button to simulate scanning

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        // Setup toolbar
        Toolbar toolbar = findViewById(R.id.toolbarScanQR);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnScanQR = findViewById(R.id.btnScanQR);

        // Click event for Scan QR button
        btnScanQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBookInfoDialog();
            }
        });
    }

    // Simulate scanning and show a popup with book info
    private void showBookInfoDialog() {
        // PLACEHOLDER: Replace this with real QR database lookup later
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Book Info");
        builder.setMessage("Book: Harry Potter\nAuthor: J.K. Rowling\nStatus: Available");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    // Back button on toolbar returns to Dashboard
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            startActivity(new Intent(ScanQRActivity.this, DashboardActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
