/*
 * Hackdroid Tech Author raza11409652 github www.github.com/raza11409652 Copyright (c)  2020
 */

package com.hotel.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hotel.user.R;
import com.hotel.user.model.Payments;

import java.util.ArrayList;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.Viewholder> {
    ArrayList<Payments> list;
    Context context;

    public PaymentAdapter(ArrayList<Payments> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_payment_item, parent, false);
        return new PaymentAdapter.Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, final int position) {
        String electricity = list.get(position).getElectricity();
        if (electricity.equals("0.00") || electricity == "0") {
            holder.elect.setVisibility(View.GONE);
            holder.hintElectricity.setVisibility(View.GONE);
            holder.paidForElectricity.setVisibility(View.GONE);
        } else {
            holder.elect.setVisibility(View.VISIBLE);
            holder.hintElectricity.setVisibility(View.VISIBLE);
            holder.paidForElectricity.setVisibility(View.VISIBLE);
            holder.paidForElectricity.setText("RS." + list.get(position).getElectricity());
        }
        holder.paidForRent.setText("Rs. " + list.get(position).getRent());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView paidForRent, paidForElectricity, hintRent, hintElectricity;
        ImageView rent, elect;


        public Viewholder(@NonNull View itemView) {


            super(itemView);

            paidForRent = itemView.findViewById(R.id.paid_for_rent);
            paidForElectricity = itemView.findViewById(R.id.paid_for_elec);
            hintRent = itemView.findViewById(R.id.hint_paid_for_rent);
            hintElectricity = itemView.findViewById(R.id.hint_for_elec);
            rent = itemView.findViewById(R.id.image);
            elect = itemView.findViewById(R.id.image_elec);

        }
    }
}
