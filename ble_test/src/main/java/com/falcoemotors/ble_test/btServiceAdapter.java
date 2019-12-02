package com.falcoemotors.ble_test;

import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class btServiceAdapter extends RecyclerView.Adapter<btServiceAdapter.MyViewHolder> {

    private List<BluetoothGattService> mDataset;
    private Context context;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        TextView textView;
        MyViewHolder(TextView v) {
            super(v);
            textView = v;
        }
    }

    btServiceAdapter(Context ctx, List<BluetoothGattService> myDataset) {
        context = ctx;
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public btServiceAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mycard, parent, false);

        btServiceAdapter.MyViewHolder vh = new btServiceAdapter.MyViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(btServiceAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(String.valueOf(mDataset.get(position)));

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
