package com.bignerdranch.android.movieland.utilities;

/**
 * Created by Kigold on 4/17/2017.
 */
public interface AsyncTaskGetMovies<T>
{
    /**
     * Invoked when the AsyncTask has completed its execution.
     * @param result The resulting object from the AsyncTask.
     */
    public void onTaskComplete(T result);
    public void beforeTask();

}