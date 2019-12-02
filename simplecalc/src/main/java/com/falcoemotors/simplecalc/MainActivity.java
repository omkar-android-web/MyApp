package com.falcoemotors.simplecalc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText txt1, txt2;
    TextView txv;
    double num1,num2,total;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt1 = findViewById(R.id.txt1);
        txt2 = findViewById(R.id.txt2);
        txv = findViewById(R.id.txv);

    }

    public void onAdd(View view) {

        num1 = Double.valueOf(txt1.getText().toString());
        num2 = Double.valueOf(txt2.getText().toString());
        total = addition(num1,num2);
        txv.setText(String.valueOf(total));

    }

    public void onSub(View view) {

        num1 = Double.valueOf(txt1.getText().toString());
        num2 = Double.valueOf(txt2.getText().toString());
        total = num1 - num2;
        txv.setText(String.valueOf(total));

    }

    public void onDiv(View view) {

        num1 = Double.valueOf(txt1.getText().toString());
        num2 = Double.valueOf(txt2.getText().toString());
        total = num1 / num2;
        txv.setText(String.valueOf(total));

    }

    public void onMul(View view) {

        num1 = Double.valueOf(txt1.getText().toString());
        num2 = Double.valueOf(txt2.getText().toString());
        total = num1 * num2;
        txv.setText(String.valueOf(total));

    }

    public static double addition(double a, double b){
        return a + b;
    }
}
