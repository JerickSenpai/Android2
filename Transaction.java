package com.example.android;

public class Transaction {
    private String bookTitle;
    private String date;
    private String status;

    public Transaction(String bookTitle, String date, String status) {
        this.bookTitle = bookTitle;
        this.date = date;
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getDate() {
        return date;
    }

    public String getStatus() {
        return status;
    }

    public int getType() {
        return 0;
    }
}
