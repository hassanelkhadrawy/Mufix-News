package com.example.finalmufixapp.Activites;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.finalmufixapp.R;

public class About extends AppCompatActivity {
    private String facebookUrl = "https://www.facebook.com/mufix.org/";
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
    }

    public void action(View view) {
        int id = view.getId();
        if (id == R.id.facebook) {

            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("fb://facewebmodal/f?href=" + facebookUrl));
            startActivity(intent);
        } else if (id == R.id.linkedin) {


            intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://www.linkedin.com/company/mufix-community/about/"));
            intent.setPackage("com.linkedin.android");
            startActivity(intent);

        }


    }

}
