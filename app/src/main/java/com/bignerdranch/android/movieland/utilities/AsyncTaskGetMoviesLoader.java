package com.bignerdranch.android.movieland.utilities;

/**
 * Created by Kigold on 5/13/2017.
 */

public interface AsyncTaskGetMoviesLoader<T> {
    public void onTaskComplete(T result);
    public void beforeTask();
}
