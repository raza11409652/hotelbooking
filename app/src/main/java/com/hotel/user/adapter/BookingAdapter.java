/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.user.R;
import com.hotel.user.listener.BookingItemListener;
import com.hotel.user.model.Booking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.ViewHolder> {
    ArrayList<Booking> list;
    Context context;
    BookingItemListener listener;
    String TAG = BookingAdapter.class.getSimpleName();

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
        holder.bookingAmount.setText(list.get(position).getAmount());
        holder.property.setText("FOD" + list.get(position).getPropertyId());
        holder.startDate.setText(list.get(position).getStartDate());
        holder.endDate.setText(list.get(position).getEndDate());
//        holder.status.setText(list.get(position).getStatus());

        /**
         * Setting Dynamically 140sp Margin Bottom  to last Card view on Scroll till end
         *
         */
        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) holder.parent.getLayoutParams();
        if (position == list.size() - 1) {
            lp.setMargins(0, 0, 0, 140);
            Log.d(TAG, "onBindViewHolder: Last Card encounter");
        } else {
            lp.setMargins(0, 0, 0, 0);
        }

        holder.parent.setLayoutParams(lp);


        holder.viewPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPaymentViewClick(list.get(position));
            }
        });
       // Picasso.get().load(list.get(position).getPropertyCoverImage()).placeholder() into(holder.propertyImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView bookingNumber, status, property, startDate, endDate, bookingAmount, viewPayment;
        CardView parent;
        ImageView propertyImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bookingNumber = itemView.findViewById(R.id.booking_number);
            status = itemView.findViewById(R.id.booking_status);
            property = itemView.findViewById(R.id.booking_property);
            startDate = itemView.findViewById(R.id.booking_start_date);
            endDate = itemView.findViewById(R.id.booking_end_date);
            bookingAmount = itemView.findViewById(R.id.booking_amount);
            parent = itemView.findViewById(R.id.parent);
            viewPayment = itemView.findViewById(R.id.view_payment);
            propertyImage = itemView.findViewById(R.id.booking_property_image);

        }
    }
}
