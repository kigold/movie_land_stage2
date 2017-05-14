package com.bignerdranch.android.movieland.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bignerdranch.android.movieland.R;

/**
 * Created by Kigold on 5/14/2017.
 */

public class MoviePreference {

    private final String SORT_PREF_KEY = "SORTKEY";

    public static String getSortOrder(Context context){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(context.getString(R.string.movie_sort_type), context.getString(R.string.menu_sort_popular));
    }

}
