package com.falcoemotors.two_activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {

    public static final String TAG_RECEIVE = "RECEIVE";

    TextView txv2;
    Button btn2;
    EditText txt2;
    String msg, recivedMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mT("On Create 1");

        txv2 = findViewById(R.id.txv2);
        btn2 = findViewById(R.id.btn2);
        txt2 = findViewById(R.id.txt2);

        Intent reciveIntent = getIntent();
        Bundle bundle = reciveIntent.getExtras();
        recivedMsg = bundle.getString(MainActivity.TAG_SEND);
        txv2.setText(recivedMsg);

        btn2.setOnClickListener(this :: onClick);

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
        msg = txt2.getText().toString();

        Intent messageIntent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(TAG_RECEIVE,msg);
        messageIntent.putExtras(bundle);
        setResult(RESULT_OK,messageIntent);
        finish();
    }

    private void mT(String msg){
        Toast toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        View toastView = toast.getView();

        TextView toastMessage = toastView.findViewById(R.id.txvToast);

        toastMessage.setGravity(Gravity.BOTTOM);
        toast.show();
    }
}
