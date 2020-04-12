package com.example.finalmufixapp.Activites;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import android.widget.Toast;

import com.example.finalmufixapp.Database.DatabaseHandler;
import com.example.finalmufixapp.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Tables extends AppCompatActivity {
    private ArrayList<String> section_list = new ArrayList<>();
    private Spinner spSection;
    private ImageView Display;
    private Typeface typeface;
    private String elfr2a, depart;
    DatabaseHandler db;
    Progress_Dialog progress_dialog;
    private ArrayAdapter<String> section_adabter, EldepartAdapter;
    private String MainUrl = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/tables/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        initView();
        db = new DatabaseHandler(this);
        savedInstanceState = getIntent().getExtras();
        int number_section = savedInstanceState.getInt("section");
        elfr2a = savedInstanceState.getString("elfr2a");
        depart = savedInstanceState.getString("depart");

        progress_dialog = new Progress_Dialog(this);
        progress_dialog.SweetAlertDialog();
        CheckInternet checkInternet=new CheckInternet(this);
        boolean check=checkInternet.CheckConnection();
        if (check){

            if (db.DeletData()){

                Toast.makeText(this, "Data Refreshed", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "Data Not Refreshed", Toast.LENGTH_SHORT).show();

            }

        }

        Section(number_section);
        action();
    }

    private void Section(int number) {
        section_list.clear();

        section_list.add("--Select Section--");

        for (int i = 1; i <= number; i++) {

            section_list.add("" + i + "");
        }


    }

    private void initView() {
        spSection = (Spinner) findViewById(R.id.sp_section);
        Display = (ImageView) findViewById(R.id.display);
    }

    private void action() {


        section_adabter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, section_list) {

            @SuppressLint("ResourceAsColor")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                typeface = Typeface.createFromAsset(getAssets(), "fonts/Comfortaa-Bold.ttf");
                tv.setTypeface(typeface);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        spSection.setAdapter(section_adabter);

        spSection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                if (i != 0) {

                    final String imagename = elfr2a + depart + spSection.getSelectedItem().toString();

                    boolean check = get(imagename);
                    if (!check) {

                        String URL = MainUrl + elfr2a + "/" + depart + "/" + spSection.getSelectedItem().toString() + ".png";


                        progress_dialog.pDialog.show();

                        Picasso.with(Tables.this).load(URL).into(Display, new Callback() {
                            @Override
                            public void onSuccess() {

                                progress_dialog.pDialog.dismiss();
                                BitmapDrawable drawable = (BitmapDrawable) Display.getDrawable();
                                Bitmap bitmap = drawable.getBitmap();
                                boolean succes = db.Insert_Image(imagename, bitmap);
                                if (succes) {
                                    progress_dialog.pDialog.dismiss();
                                } else {
                                    progress_dialog.pDialog.dismiss();


                                }
                            }

                            @Override
                            public void onError() {
                                progress_dialog.pDialog.dismiss();

                                Toast.makeText(Tables.this, "check internet", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }

                }else {
                    Display.setImageResource(R.drawable.def);
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (spSection.getSelectedItemPosition()!=0){

                    Intent intent = new Intent(Tables.this, Image.class);
                    intent.putExtra("elfr2a", elfr2a);
                    intent.putExtra("depart", depart);
                    intent.putExtra("section", spSection.getSelectedItem().toString());
                    intent.putExtra("flag", 0);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }


            }
        });
    }


    public boolean get(final String Image_Name) {

        boolean flag = false;

        Bitmap bitmap = db.getImage(Image_Name);

        if (bitmap != null) {

            Display.setImageBitmap(bitmap);

            flag = true;
        } else {

            flag = false;
        }


        return flag;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(this, StartActivity.class));
        finish();

    }
}
