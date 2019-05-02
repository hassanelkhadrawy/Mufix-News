package com.example.finalmufixapp.Activites;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Activites.Home;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.ADD_USER;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignUp extends Fragment {

    //ImageView PERSONAL_IMAGE;
    View LayoutView;
    FloatingActionButton SignUp;
    EditText Person_Name,Email,Password;
    Button Submit;
    //String  Image_String;
    RequestQueue requestQueue;
    private SweetAlertDialog pDialog;


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
        SweetAlertDialog();

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Person_Name.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "enter your name", Toast.LENGTH_SHORT).show();

                }else if (Email.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "enter your Email", Toast.LENGTH_SHORT).show();


                }else if (Password.getText().toString().isEmpty()){
                    Toast.makeText(getActivity(), "enter your Password", Toast.LENGTH_SHORT).show();


                }else if (Patterns.EMAIL_ADDRESS.matcher(Email.getText().toString()).matches()){
                    Toast.makeText(getActivity(), "enter correct Email", Toast.LENGTH_SHORT).show();


                }else if (Password.getText().toString().length() >6){
                    Toast.makeText(getActivity(), "password very weak", Toast.LENGTH_SHORT).show();


                }else {
                    pDialog.show();

                    Send_Person_Data();

                }


            }
        });

//        Submit=findViewById(R.id.Submit);
//        Select_P_Image();

//        Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Encoding_Image_To_String();
//            }
//        });




    }


    private void Send_Person_Data(){

        String username=Person_Name.getText().toString();
        String email=Email.getText().toString();
        String password=Password.getText().toString();






        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject=new JSONObject(response);


                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getActivity(), Home.class));
                        getActivity().finish();


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



//    private void Select_P_Image(){
//
//        PERSONAL_IMAGE.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent in = new Intent();
//                in.setType("image/*");
//                in.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(in, "select picture"), 1);
//
//            }
//        });
//
//
//
//    }
//
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
//
//            Uri uri = data.getData();
//
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
//                PERSONAL_IMAGE.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    private void SweetAlertDialog(){

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }




}
