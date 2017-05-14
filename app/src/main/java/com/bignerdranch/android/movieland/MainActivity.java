package com.bignerdranch.android.movieland;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bignerdranch.android.movieland.Adapter.MovieAdapter;
import com.bignerdranch.android.movieland.dataType.MovieDataType;
import com.bignerdranch.android.movieland.utilities.AsyncTaskGetMoviesLoader;
import com.bignerdranch.android.movieland.utilities.MovieParseUtils;
import com.bignerdranch.android.movieland.utilities.MoviePreference;
import com.bignerdranch.android.movieland.utilities.NetworkUtils;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements
        MovieAdapter.MovieAdapterOnClickHandler
        ,LoaderCallbacks<ArrayList<MovieDataType>>
        ,SharedPreferences.OnSharedPreferenceChangeListener

{

    private RecyclerView mRecyclerView;
    private MovieAdapter mMovieAdapter;
    private TextView mErrorMessage;
    private ProgressBar mProgressBar;
    private ArrayList<MovieDataType> mMovie;
    private String mSort_choice = "top_rated";
    private int NUMBER_OF_ITEMS_IN_GRIDVIEW = 2;
    private int mGrid_size = NUMBER_OF_ITEMS_IN_GRIDVIEW;
    private final String MOVIE_DATA_FOR_INTENT = "MOVIE_DATA";
    private final String BUNDLE_RECYCLER_LAYOUT = "bundle_recycle_layout";
    private final String BUNDLE_RECYCLER_MOVIEDATA = "bundle_movie_data";
    private final String BUNDLE_SORT_CHOICE = "bundle_sort_choice";
    private final String BUNDLE_GRID_SIZE = "bundle_grid_size";
    private static final int MOVIE_LOADER_ID = 0;
    private static final String MOVIES_API_KEY = BuildConfig.MOVIES_API_KEY;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movie_recycle_view);
        mErrorMessage = (TextView) findViewById(R.id.tv_errro_msg);

        mMovieAdapter = new MovieAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, NUMBER_OF_ITEMS_IN_GRIDVIEW);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mRecyclerView.setAdapter(mMovieAdapter);
        //init progress bar
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress_bar);


        if (savedInstanceState != null) {
            Parcelable savedRecyclerViewState = savedInstanceState.getParcelable(
                    BUNDLE_RECYCLER_LAYOUT);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerViewState);
            mMovie = savedInstanceState.getParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA);
            mSort_choice = savedInstanceState.getString(BUNDLE_SORT_CHOICE);
            mGrid_size = savedInstanceState.getInt(BUNDLE_GRID_SIZE, NUMBER_OF_ITEMS_IN_GRIDVIEW);
            resizeGrid(mGrid_size);
            loadMovieData(mSort_choice);
        }
        else
        {
            //load movies
            loadMovieData(null);
        }

        int loaderId = MOVIE_LOADER_ID;
        Bundle bundleForLoader = null;
        LoaderCallbacks<ArrayList<MovieDataType>> callback = MainActivity.this;

        getSupportLoaderManager().initLoader(loaderId, bundleForLoader, callback);
    }

    @Override
    public android.support.v4.content.Loader<ArrayList<MovieDataType>> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<ArrayList<MovieDataType>>(this) {
            ArrayList<MovieDataType> mMovieData = null;

            @Override
            protected void onStartLoading() {

                if (mMovieData != null){
                    deliverResult(mMovieData);
                }else{
                    mProgressBar.setVisibility(View.VISIBLE);
                    forceLoad();
                }
            }
            @Override
            public ArrayList<MovieDataType> loadInBackground() {
                URL movieReqestUrl = NetworkUtils.buildUrl(mSort_choice);
                try {
                    String jsonMovieResponse = NetworkUtils
                            .getResponseFromHttpUrl(movieReqestUrl);

                    ArrayList<MovieDataType> movies = MovieParseUtils
                            .getMovieFromHttpRequest(MainActivity.this, jsonMovieResponse);
                    return movies;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(ArrayList<MovieDataType> data) {
                mMovie = data;
                super.deliverResult(data);
            }
        };
    }

    @Override
    public void onLoadFinished(android.support.v4.content.Loader<ArrayList<MovieDataType>> loader, ArrayList<MovieDataType> data) {
        mProgressBar.setVisibility(View.INVISIBLE);

        if (data == null) {
            showErro();
        } else {
            mMovieAdapter.setData(mMovie);
            showMovieDataView();
        }
    }

    @Override
    public void onLoaderReset(android.support.v4.content.Loader<ArrayList<MovieDataType>> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(BUNDLE_RECYCLER_LAYOUT, mRecyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList(BUNDLE_RECYCLER_MOVIEDATA, mMovie);
        outState.putString(BUNDLE_SORT_CHOICE, mSort_choice);
        outState.putInt(BUNDLE_GRID_SIZE, mGrid_size);
    }


    private void resizeGrid(int size) {
        GridLayoutManager layoutManager = new GridLayoutManager(this, size);
        mRecyclerView.setLayoutManager(layoutManager);

    }
    private void loadMovieData(String choice) {
        showMovieDataView();
        //TODO get menu sort item from persistent data source
        //set Prefecen sort moview value
        mSort_choice = MoviePreference.getSortOrder(this) ;//"top_rated";

        if (choice != null) {
            mSort_choice = choice;
            // TODO set pref value

        }
        // "popular" or "top_rated"
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_SORT_CHOICE,mSort_choice);
        //fresh load
        if (mMovie == null){
            //new GetMoviesTask(this, new GetMovieTaskCompleteListner()).execute(sort_choice);
            //new GetMoviesTaskLoader(this, new GetMovieTaskCompleteListener()).forceLoad();
            //LoaderCallbacks<ArrayList<MovieDataType>> callback = MainActivity.this;

            getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, bundle, this).forceLoad();
        }
        else{
            //from savedStateInstance
            //mMovieAdapter.setData(mMovie);
            getSupportLoaderManager().restartLoader(MOVIE_LOADER_ID, bundle, this).forceLoad();
        }
    }


    @Override
    public void onClick(MovieDataType movie) {
        Context context = this;
        Intent i = new Intent();
        i.setClass(this, DetailedView.class);
        //i.putExtra(MOVIE_DATA_FOR_INTENT, movie.getAsJsonData());
        i.putExtra(MOVIE_DATA_FOR_INTENT, movie);
        startActivity(i);
        Toast.makeText(context, movie.getOriginal_title(), Toast.LENGTH_SHORT)
                .show();
    }

    private void showMovieDataView(){
        // hide error message view
        mErrorMessage.setVisibility(View.INVISIBLE);
        // show movie grid list
        mRecyclerView.setVisibility(View.VISIBLE);
    }
    private void showErro(){
        // show error message view
        mErrorMessage.setVisibility(View.VISIBLE);
        // hide movie grid list
        mRecyclerView.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //inflate manue
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie, menu);
        //inflater.inflate(R.menu.movie_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
        // "popular" or "top_rated"
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_highest_rated:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Sort By Highest Rated", Toast.LENGTH_LONG).show();
                mMovie = null;
                mMovieAdapter.setData(null);
                loadMovieData("top_rated");
                return true;
            case R.id.menu_most_popular:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Sort By Most Popular", Toast.LENGTH_LONG).show();
                mMovie = null;
                mMovieAdapter.setData(null);
                loadMovieData("popular");
                return true;
            case R.id.menu_two:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Change Grid size to 2", Toast.LENGTH_LONG).show();
                mGrid_size = 2;
                if (NUMBER_OF_ITEMS_IN_GRIDVIEW != R.id.menu_two){
                    resizeGrid(2);
                }
                return true;
            case R.id.menu_three:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Change Grid size to 3", Toast.LENGTH_LONG).show();
                mGrid_size = 3;
                if (NUMBER_OF_ITEMS_IN_GRIDVIEW != R.id.menu_three){
                    resizeGrid(3);
                }
                return true;
            case R.id.menu_four:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Change Grid size to 4", Toast.LENGTH_LONG).show();
                mGrid_size = 4;
                if (NUMBER_OF_ITEMS_IN_GRIDVIEW != R.id.menu_four){
                    resizeGrid(4);
                }
                return true;
            case R.id.menu_five:
                item.setChecked(true);
                Toast.makeText(getApplicationContext(), "Change Grid size to 5", Toast.LENGTH_LONG).show();
                mGrid_size = 5;
                if (NUMBER_OF_ITEMS_IN_GRIDVIEW != R.id.menu_five){
                    resizeGrid(5);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //loadMovieData(key);
    }
}
