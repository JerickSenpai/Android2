package com.example.android;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlaceholderDataGenerator {

    private static final List<String> BOOK_TITLES = Arrays.asList(
            "Introduction to Computer Science",
            "Data Structures and Algorithms",
            "Database Management Systems",
            "Software Engineering Principles",
            "Operating Systems Concepts",
            "Computer Networks",
            "Artificial Intelligence: A Guide",
            "Machine Learning Fundamentals",
            "Web Development with Java",
            "Mobile App Development",
            "Cybersecurity Essentials",
            "Digital Image Processing",
            "Computer Graphics",
            "Compiler Design",
            "Discrete Mathematics",
            "Linear Algebra for Engineers",
            "Calculus: Early Transcendentals",
            "Physics for Scientists",
            "Chemistry: The Central Science",
            "Biology: Concepts and Connections",
            "Advanced Java Programming",
            "Python Programming Guide",
            "JavaScript: The Definitive Guide",
            "React Native Development",
            "Flutter App Development"
    );

    private static final List<String> DATES_2024 = Arrays.asList(
            "2024-01-15", "2024-01-22", "2024-02-05", "2024-02-18", "2024-03-01",
            "2024-03-14", "2024-03-28", "2024-04-10", "2024-04-23", "2024-05-06",
            "2024-05-19", "2024-06-02", "2024-06-15", "2024-06-28", "2024-07-11",
            "2024-07-24", "2024-08-06", "2024-08-19", "2024-09-02", "2024-09-15",
            "2024-09-28", "2024-10-11", "2024-10-24", "2024-11-06", "2024-11-19",
            "2024-12-02", "2024-12-15"
    );

    private static final List<String> RETURN_DATES_2024 = Arrays.asList(
            "2024-01-29", "2024-02-05", "2024-02-19", "2024-03-04", "2024-03-15",
            "2024-03-28", "2024-04-11", "2024-04-24", "2024-05-07", "2024-05-20",
            "2024-06-03", "2024-06-16", "2024-06-29", "2024-07-12", "2024-07-25",
            "2024-08-07", "2024-08-20", "2024-09-03", "2024-09-16", "2024-09-29",
            "2024-10-12", "2024-10-25", "2024-11-07", "2024-11-20", "2024-12-03",
            "2024-12-16", "2024-12-30"
    );

    private static final List<String> STATUSES = Arrays.asList(
            "returned", "approved", "overdue", "pending"
    );

    private static final List<String> DUES_MESSAGES = Arrays.asList(
            "Late return fee: ₱280.00 for 'Introduction to Computer Science'",
            "Damaged book fee: ₱850.00 for 'Data Structures and Algorithms'",
            "Lost book replacement: ₱2550.00 for 'Software Engineering Principles'",
            "Late return fee: ₱170.00 for 'Operating Systems Concepts'",
            "Processing fee: ₱115.00 for membership renewal",
            "Late return fee: ₱390.00 for 'Computer Networks'",
            "Damaged book fee: ₱680.00 for 'Artificial Intelligence: A Guide'",
            "Late return fee: ₱225.00 for 'Web Development with Java'",
            "Lost book replacement: ₱2150.00 for 'Mobile App Development'",
            "Late return fee: ₱340.00 for 'Cybersecurity Essentials'"
    );
    /**
     * Generate sample transaction history data
     */
    public static ArrayList<Transaction> generateSampleTransactions(int count) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String bookTitle = BOOK_TITLES.get(random.nextInt(BOOK_TITLES.size()));
            String borrowDate = DATES_2024.get(random.nextInt(DATES_2024.size()));

            String returnDate;
            String status = STATUSES.get(random.nextInt(STATUSES.size()));

            // Determine return date based on status
            switch (status) {
                case "approved":
                    returnDate = "Currently borrowed";
                    break;
                case "pending":
                    returnDate = "Not returned";
                    break;
                case "overdue":
                    returnDate = "Overdue";
                    break;
                default: // returned
                    returnDate = RETURN_DATES_2024.get(random.nextInt(RETURN_DATES_2024.size()));
                    break;
            }

            transactions.add(new Transaction(i + 1, bookTitle, borrowDate, returnDate, status));
        }

        return transactions;
    }

    /**
     * Generate sample dues data
     */
    public static ArrayList<String> generateSampleDues(int count) {
        ArrayList<String> dues = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            dues.add(DUES_MESSAGES.get(random.nextInt(DUES_MESSAGES.size())));
        }

        return dues;
    }

    /**
     * Generate sample student IDs for QR testing
     */
    public static ArrayList<Integer> generateSampleStudentIds(int count) {
        ArrayList<Integer> studentIds = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            // Generate student IDs in range 100000 to 999999
            int studentId = 100000 + random.nextInt(900000);
            studentIds.add(studentId);
        }

        return studentIds;
    }

    /**
     * Get a specific sample transaction for testing
     */
    public static Transaction getSampleTransaction() {
        return new Transaction(
                1,
                "Introduction to Computer Science",
                "2024-03-15",
                "2024-03-29",
                "returned"
        );
    }

    /**
     * Get sample currently borrowed book
     */
    public static Transaction getCurrentlyBorrowedBook() {
        return new Transaction(
                2,
                "Data Structures and Algorithms",
                "2024-11-01",
                "Currently borrowed",
                "approved"
        );
    }

    /**
     * Get sample overdue book
     */
    public static Transaction getOverdueBook() {
        return new Transaction(
                3,
                "Software Engineering Principles",
                "2024-10-15",
                "Overdue",
                "overdue"
        );
    }

    /**
     * Generate mock JSON response for history API
     */
    public static String generateMockHistoryJson(int studentId) {
        StringBuilder json = new StringBuilder();
        json.append("[");

        ArrayList<Transaction> transactions = generateSampleTransactions(5);

        for (int i = 0; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            json.append("{");
            json.append("\"book_title\":\"").append(t.getBookTitle()).append("\",");
            json.append("\"borrow_date\":\"").append(t.getBorrowDate()).append("\",");
            json.append("\"return_date\":\"").append(t.getReturnDate().equals("Currently borrowed") ? "null" : t.getReturnDate()).append("\",");
            json.append("\"status\":\"").append(t.getStatus()).append("\"");
            json.append("}");

            if (i < transactions.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        return json.toString();
    }

    /**
     * Generate mock JSON response for dues API
     */
    public static String generateMockDuesJson() {
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"success\":true,");
        json.append("\"dues\":[");

        ArrayList<String> dues = generateSampleDues(3);

        for (int i = 0; i < dues.size(); i++) {
            json.append("\"").append(dues.get(i)).append("\"");
            if (i < dues.size() - 1) {
                json.append(",");
            }
        }

        json.append("]");
        json.append("}");
        return json.toString();
    }
}