package com.bignerdranch.android.movieland.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.movieland.R;
import com.bignerdranch.android.movieland.dataType.MovieReview;
import com.bignerdranch.android.movieland.dataType.MovieTrailer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by Kigold on 5/14/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MovieTrailerAdapterViewHolder>{
    private ArrayList<MovieTrailer> mTrailerData;

    private final TrailerAdapter.TrailerAdapterOnClickHandler mClickHandler;



    // click handler interface

    public interface TrailerAdapterOnClickHandler {
        void onClick(MovieTrailer movie);
    }

    public TrailerAdapter(TrailerAdapter.TrailerAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    public class MovieTrailerAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final ImageButton mTrailerButton;

        public MovieTrailerAdapterViewHolder(View view) {
            super(view);
            mTrailerButton = (ImageButton) view.findViewById(R.id.ib_trailer_thumb);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieTrailer review= mTrailerData.get(adapterPosition);
            mClickHandler.onClick(review);
        }
    }

    @Override
    public TrailerAdapter.MovieTrailerAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(R.layout.movies_trailers, viewGroup, shouldAttactToParentImmediately);
        return new TrailerAdapter.MovieTrailerAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TrailerAdapter.MovieTrailerAdapterViewHolder holder, int position) {
        MovieTrailer daMovie = mTrailerData.get(position);
        Context context = holder.itemView.getContext();
        holder.mTrailerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //final int position = getView(position, view, parent).get(position);
                Toast.makeText(view.getContext(), "Trailer Should be Playing now" ,Toast.LENGTH_SHORT).show();
            }
        });
        Picasso.with(context)
                .load(daMovie.getUrl())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mTrailerButton);
    }


    @Override
    public int getItemCount() {
        if (null != mTrailerData) {
            return mTrailerData.size();
        }
        return 0;
    }

    public void setData(ArrayList<MovieTrailer> moviesData){
        mTrailerData = moviesData;
        notifyDataSetChanged();
    }
}