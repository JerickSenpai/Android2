package com.example.android;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import java.util.ArrayList;

public class HistoryAdapter extends ArrayAdapter<Transaction> {

    public HistoryAdapter(@NonNull Context context, ArrayList<Transaction> transactions) {
        super(context, 0, transactions);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        Transaction transaction = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_transaction, parent, false);
        }

        TextView title = convertView.findViewById(R.id.bookTitle);
        TextView date = convertView.findViewById(R.id.transactionDate);
        TextView status = convertView.findViewById(R.id.transactionStatus);

        if (transaction != null) {
            title.setText(transaction.getBookTitle());
            date.setText(transaction.getDate());
            status.setText(transaction.getStatus());
        }

        return convertView;
    }
}
