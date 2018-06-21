package com.thokolo.lerato.draweractivitywithfragments.activities;


import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thokolo.lerato.draweractivitywithfragments.R;

public class TabbedActivity extends AppCompatActivity {


    private SectionsPageAdapter sectionsPageAdapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);
        getSupportActionBar().setTitle("Admin Panel");

        sectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());


        viewPager = findViewById(R.id.viewPager);
        setUpViewPager(viewPager);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


    }

    private void setUpViewPager(ViewPager viewPager){

        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_One(), "Product");
        adapter.addFragment(new Fragment_Two(),"Catego..");
        adapter.addFragment(new Fragment_Three(), "3rd party");
        adapter.addFragment(new Fragment_Four(), "Orders");
        adapter.addFragment(new Fragment_Five(), "App users");

        viewPager.setAdapter(adapter);
    }

}
