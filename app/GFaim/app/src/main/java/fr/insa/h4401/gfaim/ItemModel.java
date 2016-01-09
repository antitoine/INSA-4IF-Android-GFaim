package fr.insa.h4401.gfaim;

import android.animation.TimeInterpolator;

public class ItemModel {
    public final String description;
    public final TimeInterpolator interpolator;

    public ItemModel(String description, TimeInterpolator interpolator) {
        this.description = description;
        this.interpolator = interpolator;
    }
}