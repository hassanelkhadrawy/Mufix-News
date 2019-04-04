package com.example.finalmufixapp.Activites;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.finalmufixapp.Adapters.MyPagerAdapter;
import com.example.finalmufixapp.R;


public class MainActivity extends AppCompatActivity {
    private MyPagerAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new SignIn(), "SignIn");
        adapter.addFragment(new SignUp(), "SignUp");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
