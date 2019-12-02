package com.android.web.user_navigation;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TabLayout tabLayout = findViewById(R.id.tab_layout);

        TabLayout.Tab tabTopStories = tabLayout.newTab();
        TabLayout.Tab tabTechNews = tabLayout.newTab();
        TabLayout.Tab tabCooking = tabLayout.newTab();

        tabLayout.addTab(tabTopStories.setText(R.string.tab_label1));
        tabLayout.addTab(tabTechNews.setText(R.string.tab_label2));
        tabLayout.addTab(tabCooking.setText(R.string.tab_label3));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                Log.i("TAB_UNSELECTED",tab.getPosition()+"");

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

                Log.i("TAB_RESELECTED",tab.getPosition()+"");

            }
        });
    }
}

//5. Coding challenge
