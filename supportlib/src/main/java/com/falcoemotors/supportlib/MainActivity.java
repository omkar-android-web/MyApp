package com.falcoemotors.supportlib;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    Button btn;
    TextView txv;

    // array of color names, these match the color resources in color.xml
    private String[] mColorArray = {"red", "pink", "purple", "deep_purple",
            "indigo", "blue", "light_blue", "cyan", "teal", "green",
            "light_green", "lime", "yellow", "amber", "orange", "deep_orange",
            "brown", "grey", "blue_grey", "black" };

    int colorRes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = findViewById(R.id.btn);
        txv = findViewById(R.id.txv);
        btn.setOnClickListener(this :: onChange);

        // restore saved instance state (the text color)
        if (savedInstanceState != null) {
            txv.setTextColor(savedInstanceState.getInt("color"));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // save the current text color
        outState.putInt("color", txv.getCurrentTextColor());
    }

    private void onChange(View view) {

        Random random = new Random();
        String colorName = mColorArray[random.nextInt(20)];
        int colorResourceName = getResources().getIdentifier(colorName,
                "color", getApplicationContext().getPackageName());
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            colorRes =
                    getResources().getColor(colorResourceName, this.getTheme());

        }
        //int colorRes = ContextCompat.getColor(this, colorResourceName);
        txv.setTextColor(colorRes);

    }
}
