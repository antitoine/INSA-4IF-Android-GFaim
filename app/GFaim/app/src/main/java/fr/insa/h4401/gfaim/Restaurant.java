package fr.insa.h4401.gfaim;

import org.osmdroid.util.GeoPoint;

import java.util.HashMap;

public class Restaurant {

    private GeoPoint mLocation;
    private String mTitle;
    private float mRating;
    private int mNbRates;
    private String mStatus;
    private String mPrice;
    private int mWaitingDuration;
    private int mRouteDuration;



    public Restaurant(double latitude, double longitude, String title, float rating, int nbRates, String status, String price, int waitingDuration, int routeDuration) {
        this.mLocation = new GeoPoint(latitude, longitude);
        this.mTitle = title;
        this.mRating = rating;
        this.mNbRates = nbRates;
        this.mStatus = status;
        this.mPrice = price;
        this.mWaitingDuration = waitingDuration;
        this.mRouteDuration = routeDuration;
    }

    public GeoPoint getLocation() {
        return mLocation;
    }

    public String getTitle() {
        return mTitle;
    }

    public float getRating() {
        return mRating;
    }

    public int getNbRates() {
        return mNbRates;
    }

    public String getStatus() {
        return mStatus;
    }

    public String getPrice() {
        return mPrice;
    }

    public int getWaitingDuration() {
        return mWaitingDuration;
    }

    public int getRouteDuration() {
        return mRouteDuration;
    }
}
