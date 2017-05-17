package com.bignerdranch.android.movieland.database;

/**
 * Created by Kigold on 5/17/2017.
 */

public class MovieDBSchema {
    public static final class MovieTable {
        public static final String NAME = "favouriteMovies";

        public static final class Cols {

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
}
