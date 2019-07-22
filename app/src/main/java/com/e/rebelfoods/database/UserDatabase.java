package com.e.rebelfoods.database;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.e.rebelfoods.dao.UserDao;
import com.e.rebelfoods.model.Address;
import com.e.rebelfoods.model.Company;
import com.e.rebelfoods.model.Geo;
import com.e.rebelfoods.model.User;


@Database(entities = {User.class, Address.class, Geo.class, Company.class}, version = 1)

public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDao userDao();

    public static UserDatabase INSTANCE;
    public static final String DATABASE_NAME = "users";

    public static synchronized UserDatabase getINSTANCE(Context context){
        UserDatabase userDatabase = null;
        if(INSTANCE == null){
            userDatabase = Room.databaseBuilder(context,
                                UserDatabase.class,
                                DATABASE_NAME)
                                .allowMainThreadQueries()
                                .build();

        }
        return userDatabase;
    }

}
