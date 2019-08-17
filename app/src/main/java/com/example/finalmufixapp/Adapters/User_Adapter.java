package com.example.finalmufixapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class User_Adapter extends RecyclerView.Adapter<User_Adapter.view_holder> {


    public static class view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView person_name, post_Description, time;
        ImageView Image_Post;
        static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";


        public view_holder(View itemView) {
            super(itemView);


            person_name = itemView.findViewById(R.id.user_name);
            post_Description = itemView.findViewById(R.id.description);
            Image_Post = itemView.findViewById(R.id.img_post);
            time = itemView.findViewById(R.id.dateandtime);



        }

        @Override
        public void onClick(View v) {

        }
    }


    Context context;

    ArrayList<Post_Model> post_info_list;
    private static User_Adapter.ClickListener clickListener;


    public User_Adapter(Context context, ArrayList<Post_Model> post_info_list, User_Adapter.ClickListener cardClickListener) {
        this.context = context;
        this.post_info_list = post_info_list;
        this.clickListener = cardClickListener;

    }

    public interface ClickListener {
        void onItemClick(int position);

        void on_P_Img_Click(int position);

    }

    @NonNull
    @Override
    public User_Adapter.view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item_post, null);
        return new User_Adapter.view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final User_Adapter.view_holder holder, final int position) {


        holder.person_name.setText(post_info_list.get(position).Username);
        holder.post_Description.setText(post_info_list.get(position).Text_Tittle +"\n"+post_info_list.get(position).Text_Post);
        holder.time.setText(post_info_list.get(position).Date+" "+post_info_list.get(position).Time );


        if (post_info_list.get(position).P_Image.equals("null")) {

            holder.Image_Post.setVisibility(View.GONE);

        } else {
            Picasso.with(context).load(holder.url + post_info_list.get(position).P_Image).placeholder(R.drawable.ic_person_black_24dp).into(holder.Image_Post);


        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
            }
        });

        holder.Image_Post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.on_P_Img_Click(position);


            }
        });


    }

    @Override
    public int getItemCount() {
        return post_info_list.size();
    }

}