package com.e.rebelfoods.fragments;


import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.e.rebelfoods.OnFavClickInterface;
import com.e.rebelfoods.R;
import com.e.rebelfoods.adapters.FavoriteAdapter;
import com.e.rebelfoods.model.User;
import com.e.rebelfoods.viewmodel.UsersViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements OnFavClickInterface {

    private UsersViewModel mViewModel;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private LinearLayout favListEmpty;
    
    public FavoriteFragment() {
    }

    public static FavoriteFragment newInstance() {
        return new FavoriteFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        recyclerView = view.findViewById(R.id.user_recycler_view);
        favListEmpty = view.findViewById(R.id.fav_list_empty);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);

        mViewModel.setUsersFromNetwork(false);
        mViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

                if (users!=null && users.size()>0) {
                    favoriteAdapter = new FavoriteAdapter(users, getContext());
                    favoriteAdapter.setOnFavClickInterface(FavoriteFragment.this);
                    recyclerView.setAdapter(favoriteAdapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

                    favListEmpty.setVisibility(View.GONE);
                }else
                    favListEmpty.setVisibility(View.VISIBLE);

            }
        });
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mViewModel!=null)
        mViewModel.setUsersFromNetwork(false);

    }

    public FavoriteAdapter getFavoriteAdapter() {
        return favoriteAdapter;
    }

    public LinearLayout getFavListEmpty() {
        return favListEmpty;
    }

    @Override
    public void onClick(boolean isListEmpty) {
        if (isListEmpty)
            favListEmpty.setVisibility(View.VISIBLE);
        else
            favListEmpty.setVisibility(View.GONE);

    }
}