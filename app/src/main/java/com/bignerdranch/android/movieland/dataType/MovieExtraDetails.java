package com.bignerdranch.android.movieland.dataType;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Kigold on 5/14/2017.
 */

public class MovieExtraDetails implements Parcelable {
    private ArrayList<MovieReview> mR;
    private ArrayList<MovieTrailer> mT;

    public MovieExtraDetails(ArrayList<MovieReview> r, ArrayList<MovieTrailer> t) {
        mR = r;
        mT = t;
    }

    public ArrayList<MovieReview> getR() {
        return mR;
    }

    public void setR(ArrayList<MovieReview> r) {
        mR = r;
    }

    public ArrayList<MovieTrailer> getT() {
        return mT;
    }

    public void setT(ArrayList<MovieTrailer> t) {
        mT = t;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mR);
        dest.writeTypedList(this.mT);
    }

    protected MovieExtraDetails(Parcel in) {
        this.mR = in.createTypedArrayList(MovieReview.CREATOR);
        this.mT = in.createTypedArrayList(MovieTrailer.CREATOR);
    }

    public static final Parcelable.Creator<MovieExtraDetails> CREATOR = new Parcelable.Creator<MovieExtraDetails>() {
        @Override
        public MovieExtraDetails createFromParcel(Parcel source) {
            return new MovieExtraDetails(source);
        }

        @Override
        public MovieExtraDetails[] newArray(int size) {
            return new MovieExtraDetails[size];
        }
    };
}
