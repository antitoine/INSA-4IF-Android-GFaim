package fr.insa.h4401.gfaim;

import android.animation.TimeInterpolator;

public class ItemModel {
    public final String description;
    public final TimeInterpolator interpolator;
    public boolean isOn;

    public ItemModel(String description, TimeInterpolator interpolator, boolean isOn) {
        this.description = description;
        this.interpolator = interpolator;
        this.isOn = isOn;
    }
}