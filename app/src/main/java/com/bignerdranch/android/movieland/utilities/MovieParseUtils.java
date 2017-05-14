package com.bignerdranch.android.movieland.utilities;

import android.content.Context;

import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.bignerdranch.android.movieland.dataType.MovieReview;
import com.bignerdranch.android.movieland.dataType.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Kigold on 4/14/2017.
 */

public class MovieParseUtils {

    public static ArrayList<MovieDataType> getMovieFromHttpRequest(Context context, String raw_movies)
        throws JSONException    {
        ArrayList<MovieDataType> movies = new ArrayList<>();

        JSONObject moviesJson = new JSONObject(raw_movies);

         //Is there an error? /
        if (moviesJson.has("results")) {
            JSONArray moviesArray = moviesJson.getJSONArray("results");

            if (moviesArray != null || moviesArray.length() > 0) {
                moviesArray = moviesJson.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject mv = moviesArray.getJSONObject(i);

                    MovieDataType movie = new MovieDataType(mv.getInt("id")
                    ,mv.getString("original_title")
                    ,mv.getString("overview")
                    ,mv.getDouble("vote_average")
                    ,mv.getString("release_date")
                    ,mv.getString("poster_path")
                    ,mv.getDouble("popularity")
                    ,mv.toString());

                    movies.add(movie);
                }
            }
        }
        return movies;
    }
    public static ArrayList<MovieReview> getMovieReviewFromHttpRequest(Context context, String raw_movies)
            throws JSONException    {
        ArrayList<MovieReview> movies = new ArrayList<>();

        JSONObject moviesJson = new JSONObject(raw_movies);

        //Is there an error? /
        if (moviesJson.has("results")) {
            JSONArray moviesArray = moviesJson.getJSONArray("results");

            if (moviesArray != null || moviesArray.length() > 0) {
                moviesArray = moviesJson.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject mv = moviesArray.getJSONObject(i);

                    MovieReview movie = new MovieReview(mv.getString("author")
                            ,mv.getString("content"));

                    //add movie to List
                    movies.add(movie);
                }
            }
        }
        return movies;
    }

    public static ArrayList<MovieTrailer> getMovieTrailerFromHttpRequest(Context context, String raw_movies)
            throws JSONException    {
        ArrayList<MovieTrailer> movies = new ArrayList<>();

        JSONObject moviesJson = new JSONObject(raw_movies);

        //Is there an error? /
        if (moviesJson.has("results")) {
            JSONArray moviesArray = moviesJson.getJSONArray("results");

            if (moviesArray != null || moviesArray.length() > 0) {
                moviesArray = moviesJson.getJSONArray("results");

                for (int i = 0; i < moviesArray.length(); i++) {
                    JSONObject mv = moviesArray.getJSONObject(i);

                    MovieTrailer movie = new MovieTrailer(mv.getString("id")
                            ,mv.getString("key")
                            ,mv.getString("name")
                            ,mv.getString("site")
                            ,mv.getString("type")
                    );

                    //add movie to List
                    movies.add(movie);
                }
            }
        }
        return movies;
    }
    private ArrayList<MovieDataType> movies  = null;
}
