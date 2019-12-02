package com.falcoemotors.implicitintent;

import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText txt_uri, txt_loc, txt_share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_uri = findViewById(R.id.txt_uri);
        txt_loc = findViewById(R.id.txt_loc);
        txt_share = findViewById(R.id.txt_share);
    }

    public void openWebsite(View view) {

        // Get the URL text.
        String url = txt_uri.getText().toString();

        // Parse the URI and create the intent.
        Uri webpage = Uri.parse("https://"+url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        // Find an activity to hand the intent and start that activity.
        //if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        /*} else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }*/

    }

    public void openLocation(View view) {

        // Get the string indicating a location. Input is not validated; it is
        // passed to the location handler intact.
        String loc = txt_loc.getText().toString();

        // Parse the location and create the intent.
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        // Find an activity to handle the intent, and start that activity.
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }

    }

    public void shareText(View view) {

        String txt = txt_share.getText().toString();
        String mimeType = "text/plain";
        ShareCompat.IntentBuilder
                .from(this)
                .setType(mimeType)
                .setChooserTitle("Share this text with: ")
                .setText(txt)
                .startChooser();


    }
}
