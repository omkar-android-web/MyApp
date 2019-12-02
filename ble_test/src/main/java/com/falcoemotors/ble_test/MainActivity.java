package com.falcoemotors.ble_test;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private BluetoothAdapter mBluetoothAdapter;
    private int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000;
    private BluetoothLeScanner mLEScanner;
    private ScanSettings settings;
    private List<ScanFilter> filters;
    private BluetoothGatt mGatt;
    String btDevice;

    RecyclerView recyclerViewServices;
    RecyclerView.LayoutManager layoutManagerService;
    btServiceAdapter btServiceAdapter;

    RecyclerView recyclerViewDevice;
    RecyclerView.LayoutManager layoutManagerDevice;
    btScanAdapter btDeviceAdapter;

    RecyclerView recyclerViewMotorRx;
    RecyclerView.LayoutManager layoutManagerMotorRx;
    motorRxAdapter motorRxAdapter;

    BluetoothDevice selectedBt;
    List<BluetoothGattService> services;
    ArrayList<byte[]>  motorRxList;

    Button btnScanDevice, btnScanService, btnMotorRx;
    ProgressBar pB;

    public static final UUID motor_ServiceUUID = UUID.fromString("6E400001-B5A3-F393-E0A9-E50E24DCCA9E");
    public static final UUID motor_TXCharacteristicUUID = UUID.fromString("6E400003-B5A3-F393-E0A9-E50E24DCCA9E");
    public static final UUID motor_RXCharacteristicUUID = UUID.fromString("6E400002-B5A3-F393-E0A9-E50E24DCCA9E");
    public static final UUID TS_ServiceUUID = UUID.fromString("0000180D-0000-1000-8000-00805f9b34fb");
    public static final UUID TS_RXCharacteristicUUID = UUID.fromString("00002A37-0000-1000-8000-00805f9b34fb");

    byte[] motorRx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScanService = findViewById(R.id.btnScanService);
        btnScanDevice = findViewById(R.id.btnScanDevice);
        btnMotorRx = findViewById(R.id.btnMotorRx);

        pB = findViewById(R.id.pB);
        pB.setVisibility(View.INVISIBLE);

        btnScanDevice.setOnClickListener(this :: onScanDevice);
        btnScanService.setOnClickListener(this ::onScanService);
        btnMotorRx.setOnClickListener(this :: onMotorRx);

        recyclerViewDevice = findViewById(R.id.recyclerViewDevices);
        recyclerViewDevice.setHasFixedSize(true);
        layoutManagerDevice = new LinearLayoutManager(this);
        recyclerViewDevice.setLayoutManager(layoutManagerDevice);

        recyclerViewServices = findViewById(R.id.recyclerViewServices);
        recyclerViewServices.setHasFixedSize(true);
        layoutManagerService = new LinearLayoutManager(this);
        recyclerViewServices.setLayoutManager(layoutManagerService);

        recyclerViewMotorRx = findViewById(R.id.recyclerViewMotorRx);
        recyclerViewMotorRx.setHasFixedSize(true);
        layoutManagerMotorRx = new LinearLayoutManager(this);
        recyclerViewMotorRx.setLayoutManager(layoutManagerMotorRx);

        motorRxList = new ArrayList<>();

        mHandler = new Handler();
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE Not Supported",
                    Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_CANCELED) {
                //Bluetooth not enabled.
                finish();
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void onScanDevice(View view) {

        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        } else {
            if (Build.VERSION.SDK_INT >= 21) {
                mLEScanner = mBluetoothAdapter.getBluetoothLeScanner();

                settings = new ScanSettings.Builder()
                        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                        .build();
                filters = new ArrayList<>();
            }
            scanLeDevice(true);

        }



        mHandler.postDelayed(() -> {
            if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
                scanLeDevice(false);
                pB.setVisibility(View.INVISIBLE);

                /*btDeviceAdapter = new btScanAdapter(myDevices);
                recyclerView.setAdapter(btDeviceAdapter);
                btDeviceAdapter.notifyDataSetChanged();*/
            }
        }, 15000);
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mLEScanner.stopScan(mScanCallback);
                }
            }, SCAN_PERIOD);
            mLEScanner.startScan(filters, settings, mScanCallback);
            pB.setVisibility(View.VISIBLE);
        } else {
            mLEScanner.stopScan(mScanCallback);
        }
    }

    private ScanCallback mScanCallback = new ScanCallback() {
        ArrayList<BluetoothDevice> devi = new ArrayList<>();
        @Override
        public void onScanResult(int callbackType, ScanResult result) {
            Log.i("callbackType", String.valueOf(callbackType));
            Log.i("result", result.toString());
            btDevice = result.getDevice().getName();

            Log.i("result device", String.valueOf(result.getDevice()));

            BluetoothDevice deviceName = result.getDevice();

            if(!devi.contains(deviceName))
                devi.add(result.getDevice());

            btDeviceAdapter = new btScanAdapter(MainActivity.this,devi);
            recyclerViewDevice.setAdapter(btDeviceAdapter);
            btDeviceAdapter.notifyDataSetChanged();
        }
        @Override
        public void onBatchScanResults(List<ScanResult> results) {
            for (ScanResult sr : results) {
                Log.i("ScanResult - Results", sr.toString());
            }
        }
        @Override
        public void onScanFailed(int errorCode) {
            Log.e("Scan Failed", "Error Code: " + errorCode);
        }
    };

    private void onScanService(View view) {

        pB.setVisibility(View.VISIBLE);

        selectedBt = btDeviceAdapter.getSelectedBt();

        connectToDevice(selectedBt);

        mHandler.postDelayed(() -> {

            pB.setVisibility(View.INVISIBLE);

            if(services != null){
                btServiceAdapter = new btServiceAdapter(MainActivity.this,services);
                recyclerViewServices.setAdapter(btServiceAdapter);
                btServiceAdapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(this,"Services are null",Toast.LENGTH_SHORT).show();

        }, 15000);

    }


    public void connectToDevice(BluetoothDevice device) {
        if (mGatt == null) {
            mGatt = device.connectGatt(this, false, gattCallback);
            scanLeDevice(false);// will stop after first device detection
        }
    }

    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            Log.i("onConnectionStateChange", "Status: " + status);
            switch (newState) {
                case BluetoothProfile.STATE_CONNECTED:
                    Log.i("gattCallback", "STATE_CONNECTED");
                    gatt.discoverServices();
                    break;
                case BluetoothProfile.STATE_DISCONNECTED:
                    Log.e("gattCallback", "STATE_DISCONNECTED");
                    gatt.disconnect();
                    break;
                default:
                    Log.e("gattCallback", "STATE_OTHER");

            }
        }
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            services = gatt.getServices();
            Log.i("onServicesDiscovered", services.toString());
            //gatt.readCharacteristic(services.get(1).getCharacteristics().get(0));


        }
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic
                                                 characteristic, int status) {
            Log.i("onCharacteristicRead", characteristic.toString());


            BluetoothGattService motorService = gatt.getService(motor_ServiceUUID);
            BluetoothGattCharacteristic motor_RXChar = motorService.getCharacteristic(motor_RXCharacteristicUUID);

            Log.i("Char.Val => ",characteristic.getUuid()+"");



            motorRx = motor_RXChar.getValue();

            motorRxList.add(motorRx);

            Log.i("Motor RX value"," => "+ Arrays.toString(motorRx));

        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);


        }
    };

    private void onMotorRx(View view) {

        pB.setVisibility(View.VISIBLE);

        mHandler.postDelayed(() -> {
            pB.setVisibility(View.INVISIBLE);
            if(motorRx != null){
                motorRxAdapter = new motorRxAdapter(MainActivity.this,motorRxList);
                recyclerViewMotorRx.setAdapter(motorRxAdapter);
                motorRxAdapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(this,"Motor Rx are null",Toast.LENGTH_SHORT).show();

        }, 15000);

    }

    @Override
    protected void onDestroy() {
        if (mGatt == null) {
            return;
        }
        mGatt.close();
        mGatt = null;
        super.onDestroy();
    }

    private void mt(String msg){
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }


}
