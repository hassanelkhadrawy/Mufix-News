package com.example.finalmufixapp.Activites;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.Add_Post_Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;
    private FragmentTransaction fragmentTransaction;
    private User_Home user_home;
    private Profile profile;

   private ImageView Image_Poast_Container;

    private Button UpLoad_Image;
    private EditText Text_Post,Post_Tittle;
    private View  Post_Layout_View;
    private RecyclerView Recycler_Item_Post;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    private String Image_String;
    private boolean Flag=false;
    private AlertDialog.Builder builder;
    DialogInterface dialog;
    SweetAlertDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        user_home = new User_Home();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Container_Activities, user_home, "Registration");
        fragmentTransaction.commit();
        sharedPreferences=getSharedPreferences("mufix_file", Context.MODE_PRIVATE);

        SweetAlertDialog();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    getFragmentManager().beginTransaction().remove(profile).commit();


                    return true;
                case R.id.navigation_dashboard:

                    Create_POST_VIEW();
                    return true;
                case R.id.navigation_notifications:
                    profile = new Profile();
                    fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.add(R.id.Container_Activities, profile, "Registration");
//                    fragmentTransaction.hide(user_home);
                    fragmentTransaction.commit();



                    return true;
            }
            return false;
        }
    };



    private void Create_POST_VIEW() {


        builder = new AlertDialog.Builder(this);
        Post_Layout_View = LayoutInflater.from(this).inflate(R.layout.post_layout, null);

        UpLoad_Image = Post_Layout_View.findViewById(R.id.upload_image);
        Image_Poast_Container = Post_Layout_View.findViewById(R.id.image_post_container);
        Text_Post=Post_Layout_View.findViewById(R.id.text_post);
        Post_Tittle=Post_Layout_View.findViewById(R.id.post_tittle);

        builder.setView(Post_Layout_View);
        builder.setCancelable(false);
        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog=dialogInterface;
                pDialog.show();

                if (Flag=false){

                    Image_String="null";
                }
                Send_Post_Data();



            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();


        UpLoad_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_IMAGE();
            }
        });



    }
    private void SELECT_IMAGE() {
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in, "select picture"), 1);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                Flag=true;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Image_Poast_Container.setImageBitmap(bitmap);
                Image_Poast_Container.setVisibility(View.VISIBLE);
                Encoding_Image_To_String();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }




    private void Send_Post_Data(){


        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        pDialog.dismiss();
                        Toast.makeText(Home.this, "done", Toast.LENGTH_SHORT).show();
                        Create_POST_VIEW();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(Home.this, "failed", Toast.LENGTH_SHORT).show();
                        Create_POST_VIEW();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = tf.format(c.getTime());
        Add_Post_Data add_post_data=new Add_Post_Data(sharedPreferences.getString("Email","null")
                ,Post_Tittle.getText().toString()  ,Text_Post.getText().toString(),Image_String,""+formattedDate,formattedTime,listener);
        requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(add_post_data);





    }


    private void Encoding_Image_To_String(){

        Bitmap bitmap = ((BitmapDrawable) Image_Poast_Container.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        Image_String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);



    }
    private void SweetAlertDialog(){

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }

}