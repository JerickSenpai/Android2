package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

public class ScanQRActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeScannerView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        barcodeScannerView = findViewById(R.id.barcode_scanner);
        backButton = findViewById(R.id.back_button);

        barcodeScannerView.decodeContinuous(callback);

        backButton.setOnClickListener(v -> {
            Intent intent = new Intent(ScanQRActivity.this, DashboardActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String scannedData = result.getText();
            if (scannedData == null) return;

            barcodeScannerView.pause(); // Pause after reading

            if (scannedData.startsWith("http://") || scannedData.startsWith("https://")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedData));
                startActivity(browserIntent);
            } else {
                Toast.makeText(ScanQRActivity.this, "Scanned: " + scannedData, Toast.LENGTH_SHORT).show();
                barcodeScannerView.resume();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
    }
}
