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
    private AlertDialog.Builder builder;
    private View full_Post_Layout_View;
    private ImageView full_post_Image_View,Like,Comment;
    private TextView Discreption;
    ArrayList<Post_Model> post_info_list;
    private boolean Flag=false;

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



    }

    @Override
    public int getItemCount() {
        return post_info_list.size();
    }


//    public void Show_Full_Post(int postion) {
//
//        builder = new AlertDialog.Builder(context);
//        full_Post_Layout_View = LayoutInflater.from(context).inflate(R.layout.full_post_item, null);
//        full_post_Image_View=full_Post_Layout_View.findViewById(R.id.full_post_image_view);
//        Discreption=full_Post_Layout_View.findViewById(R.id.full_text_post);
//        Like=full_Post_Layout_View.findViewById(R.id.like);
//
//
//        builder.setView(full_Post_Layout_View);
//        builder.setCancelable(false);
//
//        if (Flag==true){
//            Like.setBackgroundResource(R.drawable.fill_like_icon);
//        }else {
//            Like.setBackgroundResource(R.drawable.like_icon);
//
//        }
//
//        Picasso.with(context).load(Recycler_Post_Adapter.view_holder.url+post_info_list.get(postion).P_Image).into(full_post_Image_View);
//        Discreption.setText(post_info_list.get(postion).Text_Post);
//
//
//        Like.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (Flag==false){
//                    Like.setBackgroundResource(R.drawable.fill_like_icon);
//                    Flag=true;
//
//
//                }else {
//                    Like.setBackgroundResource(R.drawable.like_icon);
//                    Flag=false;
//
//                }
//
//            }
//        });
//
//        builder.setNegativeButton("Hide", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//            }
//        });
//        builder.show();
//
//    }

}
