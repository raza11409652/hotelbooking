/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatsondemand.user.R;
import com.flatsondemand.user.listener.CategoryClickListener;
import com.flatsondemand.user.model.ComplaintsCategory;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.VieHolder> {
    ArrayList<ComplaintsCategory> list;
    Context context;
    CategoryClickListener listener;

    public CategoryAdapter(ArrayList<ComplaintsCategory> list, Context context, CategoryClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

//    public CategoryAdapter(ArrayList<ComplaintsCategory> list, Context context) {
//        this.list = list;
//        this.context = context;
//    }

    @NonNull
    @Override
    public VieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_category_text, parent, false);
        return new CategoryAdapter.VieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VieHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTitle());
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

    public class VieHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public VieHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }
}
