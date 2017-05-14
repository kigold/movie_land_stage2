package com.bignerdranch.android.movieland.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bignerdranch.android.movieland.R;
import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.bignerdranch.android.movieland.dataType.MovieReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kigold on 5/14/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MovieReviewAdapterViewHolder>{
    private ArrayList<MovieReview> mReviewData;

    private final ReviewAdapter.ReviewAdapterOnClickHandler mClickHandler;



    // click handler interface

    public interface ReviewAdapterOnClickHandler {
        void onClick(MovieReview movie);
    }

    public ReviewAdapter(ReviewAdapter.ReviewAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }



    public class MovieReviewAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public final TextView mReviewContent, mReviewAuthor;

        public MovieReviewAdapterViewHolder(View view) {
            super(view);
            mReviewContent = (TextView) view.findViewById(R.id.tv_review_content);
            mReviewAuthor = (TextView) view.findViewById(R.id.tv_review_author);

            view.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieReview review= mReviewData.get(adapterPosition);
            mClickHandler.onClick(review);
        }
    }

    @Override
    public MovieReviewAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttactToParentImmediately = false;

        View view = inflater.inflate(R.layout.movie_reviews, viewGroup, shouldAttactToParentImmediately);
        return new MovieReviewAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapterViewHolder holder, int position) {
        MovieReview daMovie = mReviewData.get(position);
        Context context = holder.itemView.getContext();
        holder.mReviewAuthor.setText(daMovie.getAuthor());
        holder.mReviewContent.setText(daMovie.getContent());
        /*Picasso.with(context)
                .load(daMovie.getPoster_image())
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(holder.mMoviePosterView);*/
    }


    @Override
    public int getItemCount() {
        if (null != mReviewData) {
            return mReviewData.size();
        }
        return 0;
    }

    public void setData(ArrayList<MovieReview> moviesData){
        mReviewData = moviesData;
        notifyDataSetChanged();
    }
}
