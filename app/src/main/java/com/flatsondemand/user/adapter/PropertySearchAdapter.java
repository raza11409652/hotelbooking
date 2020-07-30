/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatsondemand.user.R;
import com.flatsondemand.user.listener.OnPropertyClick;
import com.flatsondemand.user.model.Property;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PropertySearchAdapter extends RecyclerView.Adapter<PropertySearchAdapter.ViewHolder> {
    ArrayList<Property> list;
    Context context;
    OnPropertyClick onPropertyClick;
    String TAG = PropertySearchAdapter.class.getSimpleName();

    public PropertySearchAdapter(ArrayList<Property> list, Context context, OnPropertyClick onPropertyClick) {
        this.list = list;
        this.context = context;
        this.onPropertyClick = onPropertyClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_property, parent, false);
        return new PropertySearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Heart click" + list.get(position).isMapped());
            }
        });
        if (list.get(position).isMapped() == false) {
            holder.heart.setImageDrawable(context.getDrawable(R.drawable.heart));
        } else {
            holder.heart.setImageDrawable(context.getDrawable(R.drawable.heart_fill));
        }
        holder.propertyName.setText(list.get(position).getName());
        holder.propertyPrice.setText(list.get(position).getPrice());
        if (list.get(position).getRoom() > 0) {
            holder.roomLeft.setText("Hurry only " + list.get(position).getRoom() + " room left");
        } else {
            holder.roomLeft.setText("Filling fast");
        }

        holder.heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPropertyClick.onHeartIconClick(list.get(position));
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPropertyClick.onItemClick(list.get(position));
            }
        });
        Picasso.get().load(list.get(position).getImage()).placeholder(R.drawable.single_property_fod)
                .error(R.drawable.single_property_fod).into(holder.coverImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView propertyName, propertyPrice, roomLeft;
        ImageView coverImage;
        ImageButton heart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            propertyName = itemView.findViewById(R.id.name);
            propertyPrice = itemView.findViewById(R.id.price);
            roomLeft = itemView.findViewById(R.id.room_left);
            heart = itemView.findViewById(R.id.heart);
            coverImage = itemView.findViewById(R.id.image);
        }
    }
}
