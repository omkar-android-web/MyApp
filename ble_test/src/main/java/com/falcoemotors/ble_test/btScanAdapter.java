package com.falcoemotors.ble_test;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class btScanAdapter extends RecyclerView.Adapter<btScanAdapter.MyViewHolder> {


    private ArrayList<BluetoothDevice> mDataset;
    Context context;

    private BluetoothDevice selectedBt;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    btScanAdapter(Context ctx, ArrayList<BluetoothDevice> myDataset) {
        context = ctx;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent,
                                           int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycard, parent, false);

        MyViewHolder vh = new MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(mDataset.get(position)));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setBackgroundColor(Color.GREEN);
                selectedBt = mDataset.get(holder.getAdapterPosition());
            }
        });


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public BluetoothDevice getSelectedBt (){
        return selectedBt;
    }
}
