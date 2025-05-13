    package com.example.android;

    public class Transaction {
        private String type;
        private String bookTitle;
        private String date;

        public Transaction(String type, String bookTitle, String date) {
            this.type = type;
            this.bookTitle = bookTitle;
            this.date = date;
        }

        public String getType() {
            return type;
        }

        public String getBookTitle() {
            return bookTitle;
        }

        public String getDate() {
            return date;
        }
    }
