package com.example.finalmufixapp.Activites;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Adapters.MyPagerAdapter;
import com.example.finalmufixapp.Models.P_Info_Model;
import com.example.finalmufixapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private MyPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ArrayList<P_Info_Model> chickLoginList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getUser_Info.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        sharedPreferences=getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        SELECT_Login_Info(URL);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignIn(), "SignIn");
        adapter.addFragment(new SignUp(), "SignUp");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }


    private void aotoLogin(String email,String password){
        for (int x=0;x<chickLoginList.size();x++){

            if (email.equals(chickLoginList.get(x).P_Email)
                    &&password.equals(chickLoginList.get(x).P_Password )){


                finish();
                Intent i = new Intent(getBaseContext(),Home.class);
                startActivity(i);
                break;

            }else {

                if (chickLoginList.size() -1== x){

                }


            }

        }

    }


    void SELECT_Login_Info(String URL) {
        chickLoginList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("User_Info");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String  get_Username = getData.getString("username");
                                String  get_Email = getData.getString("email");
                                String   get_Password = getData.getString("password");
                                String    get_P_Image = getData.getString("p_image");


                                chickLoginList.add(new P_Info_Model(get_Username, get_Email, get_Password, get_P_Image));

                            }

                            //progress_dialog.pDialog.dismiss();
                            String email_shared = sharedPreferences.getString("Email", "no data found");
                            String password_shared = sharedPreferences.getString("person_password", "no data found");
                            aotoLogin(email_shared,password_shared);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "check internet", Toast.LENGTH_SHORT).show();
                //progress_dialog.pDialog.dismiss();


            }
        });
        requestQueue.add(jsonObjectRequest);
    }


}
