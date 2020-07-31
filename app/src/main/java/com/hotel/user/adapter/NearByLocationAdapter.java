/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.user.R;
import com.hotel.user.model.NearByLocation;

import java.util.ArrayList;

public class NearByLocationAdapter extends RecyclerView.Adapter<NearByLocationAdapter.ViewHolder> {
    ArrayList<NearByLocation>locations ;
    Context context  ;

    public NearByLocationAdapter(ArrayList<NearByLocation> locations, Context context) {
        this.locations = locations;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  =LayoutInflater.from(parent.getContext()).inflate(R.layout.single_near_by, parent, false);
        return new NearByLocationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.address.setText(locations.get(position).getAddress());
        holder.title.setText(locations.get(position).getTitle());
        holder.distance.setText(locations.get(position).getDistance() + " m");
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title , address , distance;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            address =itemView.findViewById(R.id.address);
            distance  =itemView.findViewById(R.id.distance);
        }
    }
}
