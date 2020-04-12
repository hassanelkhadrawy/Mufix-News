package com.example.finalmufixapp.Activites;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Adapters.User_Adapter;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class User extends AppCompatActivity implements User_Adapter.ClickListener, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView userRecycler;
    private SwipeRefreshLayout refreshLayout;
    private Toolbar mTopToolbar;
    private Progress_Dialog progress_dialog;
    private ArrayList<Post_Model> Post_List = new ArrayList<>();
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Posts.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initView();
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void on_P_Img_Click(int position) {

    }

    private void initView() {
        mTopToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setTitleTextColor(getResources().getColor(R.color.whihte));
        userRecycler = (RecyclerView) findViewById(R.id.user_recycler);
        progress_dialog = new Progress_Dialog(User.this);
        progress_dialog.SweetAlertDialog();
        refreshLayout = findViewById(R.id.user_rfreshmain);
        refreshLayout.setOnRefreshListener(this);
        refreshLayout.setColorSchemeColors(Color.parseColor("#ee6e2c"));

    }

    @Override
    protected void onStart() {
        super.onStart();
        SELECT_Post_INFO(URL,this);
    }

    void SELECT_Post_INFO(String Post_URL, final Context context) {
        Post_List.clear();
        refreshLayout.setRefreshing(false);
        progress_dialog.pDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Post_URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("Post_Info");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String getpost_email = getData.getString("email");
                                String getpost_Tittle = getData.getString("tittle");
                                String getText_post = getData.getString("text_post");
                                String get_Image_Post = getData.getString("image_post");
                                String get_Username = getData.getString("username");
                                String get_P_Image = getData.getString("p_image");
                                String get_P_Date = getData.getString("date");
                                String get_P_Time = getData.getString("time");


                                Post_List.add(new Post_Model(getpost_email, get_Username, getpost_Tittle, getText_post, get_Image_Post, get_P_Image, get_P_Date, get_P_Time));

                            }

                            Collections.reverse(Post_List);
                            User_Adapter user_adapter = new User_Adapter(User.this, Post_List, User.this);
                            userRecycler.setLayoutManager(new LinearLayoutManager(User.this));
                            userRecycler.setAdapter(user_adapter);
                            user_adapter.notifyDataSetChanged();


                            progress_dialog.pDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                progress_dialog.pDialog.dismiss();
                Toast.makeText(context, "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @Override
    public void onRefresh() {
        SELECT_Post_INFO(URL,this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.About) {

            startActivity(new Intent(User.this, About.class));
        }


        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, StartActivity.class));
        finish();
    }

}
