package com.example.finalmufixapp.Activites;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.finalmufixapp.R;
import com.squareup.picasso.Picasso;


public class Image extends AppCompatActivity {
    ImageView Display;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String efr2a, depart, section;
        savedInstanceState = getIntent().getExtras();
        int postion = savedInstanceState.getInt("image_name");
        Map_Image(postion);
    }

    private void Map_Image(int position) {

        switch (position) {
            case 1:
                Display.setImageResource(R.drawable.img1);
            case 2:
                Display.setImageResource(R.drawable.img2);
                return;
            case 3:
                Display.setImageResource(R.drawable.img3);
                return;
            case 4:
                Display.setImageResource(R.drawable.img4);
                return;
            case 5:
                Display.setImageResource(R.drawable.img5);
                return;
            case 6:
                Display.setImageResource(R.drawable.img6);
                return;
            case 7:
                Display.setImageResource(R.drawable.img7);
                return;
            case 8:
                Display.setImageResource(R.drawable.img8);
                return;
            default:
                return;


        }


    }
}
