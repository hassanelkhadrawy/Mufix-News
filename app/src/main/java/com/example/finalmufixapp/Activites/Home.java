package com.example.finalmufixapp.Activites;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.finalmufixapp.Send_Data.Add_Post_Data;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Home extends AppCompatActivity implements Recycler_Post_Adapter.ClickListener, View.OnClickListener, Profile_Post_Adaptr.ClickListener {

    private TextView mTextMessage;
    private FragmentTransaction fragmentTransaction;
    private User_Home user_home;
    private Profile profile;

    private ImageView Image_Poast_Container;

    private Button UpLoad_Image;
    private EditText Text_Post, Post_Tittle;
    private View Post_Layout_View;
    private RecyclerView Recycler_Item_Post,Search_Recycler_Item_Post;
    private RequestQueue requestQueue;
    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private View proofile_view;
    private ImageView Profile_Imag, bar_P_image;
    private TextView Profile_Person_Name;
    boolean list_Flag = false;


    private ArrayList<Post_Model> Profile_post_info_list = new ArrayList<>();
    private ArrayList<Post_Model> Post_List = new ArrayList<>();


    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    AutoCompleteTextView autocomplete;
    SearchView searchView;
    private Toolbar mTopToolbar;
    private String Image_String;
    private boolean Flag = false;
    private AlertDialog.Builder builder;
    DialogInterface dialog;
    SweetAlertDialog pDialog;
    private String URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Posts.php";
    static String url = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/images/";
    private String Profile_Post_URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/getSpecialPost.php?email=";
    private String Search_URL = "https://hassan-elkhadrawy.000webhostapp.com/mufix_app/phpfiles/Search.php?post_tittle=";
//    String[] arr = { "android,java", "paython","php,embeded",
//            "embeded system,iot"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mTopToolbar = findViewById(R.id.my_toolbar);
        bar_P_image=findViewById(R.id.presonal_bar_imge_post);
        setSupportActionBar(mTopToolbar);
        mTopToolbar.setTitleTextColor(getResources().getColor(R.color.whihte));
        SweetAlertDialog();
        user_home = new User_Home();
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.Container_Activities, user_home, "Registration");
        fragmentTransaction.commit();
        sharedPreferences = getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        searchView = findViewById(R.id.search_view);
        listView = findViewById(R.id.listView);
        listView.setVisibility(View.GONE);

       String p_image_name=sharedPreferences.getString("P_Image","null");
        if (p_image_name.equals("null")){

            bar_P_image.setBackgroundResource(R.drawable.ic_person_black_24dp);

        }else {
            Picasso.with(Home.this).load(url +p_image_name).into(bar_P_image);

        }


        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, user_home.Search_List) {

            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = view.findViewById(android.R.id.text1);
                tv.setTextColor(Color.WHITE);
                return view;
            }
        };
        listView.setAdapter(arrayAdapter);

        SearchView.SearchAutoComplete searchAutoComplete = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHintTextColor(getResources().getColor(android.R.color.white));
        searchAutoComplete.setTextColor(getResources().getColor(android.R.color.white));
        ImageView searchIcon = searchView.findViewById(android.support.v7.appcompat.R.id.search_button);
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this,R.drawable.ic_search_black_24dp));

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listView.setVisibility(View.VISIBLE);
                list_Flag = true;
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                arrayAdapter.getFilter().filter(s);

                return false;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                listView.setVisibility(View.GONE);
                list_Flag = false;

                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



                Search_Posts(Search_URL + arrayAdapter.getItem(position));
                listView.setVisibility(View.GONE);


            }
        });

//        autocomplete = (AutoCompleteTextView)
//                findViewById(R.id.autoCompleteTextView1);
//        search=findViewById(R.id.search);
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>
//                (this,android.R.layout.select_dialog_item,list);
//
//        autocomplete.setThreshold(2);
//        autocomplete.setAdapter(adapter);
//        autocomplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(Home.this, ""+autocomplete.getListSelection(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.add(autocomplete.getText().toString());
//                Toast.makeText(Home.this, ""+list, Toast.LENGTH_SHORT).show();
//
//            }
//        });
//

    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:


