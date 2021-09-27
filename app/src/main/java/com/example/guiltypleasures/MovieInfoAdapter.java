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

import java.util.ConcurrentModificationException;
import java.util.List;

public class MovieInfoAdapter extends RecyclerView.Adapter<MovieInfoAdapter.MyViewHolder> {
    private Context mContext;
    private List<UpcomingMovieClass> mData;

    public MovieInfoAdapter(Context mContext, List<UpcomingMovieClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_details, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //create holder and initiate created movie variable widgets
        holder.id.setText(mData.get(position).getId());
        holder.release.setText(mData.get(position).getReleaseDate());
        holder.overview.setText(mData.get(position).getOverview());

        //Pulls Movie Images from JSON
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg()).into(holder.img);
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg()).into(holder.backImg);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //pass movie variable widgets
        TextView id;
        ImageView img;
        ImageView backImg;
        TextView overview;
        TextView release;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.movie_info_name);
            img = itemView.findViewById(R.id.movie_pic);
            backImg = itemView.findViewById(R.id.backdropimg);
            overview = itemView.findViewById(R.id.overview);
            release = itemView.findViewById(R.id.release_date);
        }
    }
}
