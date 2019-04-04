package com.example.finalmufixapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.finalmufixapp.Models.Comment_Model;
import com.example.finalmufixapp.R;

import java.util.ArrayList;

public class Comments_Adapter extends RecyclerView.Adapter<Comments_Adapter.C_holder> {

    class C_holder extends RecyclerView.ViewHolder{
        ImageView C_P_Image;
        TextView C_Username,C_text;

        public C_holder(@NonNull View itemView) {
            super(itemView);
            C_P_Image=itemView.findViewById(R.id.Comment_P_Image);
            C_Username=itemView.findViewById(R.id.Comment_Username);
            C_text=itemView.findViewById(R.id.Comment_text);


        }
    }
Context context;
    ArrayList<Comment_Model>list;

    public Comments_Adapter(Context context,ArrayList<Comment_Model>list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public C_holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view= LayoutInflater.from(context).inflate(R.layout.comment_item,null);
        return new C_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull C_holder c_holder, int i) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



}
