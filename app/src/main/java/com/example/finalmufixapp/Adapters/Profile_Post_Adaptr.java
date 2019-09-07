package com.example.finalmufixapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.finalmufixapp.Activites.Home;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Profile_Post_Adaptr extends RecyclerView.Adapter<Profile_Post_Adaptr.profile_view_holder> {
    private String DeletPostURL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/DeletePost.php?";

    public static class profile_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post_Tittle;
        ImageView Delete;


        public profile_view_holder(View home_itemView) {
            super(home_itemView);


            post_Tittle = home_itemView.findViewById(R.id.p_i_post);
            Delete = home_itemView.findViewById(R.id.delete);


        }

        @Override
        public void onClick(View v) {

        }

    }


    Context context;
    private static ClickListener clickListener;
    ArrayList<Post_Model> post_info_list;

    public Profile_Post_Adaptr(Context context, ArrayList<Post_Model> post_info_list,ClickListener ClickListener) {
        this.context = context;
        this.post_info_list = post_info_list;
        this.clickListener = ClickListener;

    }

    public interface ClickListener {
        void onPostClick(ArrayList<Post_Model> post_info_list,int position);
        void onDeleteClick(ArrayList<Post_Model> post_info_list,int position);



    }

    @NonNull
    @Override
    public Profile_Post_Adaptr.profile_view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View home_view = LayoutInflater.from(context).inflate(R.layout.profile_item_post, null);
        return new Profile_Post_Adaptr.profile_view_holder(home_view);
    }

    @Override
    public void onBindViewHolder(@NonNull final Profile_Post_Adaptr.profile_view_holder holder, final int position) {


        holder.post_Tittle.setText(post_info_list.get(position).Text_Tittle);

        holder.post_Tittle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onPostClick(post_info_list,position);
            }
        });
        holder.Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onDeleteClick(post_info_list,position);
                DeletePosts(DeletPostURL+"time="+post_info_list.get(position).Time+"&date="+post_info_list.get(position).Date+"&email="+post_info_list.get(position).Email);

            }
        });



    }

    @Override
    public int getItemCount() {
        return post_info_list.size();
    }

    private void DeletePosts(String url){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            boolean success = response.getBoolean("success");

                            if (success){
                                context.startActivity(new Intent(context, Home.class));
                            }else {
                                Toast.makeText(context, "faild", Toast.LENGTH_SHORT).show();

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, "check internet", Toast.LENGTH_SHORT).show();
                Log.d("error",error.getMessage());


            }
        });
        requestQueue.add(jsonObjectRequest);




    }



}
