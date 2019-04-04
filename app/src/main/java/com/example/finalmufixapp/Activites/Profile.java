package com.example.finalmufixapp.Activites;


import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.finalmufixapp.Adapters.Profile_Post_Adaptr;
import com.example.finalmufixapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    RecyclerView P_I_PostList;
    TextView Profile_Person_Name;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    View Profile_view;

    String[] list = {"android", "Linux", "Problem Solving"};
    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Profile_view= inflater.inflate(R.layout.fragment_profile, container, false);
        initial();
        Set_Posts();

        return Profile_view;
    }

    private void initial() {
        P_I_PostList =Profile_view. findViewById(R.id.profile_recyclerView);
        Profile_Person_Name=Profile_view.findViewById(R.id.profile_person_name);
        sharedPreferences=getActivity().getSharedPreferences("mufix_file", Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();

        Profile_Person_Name.setText(sharedPreferences.getString("Username","no data"));

    }

    private void Set_Posts() {


        User_Home user_home=new User_Home();
        Profile_Post_Adaptr profile_post_adaptr = new Profile_Post_Adaptr(getActivity(),user_home.Post_List);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        P_I_PostList.setLayoutManager(layoutManager);
        P_I_PostList.setAdapter(profile_post_adaptr);

    }



}
