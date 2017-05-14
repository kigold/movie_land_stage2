package com.bignerdranch.android.movieland;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.bignerdranch.android.movieland.dataType.MovieReview;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailedView extends AppCompatActivity {


    private final String MOVIE_DATA_FOR_INTENT = "MOVIE_DATA";

    MovieDataType mMovie;
    ArrayList<MovieReview> mMovieReview;
    TextView mTitle, mSynopsis, mReleaseDate, mPopularity, mRating, mReview;
    ImageView mPoster, mTrailerThumb;
    ProgressBar mProgressBar;
    RecyclerView mReviewList;
    RecyclerView mTrailerList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_view);



        mTitle = (TextView) findViewById(R.id.tv_dv_title);
        mSynopsis = (TextView) findViewById(R.id.tv_dv_synopsis);
        mReleaseDate = (TextView) findViewById(R.id.tv_dv_release_date);
        mPopularity = (TextView) findViewById(R.id.tv_dv_popularity);
        mRating = (TextView) findViewById(R.id.tv_dv_rating);
        mPoster = (ImageView) findViewById(R.id.iv_dv_poster);
        //mReview = (TextView) findViewById(R.id.tv_review);
        //mTrailerThumb = (ImageView) findViewById(R.id.ib_trailer_thumb);
        //mProgressBar = (ProgressBar) findViewById(R.id.pb_progress_bar_dv);

        mReviewList = (RecyclerView) findViewById(R.id.rv_review);
        mTrailerList = (RecyclerView) findViewById(R.id.rv_trailer);

        int recyclerViewOrientation = LinearLayoutManager.VERTICAL;
        boolean shouldReverseLayout = false;
        LinearLayoutManager layoutManagerReview
                = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);
        mReviewList.setLayoutManager(layoutManagerReview);
        mReviewList.setHasFixedSize(true);

        LinearLayoutManager layoutManagerTrailer
                = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);
        mReviewList.setLayoutManager(layoutManagerTrailer);
        mTrailerList.setHasFixedSize(true);





        Intent intentOrigin = getIntent();

        if (intentOrigin != null) {
            if (intentOrigin.hasExtra(MOVIE_DATA_FOR_INTENT)) {
                String movieAsJson = intentOrigin.getStringExtra(MOVIE_DATA_FOR_INTENT);
                MovieDataType mMovie = intentOrigin.getParcelableExtra(MOVIE_DATA_FOR_INTENT);
                //mMovie = MovieDataType.getMovieFromJson(movieAsJson);
                mTitle.setText(mMovie.getOriginal_title());
                mSynopsis.setText(mMovie.getSynopsis());
                mReleaseDate.setText(mMovie.getRelease_date());
                //TODO cast popularit and rating to string
                mPopularity.setText(Double.toString(mMovie.getPopularity()));
                mRating.setText(Double.toString(mMovie.getUser_rating()));
                Picasso.with(getApplicationContext())
                        .load(mMovie.getPoster_image())
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(mPoster);
            }
        }

    }

}
