package com.example.finalmufixapp.Activites;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Adapters.Comments_Adapter;
import com.example.finalmufixapp.Adapters.Recycler_Post_Adapter;
import com.example.finalmufixapp.Models.Comment_Model;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.Add_Comments;
import com.example.finalmufixapp.Send_Data.Add_Likes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Comments extends AppCompatActivity {

   private RecyclerView Comment_Recycler_View;
  private   Bundle bundle;
  private   EditText comment_txt;
  private String Email,Time,Date;
   private ArrayList<Comment_Model>C_List=new ArrayList<>();
    private String URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/get_Comments.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        inti();
    }

    private void inti(){

        bundle=getIntent().getExtras();
        comment_txt=findViewById(R.id.Comment_txt);
        Comment_Recycler_View=findViewById(R.id.Comment_Recycler_item);

        Email=bundle.getString("Email");
        Time=bundle.getString("Time");
        Date=bundle.getString("Date");




    }

    public void Send(View view) {
Send_Comments_Data();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SELECT_Comments_Data();
    }

    private void Send_Comments_Data(){


        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {

                        Toast.makeText(Comments.this, "done", Toast.LENGTH_SHORT).show();
                        comment_txt.setText("");
                        SELECT_Comments_Data();
                    } else {

                        Toast.makeText(Comments.this, "failed", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };


        Add_Comments add_comments=new Add_Comments(Email,comment_txt.getText().toString(),Time,Date,listener);
        RequestQueue requestQueue = Volley.newRequestQueue(Comments.this);
        requestQueue.add(add_comments);

    }


    private void SELECT_Comments_Data() {
      RequestQueue  requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL+"time="+Time+"&date="+Date+"&email="+Email,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("Comment_Info");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String  get_Comment = getData.getString("username");
                                String  get_Username = getData.getString("username");
                                String  get_P_Image = getData.getString("p_image");




                                C_List.add(new Comment_Model(get_Comment,get_Username,get_P_Image));

                            }

                            Comments_Adapter comments_adapter=new Comments_Adapter(Comments.this,C_List);
                            Comment_Recycler_View.setLayoutManager(new LinearLayoutManager(Comments.this));
                            Comment_Recycler_View.setAdapter(comments_adapter);
                            comments_adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(Comments.this, "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }



}
