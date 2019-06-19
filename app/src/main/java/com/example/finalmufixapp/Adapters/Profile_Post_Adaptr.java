package com.example.finalmufixapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Profile_Post_Adaptr extends RecyclerView.Adapter<Profile_Post_Adaptr.profile_view_holder> {
    static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";

    public static class profile_view_holder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView post_Tittle;


        public profile_view_holder(View home_itemView) {
            super(home_itemView);


            post_Tittle = home_itemView.findViewById(R.id.p_i_post);


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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                clickListener.onPostClick(post_info_list,position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return post_info_list.size();
    }

private void prof_post( int position){
   View profile_view = LayoutInflater.from(context).inflate(R.layout.fragment_profile, null);

    ImageView Profile_Imag = profile_view.findViewById(R.id.profile_circleImageView);
    BottomSheetDialog dialog = new BottomSheetDialog(context);
    dialog.setContentView(profile_view);
    TextView Profile_Person_Name = profile_view.findViewById(R.id.profile_person_name);
    Profile_Person_Name.setText(post_info_list.get(position).Username);

    if (post_info_list.get(position).P_Image!="null"){
        Picasso.with(context).load(url + post_info_list.get(position).P_Image).into(Profile_Imag);

    }
    dialog.show();

}


}
