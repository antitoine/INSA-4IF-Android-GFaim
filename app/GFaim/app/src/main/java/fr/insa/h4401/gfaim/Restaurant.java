package fr.insa.h4401.gfaim;

import android.os.Parcel;
import android.os.Parcelable;

import org.osmdroid.util.GeoPoint;

import java.io.Serializable;

public class Restaurant {

    private String mNameId;
    private GeoPoint mLocation;
    private String mTitle;
    private float mRating;
    private int mNbRates;
    private String mStatus;
    private String mPrice;
    private int mWaitingDuration;
    private int mRouteDuration;
    private int mImageResource;
    private String type;
    private boolean favorite;


    public Restaurant(String nameId, double latitude, double longitude, String title, float rating, int nbRates, String status, String price, int waitingDuration, int routeDuration, int imageResource, String type, boolean favorite) {
        this.mNameId = nameId;
        this.type = type;
        this.favorite = favorite;
        this.mLocation = new GeoPoint(latitude, longitude);
        this.mTitle = title;
        this.mRating = rating;
        this.mNbRates = nbRates;
        this.mStatus = status;
        this.mPrice = price;
        this.mWaitingDuration = waitingDuration;
        this.mRouteDuration = routeDuration;
        this.mImageResource = imageResource;
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

    public int getImageResource() {
        return mImageResource;
    }

    public String getNameId() {
        return mNameId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }


}
