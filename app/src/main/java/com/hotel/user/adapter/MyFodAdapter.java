/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.user.R;
import com.hotel.user.listener.OnMyFodClick;
import com.hotel.user.model.Booking;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyFodAdapter extends RecyclerView.Adapter<MyFodAdapter.ViewHolder> {
    ArrayList<Booking> list;
    Context context;
    OnMyFodClick onMyFodClick;

    public MyFodAdapter(ArrayList<Booking> list, Context context, OnMyFodClick onMyFodClick) {
        this.list = list;
        this.context = context;
        this.onMyFodClick = onMyFodClick;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_my_fod, parent, false);
        return new MyFodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.propertyName.setText(list.get(position).getPropertyName());
        holder.roomNumber.setText(list.get(position).getRoomNumber());
        Picasso.get().load(list.get(position).getPropertyCoverImage()).into(holder.imageView);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyFodClick.onItemButtonClick(list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView propertyName, roomNumber;
        Button view;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            propertyName = itemView.findViewById(R.id.property_name);
            roomNumber = itemView.findViewById(R.id.room_number);
            view = itemView.findViewById(R.id.view);
        }
    }
}
