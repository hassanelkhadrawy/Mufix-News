package com.example.finalmufixapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.Update;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Edit extends AppCompatActivity {
EditText new_name,new_email,_new_password;
ImageView new_P_Img;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
    String encoding;
    private String user_URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getUser_Info.php";

    static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        new_name=findViewById(R.id.new_name);
        new_email=findViewById(R.id.new_email);
        _new_password=findViewById(R.id.new_password);
        new_P_Img=findViewById(R.id.new_profile_circleImageView);
        sharedPreferences=getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        Set_Old_Data();


    }

    private void Set_Old_Data(){
        new_name.setText(sharedPreferences.getString("Username","null"));
        new_email.setText(sharedPreferences.getString("Email","null"));
        _new_password.setText(sharedPreferences.getString("person_password","null"));


        String Img=sharedPreferences.getString("P_Image","null");
        if (Img.equals("null")){
            new_P_Img.setBackgroundResource(R.drawable.ic_person_black_24dp);


        }else {
            Picasso.with(this).load(url+sharedPreferences.getString("P_Image","null")).into(new_P_Img);

        }






    }
    void sendData() {

        Encoding_Image();

        String username=new_name.getText().toString();
        String email=new_email.getText().toString();
        String password=_new_password.getText().toString();
        String oldemail=sharedPreferences.getString("Email","null");

        Response.Listener<String> r_listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Toast.makeText(Edit.this, "done", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(Edit.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        Update update_data = new Update(username, email, password, encoding, oldemail, r_listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Edit.this);
        requestQueue.add(update_data);
        editor.putString("Email",email);
        editor.putString("person_password",password);
        editor.putString("Username",username);
        editor.commit();

        startActivity(new Intent(Edit.this,MainActivity.class));
        finish();
        Toast.makeText(this, " Data updated", Toast.LENGTH_SHORT).show();
    }
    private void get_P_Image(){

        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in, "select picture"), 1);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                new_P_Img.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void Encoding_Image() {
        Bitmap bitmap = ((BitmapDrawable) new_P_Img.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        encoding = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


    }


    public void Send_Update(View view) {
        sendData();
    }

    public void Select_image_action(View view) {
        get_P_Image();
    }


}
