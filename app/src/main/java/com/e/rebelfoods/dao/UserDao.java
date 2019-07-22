package com.e.rebelfoods.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.e.rebelfoods.model.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long addUser(User user);

    @Delete
    int deleteUser(User user);

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT _id FROM USER where isFav=1")
    LiveData<List<Integer>> getFavIDs();

    @Query("UPDATE USER SET isFav = NOT isFav where _id = :id")
    int toggleFav(int id);
}
