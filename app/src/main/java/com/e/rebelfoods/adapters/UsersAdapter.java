package com.e.rebelfoods.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.e.rebelfoods.MapsActivity;
import com.e.rebelfoods.R;
import com.e.rebelfoods.dao.UserDao;
import com.e.rebelfoods.database.UserDatabase;
import com.e.rebelfoods.model.Company;
import com.e.rebelfoods.model.User;
import com.e.rebelfoods.support.Common;

import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    private List<Integer> favList;
    private UserDao userDao;
    public static final String TAG = "UsersAdapter";
    User user;


    public UsersAdapter(List<User> userList, Context context, List<Integer> favList) {
        this.userList = userList;
        this.context = context;
        this.favList = favList;
        userDao = UserDatabase.getINSTANCE(context).userDao();
    }

    public void setFavList(List<Integer> favList) {
        this.favList = favList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final ViewHolder viewHolder =new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adatper_users, viewGroup, false));
        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = userList.get(viewHolder.getAdapterPosition());
                if (!user.isFav()){
                    toggleFav(true, viewHolder);
                    userDao.addUser(user);
                    userDao.toggleFav(user.getId());
                    favList.add(user.getId());
                    user.setFav(true);
                }else{
                    userDao.toggleFav(user.getId());
                    favList.remove((Integer) user.getId());
                    toggleFav(false, viewHolder);
                    userDao.deleteUser(user);
                    user.setFav(false);


                }
            }
        });

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = userList.get(viewHolder.getAdapterPosition());

                Intent intent = new Intent(context, MapsActivity.class);
                intent.putExtra(Common.ADDRESS_OBJ, user.getAddress());
                context.startActivity(intent);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final User user = userList.get(i);
        Company company = user.getCompany();
        viewHolder.name.setText(user.getName());
        viewHolder.email.setText(user.getEmail());
        viewHolder.phone.setText(user.getPhone());
        viewHolder.website.setText(user.getWebsite());
        viewHolder.companyName.setText(company.getName());
        viewHolder.catchPhrase.setText(company.getCatchPhrase());
        viewHolder.bs.setText(company.getBs());
        Log.d(TAG, "onBindViewHolder: "+favList);

        if (favList.contains(user.getId())) {
            user.setFav(true);
            toggleFav(true, viewHolder);
        }
        else {
            user.setFav(false);
            toggleFav(false, viewHolder);
        }

    }

    @Override
    public int getItemCount() {
        return userList==null?0:userList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, email, phone, website, companyName, catchPhrase, bs;
        ImageButton fav;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            website = itemView.findViewById(R.id.website);
            companyName = itemView.findViewById(R.id.company_name);
            catchPhrase = itemView.findViewById(R.id.catch_phrase);
            bs = itemView.findViewById(R.id.bs);
            fav = itemView.findViewById(R.id.fav);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    private void toggleFav(boolean isFav, ViewHolder viewHolder){
        if (isFav){
            viewHolder.fav.setBackground(context.getDrawable(R.drawable.ic_favorite_black_24dp));
        }
        else {
            viewHolder.fav.setBackground(context.getDrawable(R.drawable.ic_favorite_border_black_24dp));
        }
    }
}
