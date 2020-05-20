/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.flatsondemand.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flatsondemand.user.R;
import com.flatsondemand.user.listener.UpiListener;

import java.util.List;

public class UpiAdapter extends RecyclerView.Adapter<UpiAdapter.ViewHolder> {
    Context context;
    List<ResolveInfo> list;
    Intent intent;
    UpiListener upiListener;

    public UpiAdapter(Context context, List<ResolveInfo> list, Intent intent, UpiListener upiListener) {
        this.context = context;
        this.list = list;
        this.intent = intent;
        this.upiListener = upiListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.single_layout_upi, parent, false);

        return new UpiAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ResolveInfo info = list.get(position);

        String name = String.valueOf(info.loadLabel(context.getPackageManager()));
        final Drawable icon = info.loadIcon(context.getPackageManager());
        // holder.bind(name, icon);
        holder.textView.setText(name);
        holder.imageView.setImageDrawable(icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upiListener.onUpiItemClick(info, intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.upiName);
            imageView = itemView.findViewById(R.id.image);
        }
    }
}
