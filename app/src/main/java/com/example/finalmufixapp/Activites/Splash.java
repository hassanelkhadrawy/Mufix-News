package com.example.finalmufixapp.Activites;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Adapters.Profile_Post_Adaptr;
import com.example.finalmufixapp.Models.P_Info_Model;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Splash extends AppCompatActivity {
    private ArrayList<P_Info_Model> chickLoginList=new ArrayList<>();
    SharedPreferences sharedPreferences;
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getUser_Info.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
       // final Animation animation_1 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.rotate);
        final Animation animation_2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.antirotate);
        final Animation animation_3 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);
        sharedPreferences=getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        handleSSLHandshake();

        imageView.startAnimation(animation_2);
        animation_2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
//                imageView.startAnimation(animation_1);
//                textView.setText("News");

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

//        animation_1.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                textView.setText("MUFIX News");
//
////                imageView.startAnimation(animation_3);
//
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        SELECT_Login_Info(URL);

    }

    void SELECT_Login_Info(String URL) {
        chickLoginList.clear();
        RequestQueue requestQueue = Volley.newRequestQueue(Splash.this);
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
                Toast.makeText(Splash.this, "chick internet", Toast.LENGTH_SHORT).show();
                //progress_dialog.pDialog.dismiss();


            }
        });
        requestQueue.add(jsonObjectRequest);
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

                    finish();
                    Intent i = new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                }


            }

        }

    }

    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    if (arg0.equalsIgnoreCase("hassan-elkhadrawy.000webhostapp.com")) {
                        return true;

                    } else {
                        return false;
                    }
                }
            });
        } catch (Exception ignored) {
        }
    }


}