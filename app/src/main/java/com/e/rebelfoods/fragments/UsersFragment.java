package com.e.rebelfoods.fragments;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.rebelfoods.adapters.UsersAdapter;
import com.e.rebelfoods.model.User;
import com.e.rebelfoods.viewmodel.UsersViewModel;
import com.e.rebelfoods.R;

import java.util.List;

public class UsersFragment extends Fragment {

    private UsersViewModel mViewModel;
    private RecyclerView recyclerView;
    private UsersAdapter usersAdapter;
    private LinearLayout noInternetConnectionAvailableLayout;

    public static UsersFragment newInstance() {
        return new UsersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.users_fragment, container, false);
        recyclerView = view.findViewById(R.id.user_recycler_view);
        noInternetConnectionAvailableLayout = view.findViewById(R.id.no_internet_connection_layout);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(UsersViewModel.class);

        if (isNetworkAvailable()){
            noInternetConnectionAvailableLayout.setVisibility(View.GONE);
        }else
            noInternetConnectionAvailableLayout.setVisibility(View.VISIBLE);

        mViewModel.setUsersFromNetwork(true);
        mViewModel.getUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable final List<User> users) {
                mViewModel.getFavIDs().observe(UsersFragment.this, new Observer<List<Integer>>() {
                    @Override
                    public void onChanged(@Nullable List<Integer> ids) {

                        usersAdapter = new UsersAdapter(users, getContext(), ids);

                        recyclerView.setAdapter(usersAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    }
                });

            }
        });


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && mViewModel!=null)
            mViewModel.getFavIDs().observe(this, new Observer<List<Integer>>() {
                @Override
                public void onChanged(@Nullable List<Integer> integers) {
                    if (usersAdapter!=null) {
                        usersAdapter.setFavList(integers);
                        usersAdapter.notifyDataSetChanged();
                    }
                }
            });
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
