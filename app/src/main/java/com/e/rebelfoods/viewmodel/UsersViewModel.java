package com.e.rebelfoods.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.util.Log;

import com.e.rebelfoods.dao.UserDao;
import com.e.rebelfoods.database.UserDatabase;
import com.e.rebelfoods.model.User;
import com.e.rebelfoods.support.Common;
import com.e.rebelfoods.support.RetrofitAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersViewModel extends AndroidViewModel {

    private static final String TAG = "UsersViewModel";
    private MutableLiveData<List<User>> userList = new MutableLiveData<>();

    private UserDao userDao;

    public UsersViewModel(Application application) {
        super(application);
        userDao = UserDatabase.getINSTANCE(application).userDao();
   }

    public void setUsersFromNetwork(boolean isNetworkCall){
        if(isNetworkCall){
           callRetrofitAPI();
        }else {
            callRoomDatabase();
        }
    }

    public LiveData<List<User>> getUsers(){
        return userList;
    }

    private void callRoomDatabase() {
        userList.postValue(userDao.getUsers());
    }

    private void callRetrofitAPI() {
        Common.getRetrofit().create(RetrofitAPI.class)
                .getUsers()
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if (response.isSuccessful()) {
                            userList.postValue(response.body());
                        }
                    }
                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Log.d(TAG, "onFailure: "+t.getMessage());
                    }
                });
    }

    public LiveData<List<Integer>> getFavIDs(){
        return userDao.getFavIDs();
    }
}
