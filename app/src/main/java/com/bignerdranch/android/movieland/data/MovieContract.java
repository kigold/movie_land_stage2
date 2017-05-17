package com.bignerdranch.android.movieland.data;

/**
 * Created by Kigold on 5/11/2017.
 */

import android.net.Uri;
import android.provider.BaseColumns;

public class MovieContract {
    public static final String CONTENT_AUTHORITY = "com.example.android.MovieProvider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_FAVORITES = "favourites";

    public static final class MovieEntry implements BaseColumns {
        public static final Uri CONTEXT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVORITES)
                .build();

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "id";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_ORIGINAL_TITLE = "original_title";

        public static final String COLUMN_USER_RATING = "user_rating";

        public static final String COLUMN_RELEASE_DATE = "release_date";

        public static final String COLUMN_POSTER_IMAGE = "poster_image";

        public static final String COLUMN_POPULARITY = "popularity";
    }





}

