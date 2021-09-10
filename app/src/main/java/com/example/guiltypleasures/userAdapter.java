package com.example.guiltypleasures;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class userAdapter extends RecyclerView.Adapter<userAdapter.MyViewHolder> {

    Context context;

    ArrayList<User> list;

    public userAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.user_recycle, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull userAdapter.MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.Username.setText(user.getUsername());
        holder.Username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserProfileOther.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Username;
        ImageView Profilepic;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Username = itemView.findViewById(R.id.textUsername);
            Profilepic = itemView.findViewById(R.id.profileImageView);
        }
    }
}
