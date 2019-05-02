package com.example.finalmufixapp.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
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
import com.example.finalmufixapp.Activites.Comments;
import com.example.finalmufixapp.Models.Post_Model;
import com.example.finalmufixapp.R;
import com.example.finalmufixapp.Send_Data.Add_Likes;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Recycler_Post_Adapter extends RecyclerView.Adapter<Recycler_Post_Adapter.view_holder> {


    public static class view_holder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView person_name, post_Tittle,time,date;
        ImageView Personal_Image;
        static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";


        public view_holder(View itemView) {
            super(itemView);


            person_name = itemView.findViewById(R.id.post_person_name);
            post_Tittle = itemView.findViewById(R.id.post_tittle);
            Personal_Image = itemView.findViewById(R.id.presonal_imge_post);
            time = itemView.findViewById(R.id.time);
            date = itemView.findViewById(R.id.date);



        }

        @Override
        public void onClick(View v) {

        }
    }


    Context context;
    private AlertDialog.Builder builder;
    private View full_Post_Layout_View;
    private ImageView full_post_Image_View,Like,Comment;
    private TextView Discreption;
    ArrayList<Post_Model> post_info_list;
    private static ClickListener clickListener;


    public Recycler_Post_Adapter(Context context, ArrayList<Post_Model> post_info_list, ClickListener cardClickListener) {
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
    public view_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_item_2, null);
        return new view_holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final view_holder holder, final int position) {


        holder.person_name.setText(post_info_list.get(position).Username);
        holder.post_Tittle.setText(post_info_list.get(position).Text_Tittle);
        holder.time.setText(post_info_list.get(position).Time);
        holder.date.setText(post_info_list.get(position).Date);

        if ( post_info_list.get(position).P_Image.equals("null")){

        }else {
            Picasso.with(context).load(holder.url + post_info_list.get(position).P_Image).into(holder.Personal_Image);


        }




        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickListener.onItemClick(position);
//                Show_Full_Post(position);
            }
        });

        holder.Personal_Image.setOnClickListener(new View.OnClickListener() {
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


    public void Show_Full_Post(final int postion) {

        builder = new AlertDialog.Builder(context);
        full_Post_Layout_View = LayoutInflater.from(context).inflate(R.layout.full_post_item, null);
           full_post_Image_View=full_Post_Layout_View.findViewById(R.id.full_post_image_view);

           Discreption=full_Post_Layout_View.findViewById(R.id.full_text_post);
           Like=full_Post_Layout_View.findViewById(R.id.like);
           Comment=full_Post_Layout_View.findViewById(R.id.comment);
        builder.setView(full_Post_Layout_View);
        builder.setCancelable(false);

        Picasso.with(context).load(view_holder.url+post_info_list.get(postion).Image_Post).into(full_post_Image_View);

        Discreption.setText(post_info_list.get(postion).Text_Post);

        builder.setNegativeButton("Hide", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                likes_num(postion);

           dialogInterface.cancel();
            }
        });
        Like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Send_Like_Data(postion);
            }
        });
        Comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, Comments.class);
                intent.putExtra("Email",post_info_list.get(postion).Email);
                intent.putExtra("Time",post_info_list.get(postion).Time);
                intent.putExtra("Date",post_info_list.get(postion).Date);
                context.startActivity(intent);


                // formattedDate have current date/time

            }
        });


        builder.show();

    }

    private void Send_Like_Data(final int postion){


        Response.Listener<String> listener =new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject=new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        Like.setImageResource(R.drawable.fill_like_icon);

                        Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();
                    } else {
                        Like.setImageResource(R.drawable.like_icon);

                        Toast.makeText(context, "failed"+post_info_list.get(postion).Email, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };


        Add_Likes add_post_data=new Add_Likes(post_info_list.get(postion).Email,post_info_list.get(postion).Date,post_info_list.get(postion).Time,listener);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(add_post_data);


        Toast.makeText(context, "running  "+post_info_list.get(postion).Email, Toast.LENGTH_SHORT).show();






    }


    void likes_num( int position) {
        Toast.makeText(context, "" + post_info_list.get(position).Date, Toast.LENGTH_SHORT).show();

        final SharedPreferences preferences=context.getSharedPreferences("mufix",Context.MODE_PRIVATE);
RequestQueue requestQueue;

        String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getPostsLikes.php?date="+ post_info_list.get(position).Date+"&time="+post_info_list.get(position).Time;
        final StringBuilder text = new StringBuilder();
        requestQueue = Volley.newRequestQueue(context);
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
                        if (text.toString().contains(preferences.getString("Email","no data")));

                        {
                            Like.setImageResource(R.drawable.fill_like_icon);
                            Like.setEnabled(false);


                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Errrrrrrrrrrrrrrrrrrrrrrorrr", Toast.LENGTH_SHORT).show();
                Log.e("VOLLEY", "Errrrrrrrrrrrrrrrrrrrrrrorrr");
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }



}
