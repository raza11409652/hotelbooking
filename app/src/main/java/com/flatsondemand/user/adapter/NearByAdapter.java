/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.flatsondemand.user.R;
import com.flatsondemand.user.listener.NearBySelector;
import com.flatsondemand.user.model.Nearby;

import java.util.ArrayList;

public class NearByAdapter extends RecyclerView.Adapter<NearByAdapter.VieWHolder> {
    ArrayList<Nearby> list ;
    Context context;
    NearBySelector selector ;

    public NearByAdapter(ArrayList<Nearby> list, Context context, NearBySelector selector) {
        this.list = list;
        this.context = context;
        this.selector = selector;
    }

    @NonNull
    @Override
    public VieWHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_chip_selector, parent, false);
        return new NearByAdapter.VieWHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VieWHolder holder, final int position) {
        holder.textView.setText(list.get(position).getTitle());
        if (list.get(position).isStatus()){
            holder.parent.setBackground(context.getDrawable(R.drawable.selector_active));
        }else{
            holder.parent.setBackground(context.getDrawable(R.drawable.selector_unactive));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAG", "onClick: "+holder.getAdapterPosition());
                selector.onItemClick(holder.getAdapterPosition() , list.get(position).getTitle() , list);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class VieWHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ConstraintLayout parent ;

        public VieWHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
            parent  =itemView.findViewById(R.id.parent) ;

        }
    }
}
