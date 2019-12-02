package com.falcoemotors.ble_test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class motorRxAdapter extends RecyclerView.Adapter<motorRxAdapter.MyViewHolder> {

    private ArrayList<byte[]> mDataset;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    motorRxAdapter(Context ctx, ArrayList<byte[]> myDataset) {
        context = ctx;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public motorRxAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycard, parent, false);

        motorRxAdapter.MyViewHolder vh = new motorRxAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(motorRxAdapter.MyViewHolder holder, int position) {
        holder.textView.setText((mDataset.get(position)).toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
