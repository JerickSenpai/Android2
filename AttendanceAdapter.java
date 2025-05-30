package com.example.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<AttendanceModel> attendanceList;

    public AttendanceAdapter(List<AttendanceModel> attendanceList) {
        this.attendanceList = attendanceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendance_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AttendanceModel attendance = attendanceList.get(position);
        holder.tvTimeIn.setText(attendance.getTimeIn());
        holder.tvTimeOut.setText(attendance.getTimeOut());
        holder.tvDate.setText(attendance.getDate());
    }

    @Override
    public int getItemCount() {
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTimeIn, tvTimeOut, tvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTimeIn = itemView.findViewById(R.id.tvTimeIn);
            tvTimeOut = itemView.findViewById(R.id.tvTimeOut);
            tvDate = itemView.findViewById(R.id.tvDate);
        }
    }
}
