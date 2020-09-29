package com.example.bloodlineapp.Models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bloodlineapp.R;

import java.util.ArrayList;

public class AlertClass extends BaseAdapter {
    private Context context;
    private  int layout;
    private ArrayList<Alert> alertclass;


    public AlertClass(Context context,int layout, ArrayList<Alert> alertclass){
        this.alertclass= alertclass;
        this.context = context;
        this.layout= layout;
    }

    @Override
    public int getCount() {
        return alertclass.size();
    }

    @Override
    public Object getItem(int position) { return alertclass.get(position);}

    @Override
    public long getItemId(int position) { return position; }

    class ViewHolder{
        TextView alertName, alertDate, alertText,alertBloodG;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View Row =convertView;
        ViewHolder holder = new ViewHolder();
        if (Row  == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            Row = inflater.inflate(layout, null);
            holder.alertDate= ((TextView) Row.findViewById(R.id.userD));
            holder.alertText= ((TextView) Row.findViewById(R.id.userT));
            holder.alertName = ((TextView) Row.findViewById(R.id.userN));
            holder.alertBloodG = ((TextView) Row.findViewById(R.id.userBlood));

            Row.setTag(holder);

        }
        else {
            holder = (ViewHolder) Row.getTag();
        }

        Alert alert = alertclass.get(position);
        holder.alertName.setText(alert.getFname());
        holder.alertText.setText(alert.getAlert());
        holder.alertDate.setText(alert.getDate());
        holder.alertBloodG.setText(alert.getBloodG());

        return Row;
    }





}
