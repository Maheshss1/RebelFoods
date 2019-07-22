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
import com.e.rebelfoods.OnFavClickInterface;
import com.e.rebelfoods.R;
import com.e.rebelfoods.database.UserDatabase;
import com.e.rebelfoods.fragments.FavoriteFragment;
import com.e.rebelfoods.model.Company;
import com.e.rebelfoods.model.User;
import com.e.rebelfoods.support.Common;

import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {

    private List<User> userList;
    private Context context;
    private OnFavClickInterface onFavClickInterface;
    private User user;


    public FavoriteAdapter(List<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public void setOnFavClickInterface(OnFavClickInterface onFavClickInterface) {
        this.onFavClickInterface = onFavClickInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        final ViewHolder viewHolder =new ViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_fav, viewGroup, false));
        viewHolder.fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserDatabase.getINSTANCE(context).userDao().deleteUser(userList.get(viewHolder.getAdapterPosition()));
                Log.d("FavoriteAdapter", "onClick: "+viewHolder.getAdapterPosition());
                userList.remove(viewHolder.getAdapterPosition());
                notifyItemRemoved(viewHolder.getAdapterPosition());
                onFavClickInterface.onClick(getItemCount()==0);
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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        final User user = userList.get(i);
        final Company company = user.getCompany();
        viewHolder.name.setText(user.getName());
        viewHolder.email.setText(user.getEmail());
        viewHolder.phone.setText(user.getPhone());
        viewHolder.website.setText(user.getWebsite());
        viewHolder.companyName.setText(company.getName());
        viewHolder.catchPhrase.setText(company.getCatchPhrase());
        viewHolder.bs.setText(company.getBs());


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
}

