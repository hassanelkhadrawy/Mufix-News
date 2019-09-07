package com.example.finalmufixapp.Activites;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalmufixapp.R;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {

    private Spinner spMap;
    private Typeface typeface;
    private ImageView imageView3;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String>number_of_floor=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initView();
        action();
    }


    private void initView() {
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
                        imageView3.setImageResource(R.drawable.img1);
                        return;
                    case 2:
                        imageView3.setImageResource(R.drawable.img2);
                        return;
                    case 3:
                        imageView3.setImageResource(R.drawable.img3);
                        return;
                    case 4:
                        imageView3.setImageResource(R.drawable.img4);
                        return;
                    case 5:
                        imageView3.setImageResource(R.drawable.img5);
                        return;
                    case 6:
                        imageView3.setImageResource(R.drawable.img6);
                        return;
                    case 7:
                        imageView3.setImageResource(R.drawable.img7);
                        return;
                    case 8:
                        imageView3.setImageResource(R.drawable.img8);
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
            public void onClick(View v) {
                Intent intent = new Intent(Maps.this, Image.class);
                intent.putExtra("image_name", spMap.getSelectedItemPosition());
                startActivity(intent);
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,StartActivity.class));
        finish();
    }



    public void lOgIn(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }


}
