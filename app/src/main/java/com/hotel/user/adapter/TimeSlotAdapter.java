/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.user.R;
import com.hotel.user.listener.TimeSlotListener;
import com.hotel.user.model.TimeSlot;

import java.util.ArrayList;

public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {
    ArrayList<TimeSlot> list;
    Context context;
    TimeSlotListener listener;

    public TimeSlotAdapter(ArrayList<TimeSlot> list, Context context, TimeSlotListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

//    public TimeSlotAdapter(ArrayList<TimeSlot> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_time_slot, parent, false);
        return new TimeSlotAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.slotTime.setText(list.get(position).getTime());
        boolean status = list.get(position).isStatus();
        if (status) {
            holder.slotTime.setTextColor(context.getColor(R.color.text_color));
        } else {
            holder.slotTime.setTextColor(context.getColor(R.color.color_alert_red));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(list.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView slotTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            slotTime = itemView.findViewById(R.id.slot);
        }
    }
}
