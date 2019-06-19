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
public class User_Home extends Fragment implements Recycler_Post_Adapter.ClickListener, View.OnClickListener, Profile_Post_Adaptr.ClickListener {


    private View User_home_view;
    private RecyclerView Recycler_Item_Post, Profile_recyclerView;
    private RequestQueue requestQueue;
    private View profile_view, full_Post_Layout_View;
    private ImageView full_post_Image_View, Like, Comment, Profile_Imag;
    private TextView Discreption, Profile_Person_Name;
    ArrayList<Post_Model> Profile_post_info_list = new ArrayList<>();
    ArrayList<String> Search_List = new ArrayList<>();

     WaveSwipeRefreshLayout refreshLayout;
    SharedPreferences sharedPreferences;

    Progress_Dialog progress_dialog;
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


        refreshLayout = (WaveSwipeRefreshLayout) User_home_view.findViewById(R.id.rfreshmain);
        sharedPreferences=getActivity().getSharedPreferences("mufix_file", Context.MODE_PRIVATE);

        Recycler_Item_Post = User_home_view.findViewById(R.id.recycle_item_post);
        refresh();

    }


    @Override
    public void onStart() {
        super.onStart();
        SELECT_Post_INFO(URL,getActivity());

    }

    Home home = new Home();

    void SELECT_Post_INFO(String Post_URL, final Context context) {
        Search_List.clear();
        Post_List.clear();
        requestQueue = Volley.newRequestQueue(context);
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

                            Recycler_Post_Adapter recycler_post_adapter = new Recycler_Post_Adapter(context, Post_List, User_Home.this);
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
                Toast.makeText(context, "Faild ", Toast.LENGTH_SHORT).show();
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
                            Profile_Post_Adaptr profile_post_adaptr = new Profile_Post_Adaptr(getActivity(), Profile_post_info_list,User_Home.this);

                            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                            Profile_recyclerView.setLayoutManager(layoutManager);
                            Profile_recyclerView.setAdapter(profile_post_adaptr);
                            profile_post_adaptr.notifyDataSetChanged();

                            Profile_Info(position);
                            progress_dialog.pDialog.dismiss();


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

//        progress_dialog.pDialog.show();
        //chick likes

        //get Post Info

        String Email=sharedPreferences.getString("Email", "no data");

        full_Post(Post_List,position,Email,getActivity());

    }


    // get User Profile
    @Override
    public void on_P_Img_Click(int position) {

        progress_dialog = new Progress_Dialog(getActivity());
        progress_dialog.SweetAlertDialog();
        progress_dialog.pDialog.show();
        profile_view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_profile, null);
        Profile_Imag = profile_view.findViewById(R.id.profile_circleImageView);
        Profile_recyclerView = profile_view.findViewById(R.id.profile_recyclerView);

        Profile_Person_Name = profile_view.findViewById(R.id.profile_person_name);
        SELECT_Prof_Post_INFO(position, Profile_Post_URL, Post_List.get(position).Email);


    }

    @Override
    public void onPostClick(ArrayList<Post_Model> post_info_list, int position) {
        final SharedPreferences preferences = getActivity().getSharedPreferences("mufix", Context.MODE_PRIVATE);
        String Email=preferences.getString("Email", "no data");
        full_Post(post_info_list,position,Email,getActivity());

    }


    @Override
    public void onClick(View v) {


    }

     void full_Post(final ArrayList<Post_Model> post_list, final int position, final String Email, final Context context){

         likes_num(post_list,position,Email,context);


         View view = LayoutInflater.from(context).inflate(R.layout.full_post_item, null);


        BottomSheetDialog dialog = new BottomSheetDialog(context);
        dialog.setContentView(view);

        full_post_Image_View = view.findViewById(R.id.full_post_image_view);

        Discreption = view.findViewById(R.id.full_text_post);
        Like = view.findViewById(R.id.like);
        Comment = view.findViewById(R.id.comment);

        if (post_list.get(position).Image_Post.equals("null")){



            full_post_Image_View.setVisibility(View.GONE);

        }else {
            full_post_Image_View.setVisibility(View.VISIBLE);

            Picasso.with(context).load(url + post_list.get(position).Image_Post).into(full_post_Image_View);

        }


        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send_Like_Data(post_list,position,context);

            }
        });
        Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Comments.class);
                intent.putExtra("Email",Email );
                intent.putExtra("Time", post_list.get(position).Time);
                intent.putExtra("Date", post_list.get(position).Date);
                context.startActivity(intent);

            }
        });

        Discreption.setText(post_list.get(position).Text_Post);
//        SELECT_Post_INFO(URL,context);
        dialog.show();

    }

    private void Send_Like_Data(final ArrayList<Post_Model> post_list, final int position, final Context context) {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Like.setImageResource(R.drawable.fill_like_icon);

                        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Like.setImageResource(R.drawable.like_icon);

                        Toast.makeText(context, "failed" , Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };


        Add_Likes add_post_data = new Add_Likes(sharedPreferences.getString("Email","nodata"), post_list.get(position).Date, post_list.get(position).Time, listener);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(add_post_data);


    }


    void likes_num(final ArrayList<Post_Model> post_list, final int position , final String Email, final Context context) {

        progress_dialog = new Progress_Dialog(context);
        progress_dialog.SweetAlertDialog();

        RequestQueue requestQueue;

        String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getPostsLikes.php?date=" + post_list.get(position).Date + "&time=" + post_list.get(position).Time;
        final StringBuilder text = new StringBuilder();
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("Likes");
//                            numLikes.setText("" + jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);


                                String email = getData.getString("email");
                                text.append(email);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            progress_dialog.pDialog.dismiss();
                        }

                        if (text.toString().isEmpty()){


                        }else {
                            if (text.toString().contains(Email))

                            {

                                Like.setImageResource(R.drawable.fill_like_icon);
                                Like.setEnabled(false);


                            }

                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "chick Internet", Toast.LENGTH_SHORT).show();
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

        if (Post_List.get(position).P_Image.equals("null")){
            Profile_Imag.setBackgroundResource(R.drawable.ic_person_black_24dp);


        }else {
            Picasso.with(getActivity()).load(url + Post_List.get(position).P_Image).into(Profile_Imag);

        }
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

                            SELECT_Post_INFO(URL,getActivity());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                }).start();


            }
        });

    }


public void updaterefreshPosts(){
    SELECT_Post_INFO(URL,getActivity());

}
}
