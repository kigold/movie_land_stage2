package com.bignerdranch.android.movieland.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.CancellationSignal;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;


/**
 * Created by Kigold on 5/13/2017.
 */

public class MovieProvider  extends ContentProvider{

    public static final int CODE_MOVIE = 100;
    public static final int BULK_INSERT_FAV_MOVIES = 200;
    public static final int DELETE_MOVIE = 300;
    public static final int UPDATE_MOVIE = 400;

    public static final int MOVIE_WITH_ID = 500;


    private static final UriMatcher sUriMatcher = buildUriMatcher();

    MovieDbHelper mOpenHelper;

    public static UriMatcher buildUriMatcher() {


        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONTENT_AUTHORITY;




        matcher.addURI(authority, MovieContract.PATH_MOVIE, CODE_MOVIE);

        matcher.addURI(authority, MovieContract.PATH_MOVIE + "/#", MOVIE_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, ContentValues[] values) {
        Cursor cursor;
        /*cursor = mOpenHelper.getWritableDatabase().insert(
                MovieContract.MovieEntry.TABLE_NAME,
                null,
                null,
                null,
                null);*/
        mOpenHelper.getWritableDatabase().beginTransaction();
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match){
            case BULK_INSERT_FAV_MOVIES: {
                int numInserted = 0;
                db.beginTransaction();
                try {
                    SQLiteStatement insert =
                            db.compileStatement("insert into " + MovieContract.MovieEntry.TABLE_NAME
                                + "(" + MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE
                                + "," + MovieContract.MovieEntry.COLUMN_RELEASE_DATE
                                + "," + MovieContract.MovieEntry.COLUMN_USER_RATING
                                + "," + MovieContract.MovieEntry.COLUMN_POPULARITY
                                + "," + MovieContract.MovieEntry.COLUMN_POSTER_IMAGE + ")"
                            + "values " + "?,?,?,?,?");
                    for(ContentValues value : values){
                        insert.bindString(1, value.getAsString(MovieContract.MovieEntry.COLUMN_ORIGINAL_TITLE));
                        insert.bindString(2, value.getAsString(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                        insert.bindString(3, value.getAsString(MovieContract.MovieEntry.COLUMN_USER_RATING));
                        insert.bindString(4, value.getAsString(MovieContract.MovieEntry.COLUMN_POPULARITY));
                        insert.bindString(5, value.getAsString(MovieContract.MovieEntry.COLUMN_POSTER_IMAGE));
                        insert.execute();
                    }
                    db.setTransactionSuccessful();
                    numInserted = values.length;
                } finally {
                    db.endTransaction();
                }
                if (numInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }
                return numInserted;
            }
            default:
                throw new UnsupportedOperationException("unsupported uri: " + uri);
        }

    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor cursor;

        switch (sUriMatcher.match(uri)){
            case CODE_MOVIE: {

                cursor = mOpenHelper.getReadableDatabase().query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        null,
                        null,
                        null,
                        null,
                        null);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
            switch (sUriMatcher.match(uri)) {
                case UPDATE_MOVIE:
                    count = db.update(MovieContract.MovieEntry.TABLE_NAME, values,
                            MovieContract.MovieEntry._ID + " = " + uri.getPathSegments().get(1) +
                                    (!TextUtils.isEmpty(selection) ?
                            " AND (" +selection + ')' : ""), selectionArgs);
                    break;
                default:
                    throw new IllegalArgumentException("Unknown URI " + uri );
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return count;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int count = 0;
        switch (sUriMatcher.match(uri)){
            case DELETE_MOVIE:
                String id = uri.getPathSegments().get(1);
                count = db.delete( MovieContract.MovieEntry.TABLE_NAME, MovieContract.MovieEntry._ID
                        +  " = " + id +
                                (!TextUtils.isEmpty(selection) ?
                       " AND (" + selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
