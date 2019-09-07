package com.example.finalmufixapp.Activites;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.finalmufixapp.R;
import com.github.chrisbanes.photoview.PhotoView;


public class Image extends AppCompatActivity {
    private PhotoView Display;
    boolean isImageFitToScreen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String efr2a, depart, section;
        savedInstanceState = getIntent().getExtras();
        int postion = savedInstanceState.getInt("image_name");
        initView();
        Map_Image(postion);

    }

    private void Map_Image(int position) {

        switch (position) {
            case 1:
                Display.setImageResource(R.drawable.img1);
                return;
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

    private void initView() {
        Display = (PhotoView) findViewById(R.id.show);

//        Display.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(isImageFitToScreen) {
//                    isImageFitToScreen=false;
//                    Display.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//                    Display.setAdjustViewBounds(true);
//                }else{
//                    isImageFitToScreen=true;
//                    Display.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
//                    Display.setScaleType(ImageView.ScaleType.FIT_XY);
//                }
//            }
//        });
    }
}
