/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatsondemand.user.R;
import com.flatsondemand.user.listener.BookingItemListener;
import com.flatsondemand.user.model.Booking;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    ArrayList<Booking> list;
    Context context;
    BookingItemListener listener;

    public BookingAdapter(ArrayList<Booking> list, Context context, BookingItemListener bookingItemListener) {
        this.list = list;
        this.context = context;
        this.listener = bookingItemListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_booking_property, parent, false);

        return new BookingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.bookingNumber.setText(list.get(position).getNumber());
        holder.property.setText(list.get(position).getPropertyName() + " - " + list.get(position).getRoomNumber());
        holder.status.setText(list.get(position).getStatus());
        holder.bookingPeriod.setText(list.get(position).getStartDate() + " TO " + list.get(position).getEndDate());
        holder.bookingAmount.setText(list.get(position).getAmount());
        holder.payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPaymentButtonClick(list.get(position).getId(), list.get(position).getNumber());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingNumber, status, property, bookingPeriod, bookingAmount;
        Button payments;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingNumber = itemView.findViewById(R.id.booking_number);
            status = itemView.findViewById(R.id.status);
            property = itemView.findViewById(R.id.property_name);
            bookingPeriod = itemView.findViewById(R.id.booking_period);
            bookingAmount = itemView.findViewById(R.id.booking_amount);
            payments = itemView.findViewById(R.id.payment);
        }
    }
}
