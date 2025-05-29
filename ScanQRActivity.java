package com.example.android;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ScanQRActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeScannerView;
    private ImageView backButton;
    private ScrollView borrowSlipContainer;
    private LinearLayout loadingOverlay, scanInstructions;
    private TextView loadingText;

    // Book Information Views
    private TextView bookTitle, bookId, bookAvailability;

    // Student Information Views
    private TextInputEditText studentName, studentId, reason;

    // Borrow Details Views
    private TextView borrowDate, expirationDate;

    // Action Buttons
    private Button cancelButton, submitButton;

    // Current scanned book data
    private String currentBookId;
    private String generatedBorrowId;
    private boolean isProcessingBorrow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);

        initializeViews();
        setupListeners();

        barcodeScannerView.decodeContinuous(callback);
    }

    private void initializeViews() {
        // Scanner and overlay views
        barcodeScannerView = findViewById(R.id.barcode_scanner);
        backButton = findViewById(R.id.back_button);
        borrowSlipContainer = findViewById(R.id.borrow_slip_container);
        loadingOverlay = findViewById(R.id.loading_overlay);
        scanInstructions = findViewById(R.id.scan_instructions);
        loadingText = findViewById(R.id.loading_text);

        // Book information views
        bookTitle = findViewById(R.id.book_title);
        bookId = findViewById(R.id.book_id);
        bookAvailability = findViewById(R.id.book_availability);

        // Student information views
        studentName = findViewById(R.id.student_name);
        studentId = findViewById(R.id.student_id);
        reason = findViewById(R.id.reason);

        // Borrow details views
        borrowDate = findViewById(R.id.borrow_date);
        expirationDate = findViewById(R.id.expiration_date);

        // Action buttons
        cancelButton = findViewById(R.id.cancel_button);
        submitButton = findViewById(R.id.submit_button);
    }

    private void setupListeners() {
        backButton.setOnClickListener(v -> {
            if (isProcessingBorrow) {
                resetToScanner();
            } else {
                navigateToMainActivity();
            }
        });

        cancelButton.setOnClickListener(v -> resetToScanner());

        submitButton.setOnClickListener(v -> {
            if (validateStudentInfo()) {
                submitBorrowRequest();
            }
        });
    }

    private final BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            String scannedData = result.getText();
            if (scannedData == null || isProcessingBorrow) return;

            barcodeScannerView.pause();

            // Check if it's a URL (non-book QR)
            if (scannedData.startsWith("http://") || scannedData.startsWith("https://")) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(scannedData));
                startActivity(browserIntent);
                return;
            }

            // Step 1: User taps "Generate Borrow Slip" (automatically triggered)
            // Step 2: System opens QR Scanner (already open)
            // Step 3: User scans QR on the book
            processBookQR(scannedData);
        }
    };

    private void processBookQR(String bookQRData) {
        // Step 4: System extracts book ID/title from QR
        currentBookId = extractBookId(bookQRData);

        if (currentBookId == null) {
            Toast.makeText(this, "Invalid book QR code", Toast.LENGTH_SHORT).show();
            barcodeScannerView.resume();
            return;
        }

        showLoading("Extracting book information...");

        // Step 5: Redirects to the "Borrow Slip Form" with book details pre-filled
        new Handler().postDelayed(() -> {
            fetchBookDetails(currentBookId);
        }, 1500);
    }

    private String extractBookId(String qrData) {
        // Extract book ID from QR data
        // This could be in various formats:
        // - Pure ID: "BOOK123"
        // - JSON: {"bookId":"BOOK123","title":"Sample Book"}
        // - URL: "library://book/BOOK123"

        try {
            if (qrData.startsWith("BOOK") || qrData.matches("\\d+")) {
                return qrData;
            } else if (qrData.contains("bookId")) {
                // Simple JSON parsing (you might want to use a proper JSON library)
                String[] parts = qrData.split("\"");
                for (int i = 0; i < parts.length - 1; i++) {
                    if ("bookId".equals(parts[i])) {
                        return parts[i + 2];
                    }
                }
            } else if (qrData.contains("library://book/")) {
                return qrData.substring(qrData.lastIndexOf("/") + 1);
            }
            return qrData; // Fallback - treat entire string as book ID
        } catch (Exception e) {
            return null;
        }
    }

    private void fetchBookDetails(String bookId) {
        // In a real app, this would make an API call to your library system
        // For demo purposes, we'll simulate book data

        loadingText.setText("Loading book information...");

        new Handler().postDelayed(() -> {
            // Simulate API response
            BookInfo book = simulateBookLookup(bookId);

            if (book != null) {
                displayBookDetails(book);
                setupBorrowSlipForm();
            } else {
                hideLoading();
                Toast.makeText(this, "Book not found in system", Toast.LENGTH_LONG).show();
                barcodeScannerView.resume();
            }
        }, 1000);
    }

    private BookInfo simulateBookLookup(String bookId) {
        // Simulate database lookup
        BookInfo book = new BookInfo();
        book.id = bookId;
        book.title = "The Great Gatsby"; // Would come from database
        book.author = "F. Scott Fitzgerald";
        book.isbn = "978-0-7432-7356-5";
        book.isAvailable = true;
        book.availableCopies = 3;

        return book;
    }

    private void displayBookDetails(BookInfo book) {
        bookTitle.setText("Title: " + book.title);

        // Generate auto Book ID for borrow slip
        generatedBorrowId = generateBorrowId();
        bookId.setText("Book ID: " + generatedBorrowId);

        if (book.isAvailable) {
            bookAvailability.setText("Status: Available (" + book.availableCopies + " copies)");
            bookAvailability.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        } else {
            bookAvailability.setText("Status: Not Available");
            bookAvailability.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }

    private String generateBorrowId() {
        // Generate auto Book ID (format: BR + timestamp + random)
        long timestamp = System.currentTimeMillis();
        int random = (int) (Math.random() * 1000);
        return "BR" + (timestamp % 100000) + String.format("%03d", random);
    }

    private void setupBorrowSlipForm() {
        // Set up borrow dates - 7 days expiration
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        Date today = new Date();

        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DAY_OF_MONTH, 7); // 7 days expiration period
        Date expirationDateTime = cal.getTime();

        borrowDate.setText("Borrow Date: " + dateFormat.format(today));
        expirationDate.setText("Expiration Date: " + dateFormat.format(expirationDateTime));

        hideLoading();
        showBorrowSlipForm();
    }

    private boolean validateStudentInfo() {
        String name = studentName.getText().toString().trim();
        String id = studentId.getText().toString().trim();

        if (name.isEmpty()) {
            studentName.setError("Student name is required");
            studentName.requestFocus();
            return false;
        }

        if (id.isEmpty()) {
            studentId.setError("Student ID is required");
            studentId.requestFocus();
            return false;
        }

        return true;
    }

    private void submitBorrowRequest() {
        // Step 7: User submits slip for approval
        showLoading("Submitting borrow request...");

        // Collect all form data
        BorrowRequest request = new BorrowRequest();
        request.bookId = currentBookId;
        request.generatedBorrowId = generatedBorrowId;
        request.studentName = studentName.getText().toString().trim();
        request.studentId = studentId.getText().toString().trim();
        request.reason = reason.getText().toString().trim();
        request.borrowDate = new Date();

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_MONTH, 7); // 7 days expiration
        request.expirationDate = cal.getTime();

        // Simulate API call to submit borrow request
        new Handler().postDelayed(() -> {
            // In real app, send request to server
            boolean success = submitToLibrarySystem(request);

            hideLoading();

            if (success) {
                Toast.makeText(this, "Borrow request submitted successfully!\nYou will receive confirmation via email.",
                        Toast.LENGTH_LONG).show();

                // Navigate back to dashboard or show success screen
                new Handler().postDelayed(() -> {
                    navigateToMainActivity();
                }, 2000);
            } else {
                Toast.makeText(this, "Failed to submit request. Please try again.",
                        Toast.LENGTH_LONG).show();
            }
        }, 2000);
    }

    private boolean submitToLibrarySystem(BorrowRequest request) {
        // In a real app, this would make an API call to your library management system
        // For demo purposes, always return success
        return true;
    }

    private void showLoading(String message) {
        loadingText.setText(message);
        loadingOverlay.setVisibility(LinearLayout.VISIBLE);
    }

    private void hideLoading() {
        loadingOverlay.setVisibility(LinearLayout.GONE);
    }

    private void showBorrowSlipForm() {
        scanInstructions.setVisibility(LinearLayout.GONE);
        borrowSlipContainer.setVisibility(ScrollView.VISIBLE);
        isProcessingBorrow = true;
    }

    private void resetToScanner() {
        borrowSlipContainer.setVisibility(ScrollView.GONE);
        scanInstructions.setVisibility(LinearLayout.VISIBLE);
        isProcessingBorrow = false;
        currentBookId = null;
        generatedBorrowId = null;

        // Clear form fields
        studentName.setText("");
        studentId.setText("");
        reason.setText("");

        barcodeScannerView.resume();
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(ScanQRActivity.this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!isProcessingBorrow) {
            barcodeScannerView.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
    }

    @Override
    public void onBackPressed() {
        if (isProcessingBorrow) {
            resetToScanner();
        } else {
            super.onBackPressed();
        }
    }

    // Data classes for book and borrow request
    private static class BookInfo {
        String id;
        String title;
        String author;
        String isbn;
        boolean isAvailable;
        int availableCopies;
    }

    private static class BorrowRequest {
        String bookId;
        String generatedBorrowId;
        String studentName;
        String studentId;
        String reason;
        Date borrowDate;
        Date expirationDate;
    }
}