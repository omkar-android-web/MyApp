package com.falcoemotors.ble_test;

import android.bluetooth.BluetoothGattService;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

public class gattservices extends AppCompatActivity {

    List<BluetoothGattService> services;
    RecyclerView recyclerViewServices;
    RecyclerView.LayoutManager layoutManager;
    btServiceAdapter btServiceAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gattservices);

        recyclerViewServices = findViewById(R.id.recylerViewServicesG);
        recyclerViewServices.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewServices.setLayoutManager(layoutManager);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        services = (List<BluetoothGattService>) bundle.getSerializable("key");

        Toast.makeText(this,"Executed", Toast.LENGTH_LONG).show();


        btServiceAdapter = new btServiceAdapter(gattservices.this,services);
        recyclerViewServices.setAdapter(btServiceAdapter);
        btServiceAdapter.notifyDataSetChanged();

    }
}
