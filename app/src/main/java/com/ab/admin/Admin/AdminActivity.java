package com.ab.admin.Admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.ab.admin.Admin.Fragments.AdapterFragmentsAdmin;
import com.ab.admin.R;
import com.google.android.material.tabs.TabLayout;

public class AdminActivity extends AppCompatActivity {
    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager viewPager;
    AdapterFragmentsAdmin fragmentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        toolbar = findViewById(R.id.toolBaradmin);
        tabLayout = findViewById(R.id.tablayoutadmin);
        viewPager = findViewById(R.id.viewpageradmin);
        setSupportActionBar(toolbar);
        tabLayout.addTab(tabLayout.newTab().setText("Approved Shops"));
        tabLayout.addTab(tabLayout.newTab().setText("Requested Shops"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentAdapter = new AdapterFragmentsAdmin(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(fragmentAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
}