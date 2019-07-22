package com.e.rebelfoods.support;

import com.e.rebelfoods.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitAPI {

    @GET("/users")
    Call<List<User>> getUsers();
}
