package com.bignerdranch.android.movieland.dataType;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Kigold on 4/14/2017.
 */

public class MovieDataType implements Parcelable {
    private String original_title, synopsis;
    private Double user_rating;
    private String release_date;
    private String poster_image;
    private Double popularity;
    private Integer id;
    private String asJsonData;
    private ArrayList<MovieReview> review;
    private ArrayList<MovieTrailer> trailer;


    //constructor


    public MovieDataType(int id, String original_title, String synopsis, double user_rating, String release_date, String poster_image, double popularity, String asJsonData) {
        this.id = id;
        this.original_title = original_title;
        this.synopsis = synopsis;
        this.user_rating = user_rating;
        this.release_date = release_date;
        this.poster_image = "http://image.tmdb.org/t/p/w185" + poster_image;
        this.popularity = popularity;
        this.asJsonData = asJsonData;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public double getUser_rating() {
        return user_rating;
    }

    public void setUser_rating(double user_rating) {
        this.user_rating = user_rating;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_image() {
        return poster_image;
    }

    public void setPoster_image(String poster_image) {
        this.poster_image = poster_image;
    }

    public String getAsJsonData() {
        return asJsonData;
    }

    public void setAsJsonData(String asJsonData) {
        this.asJsonData = asJsonData;
    }

    public ArrayList<MovieReview> getReview() {
        return review;
    }

    public void setReview(ArrayList<MovieReview> review) {
        this.review = review;
    }

    public ArrayList<MovieTrailer> getTrailer() {
        return trailer;
    }

    public void setTrailer(ArrayList<MovieTrailer> trailer) {
        this.trailer = trailer;
    }

    public static ArrayList<MovieDataType> get_seed_data(){
        ArrayList<MovieDataType> movies = new ArrayList<>();
        movies.add(new MovieDataType(1,"Naruto",
                "About a young man who wanted to becomee hokage",
                5, "",  "", 23, "")
                );
        movies.add(new MovieDataType(1, "Boruto",
                "About a young Boy who wanted to be like his fathe",
                5, "", "", 23, "")
        );
        movies.add(new MovieDataType(1, "Hinata",
                "About a a chick in love",
                5, "","", 23, "")
        );
        movies.add(new MovieDataType(1, "Jiraya",
                "About a Pervy Sage",
                5, "",  "", 23, "")
        );
        return movies;
    }

    //used before implementing parcelable
    public static MovieDataType getMovieFromJson(String jsonData) {
        try {
            JSONObject mv = new JSONObject(jsonData);
            MovieDataType movie = new MovieDataType(mv.getInt("id")
                    ,mv.getString("original_title")
                    ,mv.getString("overview")
                    ,mv.getDouble("vote_average")
                    ,mv.getString("release_date")
                    ,mv.getString("poster_path")
                    ,mv.getDouble("popularity")
                    ,mv.toString());
            return movie;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.original_title);
        dest.writeString(this.synopsis);
        dest.writeDouble(this.user_rating);
        dest.writeString(this.release_date);
        dest.writeString(this.poster_image);
        dest.writeDouble(this.popularity);
        dest.writeString(this.asJsonData);
        dest.writeInt(this.id);
        dest.writeList(this.review);
        dest.writeList(this.trailer);
    }

    protected MovieDataType(Parcel in) {
        this.original_title = in.readString();
        this.synopsis = in.readString();
        this.user_rating = in.readDouble();
        this.release_date = in.readString();
        this.poster_image = in.readString();
        this.popularity = in.readDouble();
        this.asJsonData = in.readString();
        this.id = in.readInt();
        this.review = new ArrayList<MovieReview>();
        in.readList(this.review, MovieReview.class.getClassLoader());
        this.trailer = new ArrayList<MovieTrailer>();
        in.readList(this.trailer, MovieTrailer.class.getClassLoader());
    }

    public static final Creator<MovieDataType> CREATOR = new Creator<MovieDataType>() {
        @Override
        public MovieDataType createFromParcel(Parcel source) {
            return new MovieDataType(source);
        }

        @Override
        public MovieDataType[] newArray(int size) {
            return new MovieDataType[size];
        }
    };
}


