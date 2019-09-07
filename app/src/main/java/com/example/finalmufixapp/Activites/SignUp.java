package com.example.finalmufixapp.Activites;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Activites.Home;
import com.example.finalmufixapp.Models.P_Info_Model;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.ADD_USER;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {

    //ImageView PERSONAL_IMAGE;
    View LayoutView;
    FloatingActionButton SignUp;
    EditText Person_Name,Email,Password;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Button Submit;
    //String  Image_String;
    RequestQueue requestQueue;
    private SweetAlertDialog pDialog;
    AlertDialog.Builder alertDialog;
    String ChickEmailURL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/check_email.php?email=";
String verificationCodeURL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/verification.php?email=";


    public SignUp() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
          LayoutView=inflater.inflate(R.layout.fragment_sign_up, container, false);
          inti();

        return LayoutView;
    }


    private void inti(){

//        PERSONAL_IMAGE=findViewById(R.id.personimage);
        Person_Name=LayoutView.findViewById(R.id.Person_Name);
        Email=LayoutView.findViewById(R.id.Email);
        Password=LayoutView.findViewById(R.id.Password);
        SignUp=LayoutView.findViewById(R.id.fab);
        sharedPreferences=getActivity().getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        alertDialog = new AlertDialog.Builder(getActivity());

        SweetAlertDialog();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass=Password.getText().toString();

                if (Person_Name.getText().toString().isEmpty()){
                    Person_Name.setError("enter your name");
                    Person_Name.requestFocus();
                }else if (Email.getText().toString().isEmpty()){
                    Email.setError("enter email");
                    Email.requestFocus();

                }else if (Password.getText().toString().isEmpty()){
                    Password.setError("enter password");
                    Password.requestFocus();

                }else if (pass.length() < 6){
                    Password.setError("at least 6 letters");
                    Password.requestFocus();

                }else {
                    boolean emai_valid=isEmailValid(Email.getText().toString());
                    if (emai_valid){

                        pDialog.show();
                        chickEmail(Email.getText().toString());

                    }else {
                        Email.setError("enter valid email");
                        Email.requestFocus();
                    }


                }


            }
        });





    }


    private void Send_Person_Data(){

        pDialog.show();
        final String username=Person_Name.getText().toString();
        final String email=Email.getText().toString();
        final String password=Password.getText().toString();


        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    boolean success = jsonObject.getBoolean("success");
                    if (success) {

                        editor.putString("Email",email);
                        editor.putString("person_password",password);
                        editor.putString("Username",username);
                        editor.putString("P_Image","null");

                        editor.commit();
                        startActivity(new Intent(getActivity(), Home.class));
                        getActivity().finish();
                        pDialog.dismiss();


                    } else {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };



        ADD_USER add_user=new ADD_USER(username,email,password,listener);
        requestQueue= Volley.newRequestQueue(getActivity());
        requestQueue.add(add_user);



    }



    private void chickEmail(final String Emial){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, ChickEmailURL+Emial,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                                int code = response.getInt("code");
                                if (code == 0){

                                    VerificationCode(Emial);
                                }else {
                                    pDialog.dismiss();
                                    Email.setError("email already exist");
                                    Email.requestFocus();
                                }







                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "check internet", Toast.LENGTH_SHORT).show();
                //progress_dialog.pDialog.dismiss();


            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    private void VerificationCode(String Emial){

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, verificationCodeURL+Emial,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            int code = response.getInt("code");

                            pDialog.dismiss();
                            Log.d("code",""+code);
                                checkCodeAliart(code);








                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getActivity(), "check internet", Toast.LENGTH_SHORT).show();
                //progress_dialog.pDialog.dismiss();


            }
        });
        requestQueue.add(jsonObjectRequest);




    }

    @SuppressLint("ResourceAsColor")
    private void checkCodeAliart(final int code) {

        final EditText txt = new EditText(getActivity());
        txt.setHint("Verification Code");
        txt.setPadding(20,10,0,10);
        txt.setWidth(100);
        txt.setHeight(100);
        txt.setTextColor(R.color.orange);
        alertDialog.setView(txt);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", null);
        alertDialog.setNegativeButton("Cancel", null);
        final AlertDialog mAlertDialog = alertDialog.create();
        mAlertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(final DialogInterface dialogInterface) {
                Button Positive = mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button Cancel = mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);

                Positive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (txt.getText().toString().isEmpty()) {
                            txt.setError("enter code");
                            txt.requestFocus();
                        } else {

                            int numberCode = Integer.parseInt(txt.getText().toString());
                            if (numberCode == code) {

                                Send_Person_Data();



                            } else {
                                txt.setError("wrong number");
                                txt.requestFocus();
                            }
                        }
                    }
                });

                Cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mAlertDialog.dismiss();

                    }
                });
            }
        });

        mAlertDialog.show();
        mAlertDialog.getWindow().setBackgroundDrawableResource(R.drawable.card_back_without_border);

    }

    private void SweetAlertDialog(){

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }


}
