package com.example.bloodlineapp;

import android.content.Context;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

public class AlertAdapter extends ArrayAdapter<AlertClass> {
    public AlertAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }
}
