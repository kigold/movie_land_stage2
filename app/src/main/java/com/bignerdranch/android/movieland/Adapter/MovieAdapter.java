package com.bignerdranch.android.movieland.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bignerdranch.android.movieland.R;
import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Kigold on 4/14/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>{

    private ArrayList<MovieDataType> mMoviesData;

    private final MovieAdapterOnClickHandler mClickHandler;




    // click handler interface

    public interface MovieAdapterOnClickHandler {
        void onClick(MovieDataType movie);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }


    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMoviePosterView;

        public MovieAdapterViewHolder(View view) {
            super(view);
            mMoviePosterView = (ImageView) view.findViewById(R.id.iv_movie_poster);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieDataType movie = mMoviesData.get(adapterPosition);
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_list_item, viewGroup, shouldAttactToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieDataType daMovie = mMoviesData.get(position);
        Context context = holder.itemView.getContext();
        //holder.mMoviePosterView.setImageResource(daMovie.getPoster_image());
        //holder.mMoviePosterView.setImageResource(R.mipmap.ic_launcher);
        Picasso.with(context)
                .load(daMovie.getPoster_image())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mMoviePosterView);

    }

    @Override
    public int getItemCount() {
        if (null != mMoviesData) {
            return mMoviesData.size();
        }
        return 0;
    }

    public void setData(ArrayList<MovieDataType> moviesData){
        mMoviesData = moviesData;
        notifyDataSetChanged();
    }
}
