package com.bignerdranch.android.movieland;

import android.content.Intent;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.android.movieland.Adapter.ReviewAdapter;
import com.bignerdranch.android.movieland.Adapter.TrailerAdapter;
import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.bignerdranch.android.movieland.dataType.MovieExtraDetails;
import com.bignerdranch.android.movieland.dataType.MovieReview;
import com.bignerdranch.android.movieland.dataType.MovieTrailer;
import com.bignerdranch.android.movieland.utilities.MovieParseUtils;
import com.bignerdranch.android.movieland.utilities.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;

public class DetailedView extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<MovieExtraDetails>
        ,ReviewAdapter.ReviewAdapterOnClickHandler
        ,TrailerAdapter.TrailerAdapterOnClickHandler {


    private final String MOVIE_DATA_FOR_INTENT = "MOVIE_DATA";

    private MovieDataType mMovie;
    private ArrayList<MovieReview> mMovieReview;
    private ArrayList<MovieTrailer> mMovieTrailer;
    private MovieExtraDetails mMovieExtraDetails;
    private TextView mTitle, mSynopsis, mReleaseDate, mPopularity, mRating;
    private ImageView mPoster;
    private TextView mErrorMessageReview;
    private TextView mErrorMessageTrailer;
    private ImageButton mTrailerThumb;
    private ProgressBar mProgressBarReview;
    private ProgressBar mProgressBarTrailer;
    private RecyclerView mReviewList;
    private RecyclerView mTrailerList;
    private ReviewAdapter mReviewAdapter;
    private TrailerAdapter mTrailerAdapter;
    private final String BUNDLE_MOVIE_ID = "bundle_movie_id";
    private final String BUNDLE_QUERY_TYPE = "bundle_query_type";
    private final String BUNDLE_QUERY_VIDEO = "vidoes";
    private final String BUNDLE_QUERY_REVIEW = "reviews";
    private final String ERROR_MESSAGE = "No Data Availabel";
    private final String BUNDLE_GRID_SIZE = "bundle_grid_size";
    private static final int REVIEW_LOADER_ID = 1;
    private static final int TRAILER_LOADER_ID = 2;;


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
        mErrorMessageReview = (TextView) findViewById(R.id.tv_review_header);
        mErrorMessageTrailer = (TextView) findViewById(R.id.tv_trailer_header);
        //mReview = (TextView) findViewById(R.id.tv_review);
        //mTrailerThumb = (ImageView) findViewById(R.id.ib_trailer_thumb);
        //mProgressBar = (ProgressBar) findViewById(R.id.pb_progress_bar_dv);

        mReviewList = (RecyclerView) findViewById(R.id.rv_review);
        mTrailerList = (RecyclerView) findViewById(R.id.rv_trailer);

        int recyclerViewOrientation = LinearLayoutManager.VERTICAL;
        boolean shouldReverseLayout = false;
        LinearLayoutManager layoutManagerReview
                = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);
        mReviewAdapter = new ReviewAdapter(this);
        mReviewList.setLayoutManager(layoutManagerReview);
        mReviewList.setHasFixedSize(true);

        LinearLayoutManager layoutManagerTrailer
                = new LinearLayoutManager(this, recyclerViewOrientation, shouldReverseLayout);
        mTrailerAdapter = new TrailerAdapter(this);
        mReviewList.setLayoutManager(layoutManagerTrailer);
        mTrailerList.setHasFixedSize(true);

        Intent intentOrigin = getIntent();

        if (intentOrigin != null) {
            if (intentOrigin.hasExtra(MOVIE_DATA_FOR_INTENT)) {                //String movieAsJson = intentOrigin.getStringExtra(MOVIE_DATA_FOR_INTENT);
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

        mReviewList.setAdapter(mReviewAdapter);
        mTrailerList.setAdapter(mTrailerAdapter);
        loadItems(BUNDLE_QUERY_REVIEW, getString(mMovie.getId()));
        loadItems(BUNDLE_QUERY_VIDEO, getString(mMovie.getId()));


    }
    private void loadItems(String query_type, String movie_id){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_QUERY_TYPE, query_type);
        bundle.putString(BUNDLE_MOVIE_ID, movie_id);
        if (query_type == BUNDLE_QUERY_VIDEO){
            getSupportLoaderManager().initLoader(TRAILER_LOADER_ID, bundle, this);
        }
        else if(query_type == BUNDLE_QUERY_REVIEW){
            getSupportLoaderManager().initLoader(REVIEW_LOADER_ID, bundle, this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadItems(BUNDLE_QUERY_REVIEW, getString(mMovie.getId()));
        loadItems(BUNDLE_QUERY_VIDEO, getString(mMovie.getId()));
    }

    //Trailer and Review Loader
    @Override
    public Loader<MovieExtraDetails> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<MovieExtraDetails>(this) {
            MovieExtraDetails mMovieData = null;
            final String query_type = args.getString(BUNDLE_QUERY_TYPE);
            final String movieId = args.getString(BUNDLE_MOVIE_ID);

            @Override
            protected void onStartLoading() {
                if (mMovieData != null){
                    deliverResult(mMovieData);
                }else{
                    mProgressBarTrailer.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            @Override
            public MovieExtraDetails loadInBackground() {
                URL movieReqestUrl = NetworkUtils.buildUrlForItem(query_type, movieId);
                MovieExtraDetails mED;
                ArrayList<MovieReview> mR;
                ArrayList<MovieTrailer> mT;
                // get Trailer details
                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieReqestUrl);
                    mT = MovieParseUtils
                            .getMovieTrailerFromHttpRequest(getContext(), jsonMovieResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    mT = null;
                }
                //get Review details
                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieReqestUrl);
                    mR = MovieParseUtils
                            .getMovieReviewFromHttpRequest(getContext(), jsonMovieResponse);
                } catch (Exception e) {
                    e.printStackTrace();
                    mR = null;
                }
                mED = new MovieExtraDetails(mR, mT);
                return mED;
            }

            @Override
            public void deliverResult(MovieExtraDetails data) {
                mMovieExtraDetails = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MovieExtraDetails> loader, MovieExtraDetails data) {
        mProgressBarTrailer.setVisibility(View.INVISIBLE);
        mProgressBarReview.setVisibility(View.INVISIBLE);



        if (data.getR().size() < 0 || data.getR() == null){
            showErro(BUNDLE_QUERY_REVIEW);
        }else {mReviewAdapter.setData(data.getR());}
        if (data.getT().size() < 0 || data.getT() == null){
            showErro(BUNDLE_QUERY_REVIEW);
        }else{
            mTrailerAdapter.setData(data.getT());
            }
    }

    @Override
    public void onClick(MovieReview movie) {

    }

    @Override
    public void onClick(MovieTrailer movie) {

    }

    @Override
    public void onLoaderReset(Loader<MovieExtraDetails> loader) {

    }

    private void showErro(String err){
        if ( err == BUNDLE_QUERY_VIDEO){
            mErrorMessageTrailer.setText(ERROR_MESSAGE);
        }
        if ( err == BUNDLE_QUERY_REVIEW){
            mErrorMessageTrailer.setText(ERROR_MESSAGE);
        }
    }
}
