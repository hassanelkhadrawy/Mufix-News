package com.example.finalmufixapp.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.finalmufixapp.R;
import com.squareup.picasso.Picasso;


public class Image extends AppCompatActivity {
String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String efr2a,depart,section;
        savedInstanceState=getIntent().getExtras();
        efr2a=savedInstanceState.getString("elfr2a");
        depart=savedInstanceState.getString("depart");
        section=savedInstanceState.getString("section");

        ImageView imageView=findViewById(R.id.show);

        Picasso.with(this).load(url+efr2a+depart+section).placeholder(R.drawable.mufix_logo).into(imageView);



    }

}
