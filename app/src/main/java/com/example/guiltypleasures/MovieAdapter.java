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

//Main Class extending RecyclerView for MovieAdapter
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {
    //variables
    private Context mContext;
    private List<UpcomingMovieClass> mData;

    //constructor
    public MovieAdapter(Context mContext, List<UpcomingMovieClass> mData) {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //call view and import it
        View v;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        v = inflater.inflate(R.layout.upcoming_movies, parent, false);

        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //create holder and initiate created movie variable widgets
        holder.id.setText(mData.get(position).getId());
        holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w500" + mData.get(position).getImg()).into(holder.img);
    }


    @Override
    public int getItemCount() {
        //return size of data
        return mData.size();
    }


    //Inner Class extending RecyclerView
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        //pass movie variable widgets
        TextView id;
        TextView title;
        ImageView img;

        //constructor
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.movie_name);
            title = itemView.findViewById(R.id.id_txt);
            img = itemView.findViewById(R.id.imageView);
        }
    }
}
