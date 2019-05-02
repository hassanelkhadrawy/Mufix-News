package com.example.finalmufixapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;

import java.util.ArrayList;

public class Profile_Post_Adaptr extends RecyclerView.Adapter<Profile_Post_Adaptr.profile_view_holder> {

    public static class profile_view_holder extends RecyclerView.ViewHolder {
        TextView post_Tittle;


        public profile_view_holder(View home_itemView) {
            super(home_itemView);


            post_Tittle = home_itemView.findViewById(R.id.p_i_post);


        }
    }


    Context context;
    ArrayList<Post_Model> post_info_list;

    public Profile_Post_Adaptr(Context context, ArrayList<Post_Model> post_info_list) {
        this.context = context;
        this.post_info_list = post_info_list;
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
        Toast.makeText(context, ""+post_info_list.size(), Toast.LENGTH_SHORT).show();



    }

    @Override
    public int getItemCount() {
        return post_info_list.size();
    }




}
