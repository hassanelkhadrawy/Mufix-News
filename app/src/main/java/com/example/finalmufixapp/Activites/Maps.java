package com.example.finalmufixapp.Activites;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.finalmufixapp.R;

import java.util.ArrayList;

public class Maps extends AppCompatActivity {

    private Spinner spMap;
    private Button uploadImage;
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
        uploadImage = (Button) findViewById(R.id.upload_image);
        imageView3 = (ImageView) findViewById(R.id.imageView3);
        number_of_floor.add("--اختر الطابق--");
        number_of_floor.add("الطابق الاول");
        number_of_floor.add("الطابق الثانى");
        number_of_floor.add("الطابق الثالث");
        number_of_floor.add("الطابق الرابع");
        number_of_floor.add("الطابق الخامس");


    }
    private void action(){
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, number_of_floor) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        spMap.setAdapter(arrayAdapter);


    }
}
