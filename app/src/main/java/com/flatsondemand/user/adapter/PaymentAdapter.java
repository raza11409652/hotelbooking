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
import com.flatsondemand.user.listener.PaymentClickListener;
import com.flatsondemand.user.model.Payments;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.Viewholder> {
    ArrayList<Payments> list;
    Context context;
    PaymentClickListener listener;

    public PaymentAdapter(ArrayList<Payments> list, Context context, PaymentClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_payment_layout, parent, false);
        return new PaymentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        holder.amountToBePaid.setText(list.get(position).getAmount());
        holder.paymentId.setText("FOD_PAY_" + list.get(position).getId());
        holder.status.setText(list.get(position).getStatus());
        holder.paymentPeriod.setText(list.get(position).getStartDate() + " TO " + list.get(position).getEndDate());
        holder.propertyName.setText(list.get(position).getRoom());
        boolean isPaid = list.get(position).getPaid();
        holder.dueDate.setText("Due Date " + list.get(position).getDueDate());
        if (isPaid) {
            holder.dueDate.setVisibility(View.GONE);
            holder.status.setBackground(context.getDrawable(R.drawable.chip_green));
            holder.payNow.setVisibility(View.GONE);
        } else {
            holder.dueDate.setVisibility(View.VISIBLE);
            holder.payNow.setVisibility(View.VISIBLE);
            holder.status.setBackground(context.getDrawable(R.drawable.chip_danger));
        }

        holder.payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onPaymentReceive(list.get(position).getAmount(), list.get(position).getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView paymentId, propertyName, status, paymentPeriod, amountToBePaid, dueDate;
        Button details, payNow;

        public Viewholder(@NonNull View itemView) {


            super(itemView);
            paymentId = itemView.findViewById(R.id.payment_id);
            propertyName = itemView.findViewById(R.id.property_name);
            status = itemView.findViewById(R.id.status);
            paymentPeriod = itemView.findViewById(R.id.payment_period);
            amountToBePaid = itemView.findViewById(R.id.booking_amount);
            dueDate = itemView.findViewById(R.id.due_date);
            payNow = itemView.findViewById(R.id.pay_button);
        }
    }
}
