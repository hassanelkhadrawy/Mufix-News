package com.example.finalmufixapp.Activites;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmufixapp.R;

import java.util.ArrayList;

public class StartActivity extends AppCompatActivity {

    private Spinner elfr2a;
    private Spinner department;
    private Spinner section;
    private Button go;
    private View view;
    private Typeface typeface;
    private TextView Login;
    private String txt_elfr2a, txt_depart;
    private int txt_section;
    private ArrayList<String> elfr2a_list=new ArrayList<>();
    private ArrayList<String> department_list=new ArrayList<>();
    private ArrayAdapter<String>  elfr2aAdapter, EldepartAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        elfr2a_list.add("--Select Year--");
        elfr2a_list.add("1");
        elfr2a_list.add("2");
        elfr2a_list.add("3");
        elfr2a_list.add("4");
        department_list.add("Select Department");
        typeface = Typeface.createFromAsset(getAssets(),"fonts/Comfortaa-Bold.ttf");



    }

    public void Map(View view) {
        startActivity(new Intent(this, Maps.class));
    }

    private void ElGadwal() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        view = LayoutInflater.from(StartActivity.this).inflate(R.layout.spenners, null);
        initView();

        department.setVisibility(View.GONE);


        elfr2aAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, elfr2a_list) {

            @SuppressLint("ResourceAsColor")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTypeface(typeface);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        elfr2a.setAdapter(elfr2aAdapter);
        EldepartAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, department_list) {

            @SuppressLint("ResourceAsColor")
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTypeface(typeface);
                tv.setTextColor(Color.parseColor("#9e0b0f"));
                return view;
            }
        };
        department.setAdapter(EldepartAdapter);

        builder.setView(view);


        elfr2a.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    txt_elfr2a = elfr2a.getSelectedItem().toString();
                  Department_list_1();
                    department.setVisibility(View.VISIBLE);

                } else if (position == 2) {
                    txt_elfr2a = elfr2a.getSelectedItem().toString();
                   Department_list_1();
                    department.setVisibility(View.VISIBLE);
                } else if (position == 3) {
                    Department_list_2();
                    txt_elfr2a = elfr2a.getSelectedItem().toString();
                    department.setVisibility(View.VISIBLE);
                } else if (position == 4) {
                    Department_list_2();
                    txt_elfr2a = elfr2a.getSelectedItem().toString();
                    department.setVisibility(View.VISIBLE);
                } else {
                    department.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        department.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (elfr2a.getSelectedItemPosition()==1){

                    if (position == 1) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=12;

                    } else if (position == 2) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=2;
                    } else if (position == 3) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=3;
                    }
                }else if (elfr2a.getSelectedItemPosition()==2){


                    if (position == 1) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=11;

                    } else if (position == 2) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=2;
                    } else if (position == 3) {
                        txt_depart = department.getSelectedItem().toString();
                        txt_section=2;
                    }

                }else if (elfr2a.getSelectedItemPosition()==3){

                   if (position == 1) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=5;

                   } else if (position == 2) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=5;
                   } else if (position == 3) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=3;
                   } else if (position == 4) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=1;
                   }else if (position == 5) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=2;
                   }else if (position == 6) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=2;
                   }
               }else if (elfr2a.getSelectedItemPosition()==4){


                   if (position == 1) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=4;

                   } else if (position == 2) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=5;
                   } else if (position == 3) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=2;
                   } else if (position == 4) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=1;
                   }else if (position == 5) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=2;
                   }else if (position == 6) {
                       txt_depart = department.getSelectedItem().toString();
                       txt_section=2;
                   }

               }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (elfr2a.getSelectedItemPosition() != 0) {
                    Intent intent = new Intent(StartActivity.this, Tables.class);
                    intent.putExtra("elfr2a", txt_elfr2a);
                    intent.putExtra("depart", txt_depart);
                    intent.putExtra("section", txt_section);
                    startActivity(intent);


                }
            }
        });

        AlertDialog dialog = builder.create();

        dialog.show();
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.card_back_without_border);

    }

    private void initView() {
        elfr2a = (Spinner) view.findViewById(R.id.elfr2a);
        department = (Spinner)view.findViewById(R.id.department);
        go = (Button)view.findViewById(R.id.go);

    }

    public void Table_Time(View view) {
        ElGadwal();
    }

    public void NewsFeeds(View view) {
        startActivity(new Intent(this,User.class));
    }


    private void Department_list_1(){

        department_list.clear();
        department_list.add("Select Department");
        department_list.add("Public");
        department_list.add("Bioinformatics");
        department_list.add("Software Engineering");
    }


    private void Department_list_2(){
        department_list.clear();
    department_list.add("Select Department");
    department_list.add("CS");
    department_list.add("IT");
    department_list.add("IS");
    department_list.add("OR");
    department_list.add("Bioinformatics");
    department_list.add("Software Engineering");
}



    public void LOGIN1(View view) {
        startActivity(new Intent(this,MainActivity.class));

    }
}
