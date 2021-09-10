package com.example.guiltypleasures;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    private Context mContext;
    private List<UpcomingMovieClass> mData;

    public MovieAdapter(Context mContext, List<UpcomingMovieClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.upcoming_movies, parent, false);

        return new MovieAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieAdapter.MyViewHolder holder, int position) {

        holder.id.setText(mData.get(position).getId());
        holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg()).into(holder.img);


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id;
        TextView title;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.id_txt);
            title = itemView.findViewById(R.id.movie_name);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
