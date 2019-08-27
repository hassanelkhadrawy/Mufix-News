package com.example.finalmufixapp.Activites;

import android.annotation.SuppressLint;
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

public class Tables extends AppCompatActivity {
    private ArrayList<String> section_list = new ArrayList<>();
    private Spinner spSection;
    private ImageView Display;
    private ArrayAdapter<String> section_adabter, EldepartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        initView();
        savedInstanceState=getIntent().getExtras();
        int number_section=savedInstanceState.getInt("section");
        String elfr2a=savedInstanceState.getString("elfr2a");
        String depart=savedInstanceState.getString("depart");
        Section(number_section);
        action();
    }

    private void Section(int number) {
        section_list.clear();

        section_list.add("--اختر السكشن--");

        for (int i = 1; i <= number; i++) {

            section_list.add("" + i + "");
        }


    }

    private void initView() {
        spSection = (Spinner) findViewById(R.id.sp_section);
        Display = (ImageView) findViewById(R.id.display);
    }
    private void action(){


        section_adabter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, section_list) {

            @SuppressLint("ResourceAsColor")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        spSection.setAdapter(section_adabter);



    }
}