//                    getFragmentManager().beginTransaction().remove(profile).commit();


                    return true;
                case R.id.navigation_dashboard:

                    try {
                        Create_POST_VIEW();

                    }catch (Exception e){

                    }
                    return true;
                case R.id.navigation_notifications:
                    try {
                        profile = new Profile();

                        pDialog.show();
                        SELECT_Prof_Post_INFO(Profile_Post_URL, sharedPreferences.getString("Email", "null"));

                    }catch (Exception e){

                    }
//                    fragmentTransaction = getFragmentManager().beginTransaction();
//                    fragmentTransaction.add(R.id.Container_Activities, profile, "Registration");
////                    fragmentTransaction.hide(user_home);
//                    fragmentTransaction.commit();



                    return true;
            }
            return false;
        }
    };


    private void Create_POST_VIEW() {
        Image_String = "null";


        builder = new AlertDialog.Builder(this);
        Post_Layout_View = LayoutInflater.from(this).inflate(R.layout.post_layout, null);

        UpLoad_Image = Post_Layout_View.findViewById(R.id.upload_image);
        Image_Poast_Container = Post_Layout_View.findViewById(R.id.image_post_container);
        Text_Post = Post_Layout_View.findViewById(R.id.text_post);
        Post_Tittle = Post_Layout_View.findViewById(R.id.post_tittle);

        builder.setView(Post_Layout_View);
        builder.setCancelable(false);
        builder.setPositiveButton("Upload", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialog = dialogInterface;
                pDialog.show();


                Send_Post_Data();


            }
        });

        builder.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();


        UpLoad_Image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SELECT_IMAGE();
            }
        });


    }

    private void SELECT_IMAGE() {
        Intent in = new Intent();
        in.setType("image/*");
        in.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(in, "select picture"), 1);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();


            try {
                Flag = true;
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                Image_Poast_Container.setImageBitmap(bitmap);
                Image_Poast_Container.setVisibility(View.VISIBLE);
                Encoding_Image_To_String();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    private void Send_Post_Data() {


        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        pDialog.dismiss();

                        Toast.makeText(Home.this, "done", Toast.LENGTH_SHORT).show();
                        //Create_POST_VIEW();
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(Home.this, "failed", Toast.LENGTH_SHORT).show();
                        Create_POST_VIEW();
                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        };

        Calendar c = Calendar.getInstance();
        System.out.println("Current time => " + c.getTime());

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat tf = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = tf.format(c.getTime());
        Add_Post_Data add_post_data = new Add_Post_Data(sharedPreferences.getString("Email", "null")
                , Post_Tittle.getText().toString(), Text_Post.getText().toString(), Image_String, "" + formattedDate, formattedTime, listener);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(add_post_data);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.Setting) {
            chik_user();
        } else if (id == R.id.Logout) {
            editor.putString("Email", "");
            editor.putString("person_password", "");
            editor.putString("Username", "");
            editor.putString("P_Image", "");

            editor.commit();
            startActivity(new Intent(Home.this,MainActivity.class));
            finish();


        }else if (id==R.id.About){

            startActivity(new Intent(Home.this,About.class));
        }


        return super.onOptionsItemSelected(item);
    }

    private void Encoding_Image_To_String() {

        Bitmap bitmap = ((BitmapDrawable) Image_Poast_Container.getDrawable()).getBitmap();
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 75, byteArrayOutputStream);
        Image_String = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);


    }

    private void SELECT_Prof_Post_INFO(String Post_URL, String Email) {
        Profile_post_info_list.clear();
        requestQueue = Volley.newRequestQueue(Home.this);
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
                            Profile_Post_Adaptr profile_post_adaptr = new Profile_Post_Adaptr(Home.this, Profile_post_info_list,Home.this);
                            Owner_Profile();
                            LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this, LinearLayoutManager.HORIZONTAL, false);
                            Recycler_Item_Post.setLayoutManager(layoutManager);
                            Recycler_Item_Post.setAdapter(profile_post_adaptr);
                            profile_post_adaptr.notifyDataSetChanged();

                            //progress_dialog.pDialog.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //progress_dialog.pDialog.dismiss();
                pDialog.dismiss();
                Toast.makeText(Home.this, "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }


    private void SweetAlertDialog() {

        pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(true);

    }

    private void Owner_Profile() {
        proofile_view = LayoutInflater.from(Home.this).inflate(R.layout.owner_profile, null);
        Profile_Imag = proofile_view.findViewById(R.id.owner_profile_circleImageView);
        Profile_Person_Name = proofile_view.findViewById(R.id.owner_profile_person_name);
        Recycler_Item_Post = proofile_view.findViewById(R.id.owner_profile_recyclerView);
        Profile_Person_Name.setText(sharedPreferences.getString("Username", "null"));
        if (sharedPreferences.getString("P_Image", "null").equals("null")){

        }else {
            Picasso.with(Home.this).load(url + sharedPreferences.getString("P_Image", "null")).into(Profile_Imag);

        }

        BottomSheetDialog dialog = new BottomSheetDialog(Home.this);
        dialog.setContentView(proofile_view);

        dialog.show();
        pDialog.dismiss();

    }

    private void chik_user() {
        View view = LayoutInflater.from(Home.this).inflate(R.layout.check_user, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText email = view.findViewById(R.id.chick_email);
        final EditText password = view.findViewById(R.id.chick_password);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (email.getText().toString().isEmpty()){
                    Toast.makeText(Home.this, "enter email", Toast.LENGTH_SHORT).show();


                }else if (password.getText().toString().isEmpty()){
                    Toast.makeText(Home.this, "enter password", Toast.LENGTH_SHORT).show();

                }else if (sharedPreferences.getString("Email", "no").equals(email.getText().toString())
                        && sharedPreferences.getString("person_password", "no").equals(password.getText().toString())) {

                    startActivity(new Intent(Home.this, Edit.class));
                    finish();
                } else {
                    Toast.makeText(Home.this, "wrong data", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setView(view);
        builder.show();
    }

    void SELECT_Post_INFO(String Post_URL) {

        Post_List.clear();
        requestQueue = Volley.newRequestQueue(Home.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Post_URL,
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


                                Post_List.add(new Post_Model(getpost_email, get_Username, getpost_Tittle, getText_post, get_Image_Post, get_P_Image, get_P_Date, get_P_Time));

                            }

                            Recycler_Post_Adapter recycler_post_adapter = new Recycler_Post_Adapter(Home.this, Post_List, Home.this);
                            Search_Recycler_Item_Post.setLayoutManager(new LinearLayoutManager(Home.this));
                            Search_Recycler_Item_Post.setAdapter(recycler_post_adapter);
                            recycler_post_adapter.notifyDataSetChanged();

                            //                          home.pDialog.dismiss();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                //             home.pDialog.dismiss();
                Toast.makeText(Home.this, "Faild ", Toast.LENGTH_SHORT).show();
                error.printStackTrace();

            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    void Search_Posts(String Post_url){

        View view = LayoutInflater.from(Home.this).inflate(R.layout.post_item, null);
        Search_Recycler_Item_Post=view.findViewById(R.id.search_recycler);


        BottomSheetDialog dialog = new BottomSheetDialog(Home.this);
        dialog.setContentView(view);
        SELECT_Post_INFO(Post_url);
        dialog.show();


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {

        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            listView.setVisibility(View.GONE);
            user_home.SELECT_Post_INFO(URL,Home.this);
        } else {

            Exit();
        }

    }


    void Exit() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void on_P_Img_Click(int position) {


    }

    @Override
    public void onPostClick(ArrayList<Post_Model> post_info_list, int position) {
        User_Home user_home=new User_Home();
        String Email=sharedPreferences.getString("Email","null");
        user_home.full_Post(post_info_list,position,Email,Home.this);

    }
}