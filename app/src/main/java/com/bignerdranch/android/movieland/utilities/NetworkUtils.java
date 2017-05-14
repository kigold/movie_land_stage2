package com.bignerdranch.android.movieland.utilities;

import android.net.Uri;
import android.util.Log;

import com.bignerdranch.android.movieland.BuildConfig;
import com.bignerdranch.android.movieland.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

/**
 * Created by Kigold on 4/14/2017.
 */

public class NetworkUtils {
    private static final String TAG = NetworkUtils.class.getSimpleName();

    private static final String MOVIES_URL =
            "https://api.themoviedb.org/3/movie";
    private static final String API_KEY = BuildConfig.MOVIES_API_KEY;;


    public static java.net.URL buildUrl(String sort_param) {
        Uri builtUri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(sort_param)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static java.net.URL buildUrlForItem(String query, String id) {
        Uri builtUri = Uri.parse(MOVIES_URL).buildUpon()
                .appendPath(id)
                .appendPath(query)
                .appendQueryParameter("api_key", API_KEY)
                .build();
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Log.v(TAG, "Built URI " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                Log.v(TAG, "Built URI " + url);
                return scanner.next();
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        finally {
            urlConnection.disconnect();
        }
        return null;
    }
}

