package com.e.rebelfoods.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.e.rebelfoods.fragments.FavoriteFragment;
import com.e.rebelfoods.fragments.UsersFragment;

public class UsersViewPagerAdapter extends FragmentPagerAdapter {

    private int fragCount;
    private UsersFragment usersFragment;
    private FavoriteFragment favoriteFragment;

    public UsersViewPagerAdapter(FragmentManager fm, int fragCount) {
        super(fm);
        this.fragCount = fragCount;
        usersFragment = new UsersFragment();
        favoriteFragment = new FavoriteFragment();
    }


    @Override
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return usersFragment;
            case 1:
                return favoriteFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return fragCount;
    }
}
