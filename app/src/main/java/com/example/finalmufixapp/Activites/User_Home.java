package com.example.finalmufixapp.Activites;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.finalmufixapp.Adapters.Recycler_Post_Adapter;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class User_Home extends Fragment {


    private View User_home_view;
    private RecyclerView Recycler_Item_Post;
    private RequestQueue requestQueue;

    public static ArrayList<Post_Model> Post_List=new ArrayList<>();
    private String URL="https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Posts.php";



    public User_Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        User_home_view =inflater.inflate(R.layout.fragment_user__home, container, false);
        Initi();
        return User_home_view;
    }


    private void Initi(){


        Recycler_Item_Post =User_home_view. findViewById(R.id.recycle_item_post);
        SELECT_Post_INFO();
    }





    private void SELECT_Post_INFO() {
        Post_List.clear();
        requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray jsonArray = response.getJSONArray("Post_Info");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject getData = jsonArray.getJSONObject(i);
                                String  getpost_email = getData.getString("email");
                                String  getpost_Tittle = getData.getString("tittle");
                                String  getText_post = getData.getString("text_post");
                                String  get_Image_Post = getData.getString("image_post");
                                String  get_Username = getData.getString("username");
                                String  get_P_Image = getData.getString("p_image");
                                String  get_P_Date = getData.getString("date");
                                String  get_P_Time = getData.getString("time");



                                Post_List.add(new Post_Model(getpost_email,get_Username,getpost_Tittle,getText_post,get_Image_Post,get_P_Image,get_P_Date,get_P_Time));

                            }

                            Recycler_Post_Adapter recycler_post_adapter=new Recycler_Post_Adapter(getActivity(),Post_List);
                            Recycler_Item_Post.setLayoutManager(new LinearLayoutManager(getActivity()));
                            Recycler_Item_Post.setAdapter(recycler_post_adapter);
                            recycler_post_adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getActivity(), "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }



}
