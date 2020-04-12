package com.example.finalmufixapp.Activites;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.finalmufixapp.Database.DatabaseHandler;
import com.example.finalmufixapp.R;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;


public class Image extends AppCompatActivity {
    private PhotoView Display;
    boolean isImageFitToScreen;
    DatabaseHandler db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        String efr2a, depart, section;

        db=new DatabaseHandler(this);


        savedInstanceState = getIntent().getExtras();
        initView();

        int flag = savedInstanceState.getInt("flag");
        if (flag == 0) {
            efr2a = savedInstanceState.getString("elfr2a");
            depart = savedInstanceState.getString("depart");
            section = savedInstanceState.getString("section");

            String imagename = efr2a + depart + section;

            get(imagename);

        } else {
            String image_name = String.valueOf(savedInstanceState.getInt("image_name"));

            get(image_name);

        }


    }

//    private void Map_Image(int position) {
//
//        switch (position) {
//            case 1:
//                Display.setImageResource(R.drawable.img1);
//                return;
//            case 2:
//                Display.setImageResource(R.drawable.img2);
//                return;
//            case 3:
//                Display.setImageResource(R.drawable.img3);
//                return;
//            case 4:
//                Display.setImageResource(R.drawable.img4);
//                return;
//            case 5:
//                Display.setImageResource(R.drawable.img5);
//                return;
//            case 6:
//                Display.setImageResource(R.drawable.img6);
//                return;
//            case 7:
//                Display.setImageResource(R.drawable.img7);
//                return;
//            case 8:
//                Display.setImageResource(R.drawable.img8);
//                return;
//            def:
//                return;
//
//
//        }
//
//
//    }

    private void initView() {
        Display = (PhotoView) findViewById(R.id.show);


    }




    public void get( final String Image_Name) {



        Bitmap bitmap= db.getImage(Image_Name);

        if (bitmap !=null) {

            Display.setImageBitmap(bitmap);

        }



    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
