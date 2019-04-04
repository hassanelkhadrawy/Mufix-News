package com.example.finalmufixapp.Activites;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;

import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignIn extends Fragment {

    View LayoutView;
    private FloatingActionButton SignIn;
    private EditText Person_Username,Person_password;
    ConstraintLayout relativeLayout;
    private RequestQueue requestQueue;
    private String get_Password, get_Email,get_Username,get_P_Image;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    SweetAlertDialog pDialog;
    private ArrayList<P_Info_Model> Person_Login_List = new ArrayList<>();
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getUser_Info.php";


    public SignIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         LayoutView= inflater.inflate(R.layout.fragment_sign_in, container, false);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Inita();

         return LayoutView;
    }

    private void Inita(){

        Person_Username=LayoutView.findViewById(R.id.username);
        Person_password=LayoutView.findViewById(R.id.P_password);
        relativeLayout=LayoutView.findViewById(R.id.PLAY_PARENT);
        SignIn=LayoutView.findViewById(R.id.floatingActionButton);
        sharedPreferences=getActivity().getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();



        SweetAlertDialog();
        new BackgroundServices().execute();


        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (Person_Login_List.size()==0){
                     new BackgroundServices().execute();

                 }
                    CHECK_PERSON_LOGIN();



            }
        });

    }



    private void CHECK_PERSON_LOGIN() {







        for (int x=0;x< Person_Login_List.size();x++){


            if (Person_Username.getText().toString().equals(Person_Login_List.get(x).P_Email)
                    && Person_password.getText().toString().equals(Person_Login_List.get(x).P_Password) ){

                editor.putString("Email",Person_Username.getText().toString());
                editor.putString("person_password",Person_password.getText().toString());
                editor.putString("Username",get_Username);
                editor.putString("P_Image",get_P_Image);

                editor.commit();
                pDialog.dismiss();
                startActivity(new Intent(getActivity(), Home.class));


            }else {

                if (Person_Login_List.size() -1== x){

                    pDialog.dismiss();
                    Snackbar.make(relativeLayout,"Email or Password Incorrect",Snackbar.LENGTH_SHORT).show();


                }

            }

        }


    }


    private void SweetAlertDialog(){

        pDialog = new SweetAlertDialog(getActivity(), SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }



class BackgroundServices extends AsyncTask<Void,Void,String>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog.show();

    }

    @Override
    protected String doInBackground(Void... voids) {


            Person_Login_List.clear();
        try {


            java.net.URL url = new URL(URL);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

            InputStream is = new BufferedInputStream(con.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {

                sb.append(line + "\n");

            }
            is.close();
            bufferedReader.close();
            con.disconnect();
            return sb.toString().trim();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;






    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);


        if (s!=null){
            try {

            JSONObject object = new JSONObject(s);

            JSONArray jsonArray = object.getJSONArray("User_Info");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject getData = jsonArray.getJSONObject(i);

                get_Username = getData.getString("username");
                get_Email = getData.getString("email");
                get_Password = getData.getString("password");
                get_P_Image = getData.getString("p_image");


                Person_Login_List.add(new P_Info_Model(get_Email, get_Password));

            }

                for (int x=0;x<Person_Login_List.size();x++){

                    if (sharedPreferences.getString("Email","no data").equals(Person_Login_List.get(x).P_Email)
                            &&sharedPreferences.getString("person_password","no data").equals(Person_Login_List.get(x).P_Password )){

                        pDialog.dismiss();

                        startActivity(new Intent(getActivity(), Home.class));

                    }else {

                        if (Person_Login_List.size() -1== x){

                            pDialog.dismiss();


                        }


                    }

                }



            }catch (Exception e){


            }

        }else {
            Snackbar.make(relativeLayout,"chech internet",Snackbar.LENGTH_SHORT).show();

        }




    }
}

}
