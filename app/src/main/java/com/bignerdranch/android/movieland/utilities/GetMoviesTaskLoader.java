package com.bignerdranch.android.movieland.utilities;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bignerdranch.android.movieland.dataType.MovieDataType;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kigold on 5/13/2017.
 */

public class GetMoviesTaskLoader extends AsyncTaskLoader<ArrayList<MovieDataType>>{
    private ArrayList<MovieDataType> mMovie;
    private String mSort_choice;
    private Context mContext;
    private AsyncTaskGetMoviesLoader<ArrayList<MovieDataType>> listner;

    public GetMoviesTaskLoader(Context context, String sort_choice){
        super(context);
        mContext = context;
        mSort_choice = sort_choice;

    }

    @Override
    protected void onStartLoading() {
        if (mMovie != null){
            deliverResult(mMovie);
        }else{
            forceLoad();
        }
    }



    @Override
    public ArrayList<MovieDataType> loadInBackground() {
        String sort_choice = mSort_choice;
        URL movieReqestUrl = NetworkUtils.buildUrl(sort_choice);
        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieReqestUrl);

            ArrayList<MovieDataType> movies = MovieParseUtils
                    .getMovieFromHttpRequest(mContext, jsonMovieResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void deliverResult(ArrayList<MovieDataType> data) {
        mMovie = data;
        super.deliverResult(data);
    }


}
