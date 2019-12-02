package com.falcoemotors.codelab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView txv;
    Button btn;
    int a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txv = findViewById(R.id.txv);
        btn = findViewById(R.id.btn);
        a = 2147483646;

        btn.setOnClickListener(this::onBtn);
        txv.setText(String.valueOf(a));
    }

    private void onBtn(View view) {

        txv.setText(String.valueOf(++a));

    }
}
