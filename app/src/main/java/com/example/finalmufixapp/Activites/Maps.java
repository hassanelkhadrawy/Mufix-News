package com.example.finalmufixapp.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmufixapp.Database.DatabaseHandler;
import com.example.finalmufixapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {

    private Spinner spMap;
    private Typeface typeface;
    private ImageView imageView3;
    DatabaseHandler db;
    Progress_Dialog progress_dialog;
    private String MainUrl="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/maps/";
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>number_of_floor=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (true) {
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
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        db = new DatabaseHandler(this);

        CheckInternet checkInternet=new CheckInternet(this);

        boolean check=checkInternet.CheckConnection();
        if (check){

            if (db.DeletData()){

//                Toast.makeText(this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }
        }


        initView();
        action();
    }


    private void initView() {
        progress_dialog = new Progress_Dialog(this);
        progress_dialog.SweetAlertDialog();
        spMap = (Spinner) findViewById(R.id.sp_map);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        number_of_floor.add("--Select Floor--");
        number_of_floor.add("G");
        number_of_floor.add("1");
        number_of_floor.add("2");
        number_of_floor.add("3");
        number_of_floor.add("4");
        number_of_floor.add("5");
        number_of_floor.add("6");
        number_of_floor.add("7");


    }

    private void action() {
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, number_of_floor) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                typeface = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa-Bold.ttf");
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTypeface(typeface);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        spMap.setAdapter(arrayAdapter);

        spMap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 1:
                        getImage(position,"0");
                        return;
                    case 2:
                        getImage(position,"1");
                        return;
                    case 3:
                        getImage(position,"2");
                        return;
                    case 4:
                        getImage(position,"3");
                        return;
                    case 5:
                        getImage(position,"4");
                        return;
                    case 6:
                        getImage(position,"5");
                        return;
                    case 7:
                        getImage(position,"6");
                        return;
                    case 8:
                        getImage(position,"7");
                        return;
                    default:
                        return;


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (spMap.getSelectedItemPosition()!=0){
                Intent intent = new Intent(Maps.this, Image.class);
                intent.putExtra("image_name", spMap.getSelectedItemPosition()-1);
                intent.putExtra("flag",1);
                    startActivity(intent);
                overridePendingTransition(0, 0);
                }else {

                }
            }
        });


    }

    public boolean get(final String Image_Name) {

        boolean flag = false;

        Bitmap bitmap = db.getImage(Image_Name);

        if (bitmap != null) {

            imageView3.setImageBitmap(bitmap);

            flag = true;
        } else {

            flag = false;
        }


        return flag;

    }

    private void getImage(int postion, final String Image_Name){
        if (postion != 0) {

//            final String imagename = elfr2a + depart + spSection.getSelectedItem().toString();

            boolean check = get(Image_Name);
            if (!check) {

                String URL = MainUrl + Image_Name+ ".png";


                progress_dialog.pDialog.show();

                Picasso.with(Maps.this).load(URL).into(imageView3, new Callback() {
                    @Override
                    public void onSuccess() {

                        progress_dialog.pDialog.dismiss();
                        BitmapDrawable drawable = (BitmapDrawable) imageView3.getDrawable();
                        Bitmap bitmap = drawable.getBitmap();
                        boolean succes = db.Insert_Image(Image_Name, bitmap);
                        if (succes) {
                            progress_dialog.pDialog.dismiss();
                        } else {
                            progress_dialog.pDialog.dismiss();


                        }
                    }

                    @Override
                    public void onError() {
                        progress_dialog.pDialog.dismiss();

                        Toast.makeText(Maps.this, "check internet", Toast.LENGTH_SHORT).show();

                    }
                });

            }

        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }





}
