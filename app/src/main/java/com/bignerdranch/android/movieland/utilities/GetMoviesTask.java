package com.bignerdranch.android.movieland.utilities;

import android.content.Context;
import android.os.AsyncTask;

import com.bignerdranch.android.movieland.dataType.MovieDataType;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kigold on 4/16/2017.
 */


public class GetMoviesTask extends AsyncTask<String, Void, ArrayList<MovieDataType>> {
    private Context context;
    private AsyncTaskGetMovies<ArrayList<MovieDataType>> listener;

    public GetMoviesTask(Context ctx, AsyncTaskGetMovies<ArrayList<MovieDataType>> listener) {
        this.context = ctx;
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        listener.beforeTask();
    }

    @Override
    protected ArrayList<MovieDataType> doInBackground(String... params) {
        String sort_choice = params[0];
        URL movieReqestUrl = NetworkUtils.buildUrl(sort_choice);
        try {
            String jsonMovieResponse = NetworkUtils
                    .getResponseFromHttpUrl(movieReqestUrl);

            ArrayList<MovieDataType> movies = MovieParseUtils
                    .getMovieFromHttpRequest(context, jsonMovieResponse);
            return movies;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<MovieDataType> moviesData) {
        listener.onTaskComplete(moviesData);
    }
}