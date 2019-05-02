package com.example.finalmufixapp.Activites;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.finalmufixapp.Adapters.Recycler_Post_Adapter;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.Add_Likes;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;
import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_Home extends Fragment implements Recycler_Post_Adapter.ClickListener, View.OnClickListener {


    private View User_home_view;
    private RecyclerView Recycler_Item_Post, Profile_recyclerView;
    private RequestQueue requestQueue;
    private View profile_view, full_Post_Layout_View;
    private ImageView full_post_Image_View, Like, Comment, Profile_Imag;
    private TextView Discreption, Profile_Person_Name;
    ArrayList<Post_Model> Profile_post_info_list = new ArrayList<>();
    ArrayList<String> Search_List = new ArrayList<>();

    Progress_Dialog progress_dialog;
    static WaveSwipeRefreshLayout refreshLayout;

    CoordinatorLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    ArrayList<Post_Model> Post_List = new ArrayList<>();
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Posts.php";
    private String Profile_Post_URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getSpecialPost.php?email=";
    static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";


    public User_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        User_home_view = inflater.inflate(R.layout.fragment_user__home, container, false);
        ButterKnife.bind(getActivity());

        Initi();
        return User_home_view;
    }


    private void Initi() {


        progress_dialog = new Progress_Dialog(getActivity());
        refreshLayout = (WaveSwipeRefreshLayout) User_home_view.findViewById(R.id.rfreshmain);

        Recycler_Item_Post = User_home_view.findViewById(R.id.recycle_item_post);
        refresh();

    }


    @Override
    public void onStart() {
        super.onStart();
        SELECT_Post_INFO(URL);

    }

    Home home = new Home();

    void SELECT_Post_INFO(String Post_URL) {
        Search_List.clear();
        Post_List.clear();
        requestQueue = Volley.newRequestQueue(getActivity());
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

                                Search_List.add(getpost_Tittle);
                            }

                            Recycler_Post_Adapter recycler_post_adapter = new Recycler_Post_Adapter(getActivity(), Post_List, User_Home.this);
                            Recycler_Item_Post.setLayoutManager(new LinearLayoutManager(getActivity()));
                            Recycler_Item_Post.setAdapter(recycler_post_adapter);
                            recycler_post_adapter.notifyDataSetChanged();
                            refreshLayout.setRefreshing(false);


                            //                          home.pDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //             home.pDialog.dismiss();
                refreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    void SELECT_Prof_Post_INFO(final int position, String Post_URL, String Email) {
        Profile_post_info_list.clear();
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Post_URL + Email,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("Prof_posts");
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


                                Profile_post_info_list.add(new Post_Model(getpost_email, get_Username, getpost_Tittle, getText_post, get_Image_Post, get_P_Image, get_P_Date, get_P_Time));

                            }
                            Profile_Post_Adaptr profile_post_adaptr = new Profile_Post_Adaptr(getActivity(), Profile_post_info_list);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            Profile_recyclerView.setLayoutManager(layoutManager);
                            Profile_recyclerView.setAdapter(profile_post_adaptr);
                            profile_post_adaptr.notifyDataSetChanged();

                            Profile_Info(position);
                            //progress_dialog.pDialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //progress_dialog.pDialog.dismiss();

                Toast.makeText(getActivity(), "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(final int position) {

        //chick likes
        likes_num(position);

        //get Post Info
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.full_post_item, null);


        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(view);

        full_post_Image_View = view.findViewById(R.id.full_post_image_view);

        Discreption = view.findViewById(R.id.full_text_post);
        Like = view.findViewById(R.id.like);
        Comment = view.findViewById(R.id.comment);


        Picasso.with(getActivity()).load(url + Post_List.get(position).Image_Post).into(full_post_Image_View);

        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send_Like_Data(position);
            }
        });
        Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Comments.class);
                intent.putExtra("Email", Post_List.get(position).Email);
                intent.putExtra("Time", Post_List.get(position).Time);
                intent.putExtra("Date", Post_List.get(position).Date);
                getActivity().startActivity(intent);

            }
        });

        Discreption.setText(Post_List.get(position).Text_Post);
        SELECT_Post_INFO(URL);
        dialog.show();

    }


    // get User Profile
    @Override
    public void on_P_Img_Click(int position) {

//progress_dialog.pDialog.show();

        profile_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_profile, null);
        Profile_Imag = profile_view.findViewById(R.id.profile_circleImageView);
        Profile_recyclerView = profile_view.findViewById(R.id.profile_recyclerView);

        Profile_Person_Name = profile_view.findViewById(R.id.profile_person_name);
        SELECT_Prof_Post_INFO(position, Profile_Post_URL, Post_List.get(position).Email);


    }


    @Override
    public void onClick(View v) {


    }

    private void Send_Like_Data(final int postion) {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Like.setImageResource(R.drawable.fill_like_icon);

                        Toast.makeText(getActivity(), "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Like.setImageResource(R.drawable.like_icon);

                        Toast.makeText(getActivity(), "failed" + Post_List.get(postion).Email, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };


        Add_Likes add_post_data = new Add_Likes(Post_List.get(postion).Email, Post_List.get(postion).Date, Post_List.get(postion).Time, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(add_post_data);


    }


    void likes_num(int position) {

        final SharedPreferences preferences = getActivity().getSharedPreferences("mufix", Context.MODE_PRIVATE);
        RequestQueue requestQueue;

        String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getPostsLikes.php?date=" + Post_List.get(position).Date + "&time=" + Post_List.get(position).Time;
        final StringBuilder text = new StringBuilder();
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Num_Likes");
//                            numLikes.setText("" + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);


                                String username1 = getData.getString("email");
                                text.append(username1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (text.toString().contains(preferences.getString("Email", "no data"))) ;

                        {
                            Like.setImageResource(R.drawable.fill_like_icon);
                            Like.setEnabled(false);


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "chick Internet", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void Profile_Info(int position) {


        BottomSheetDialog dialog = new BottomSheetDialog(getActivity());
        dialog.setContentView(profile_view);
        Profile_Person_Name = profile_view.findViewById(R.id.profile_person_name);
        Profile_Person_Name.setText(Post_List.get(position).Username);

        Picasso.with(getActivity()).load(url + Post_List.get(position).P_Image).into(Profile_Imag);
        dialog.show();

    }

    void refresh() {

        refreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);

                            SELECT_Post_INFO(URL);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });

    }

}
