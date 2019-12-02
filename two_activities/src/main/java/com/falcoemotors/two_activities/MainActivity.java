package com.falcoemotors.two_activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import static com.falcoemotors.two_activities.R.*;

public class MainActivity extends AppCompatActivity {

    public static final String TAG_SEND = "SEND";

    TextView txv1;
    Button btn1;
    EditText txt1;
    String msg,recivedMsg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);

        mT("On Create 1");

        txv1 = findViewById(id.txv1);
        btn1 = findViewById(id.btn1);
        txt1 = findViewById(id.txt1);

        btn1.setOnClickListener(this :: onClick);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mT("On Start 1");
    }

    @Override
    protected void onResume() {
        super.onResume();
        mT("On Resume 1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mT("On Destroy 1");
    }

    private void onClick(View view) {
        msg = txt1.getText().toString();

        Intent messageIntent = new Intent(this, SecondActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(TAG_SEND,msg);
        messageIntent.putExtras(bundle);
        startActivityForResult(messageIntent,111);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {

                Toast.makeText(this, "Reply Received", Toast.LENGTH_SHORT).show();
                Bundle bundle = data.getExtras();
                recivedMsg = bundle.getString(SecondActivity.TAG_RECEIVE);
                txv1.setText(recivedMsg);
            }
        }

    }

    private void mT(String msg) {

        LayoutInflater li = getLayoutInflater();

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
