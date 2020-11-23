package com.example.bloodlineapp.Models;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bloodlineapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class AlertAdapter extends FirebaseRecyclerAdapter<Alert, AlertAdapter.PastViewHolder> {

    public AlertAdapter(@NonNull FirebaseRecyclerOptions<Alert> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull PastViewHolder holder, int position, @NonNull Alert model) {
        holder.fname.setText(model.getFullname());
        holder.alert.setText(model.getAlert());
        holder.bloodgrp.setText(model.getBloodneeded());
        holder.date.setText(model.getDatealert());
        Glide.with(holder.itemView).load(model.getUrl()).into(holder.profile);
    }

    @NonNull
    @Override
    public PastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.singlerow, parent, false);
        return new PastViewHolder(view);

    }

    public class PastViewHolder extends RecyclerView.ViewHolder{

        TextView alert,date,bloodgrp,fname;
        ImageView profile;

        public PastViewHolder(@NonNull View itemView) {
            super(itemView);
            alert = itemView.findViewById(R.id.alerttext);
            date = itemView.findViewById(R.id.datetext);
            bloodgrp = itemView.findViewById(R.id.bloodtext);
            fname = itemView.findViewById(R.id.nametext);
            profile = itemView.findViewById(R.id.img1);
        }
    }
}
