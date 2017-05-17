package com.bignerdranch.android.movieland.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bignerdranch.android.movieland.R;

/**
 * Created by Kigold on 5/14/2017.
 */

public class MoviePreference {

    public static final String SORT_PREF_KEY = "MOVIESORTKEY";
    public static final String GRID_SIZE_KEY = "GRIDSIZEKEY";

    public static String getSortOrder(Context context){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
            return sharedPreferences.getString(SORT_PREF_KEY, context.getString(R.string.menu_sort_popular));
    }

    public static void saveSortOrder(Context context, String sort_value){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(SORT_PREF_KEY, sort_value).apply();
    }

    public static String getGridSize(Context context){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(GRID_SIZE_KEY, context.getString(R.string.default_grid_size));
    }

    public static void saveGridSzie(Context context, String grid_size){
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        sharedPreferences.edit().putString(GRID_SIZE_KEY, grid_size).apply();
    }

}
