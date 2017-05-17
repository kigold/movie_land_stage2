package com.bignerdranch.android.movieland.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Kigold on 5/17/2017.
 */

public class MovieLab {
    private static MovieLab sMovieLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MovieLab get(Context context) {
        if (sMovieLab == null) {
            sMovieLab = new MovieLab(context);
        }
        return sMovieLab;
    }

    private MovieLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new MovieBaseHelper(mContext)
                .getWritableDatabase();
    }

}

