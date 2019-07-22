package com.e.rebelfoods;

import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.e.rebelfoods.adapters.FavoriteAdapter;
import com.e.rebelfoods.adapters.UsersViewPagerAdapter;
import com.e.rebelfoods.fragments.FavoriteFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private UsersViewPagerAdapter usersViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setElevation(0);


        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);
        usersViewPagerAdapter = new UsersViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(usersViewPagerAdapter);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                Toast.makeText(MainActivity.this, tab.getText(), Toast.LENGTH_SHORT).show();

                if (tab.getPosition()==0)
                    viewPager.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.lightYellow));
                else
                    viewPager.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.navyblue));

                if (tab.getPosition()==1){
                    Log.d(TAG, "onTabSelected: ");

                    FavoriteAdapter favoriteAdapter = ((FavoriteFragment)usersViewPagerAdapter.getItem(1)).getFavoriteAdapter();
                    if (favoriteAdapter!=null) {
                        favoriteAdapter.notifyDataSetChanged();
                        Log.d(TAG, "onTabSelected: ");
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }
}
