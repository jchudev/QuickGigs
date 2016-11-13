package com.app.quickgigs.quickgigs;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Jonathon Chu on 11/13/2016.
 */

public class JobAdapter extends BaseAdapter {

    private Context context;
    private List<Job> listJobs;

    public JobAdapter(Context context, List<Job> listJobs){
        this.context = context;
        this.listJobs = listJobs;
    }


    @Override
    public int getCount() {
        return listJobs.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        if (v == null){
            v = LayoutInflater.from(context).inflate(R.layout.job_item, viewGroup, false);
        }
        TextView name = (TextView) v.findViewById(R.id.job_name);
        TextView price = (TextView)v.findViewById(R.id.price_text);
        TextView address = (TextView) v.findViewById(R.id.address_text);
        TextView distance = (TextView) v.findViewById(R.id.distance_text);
        Job current = listJobs.get(i);
        name.setText(current.name);
        price.setText("$" + current.pay);
        Log.v("WHAT IS HERE", address.getTextColors().toString());
//        address.setText(current.address);
//        distance.setText(current.distance);
        return v;
    }
}
