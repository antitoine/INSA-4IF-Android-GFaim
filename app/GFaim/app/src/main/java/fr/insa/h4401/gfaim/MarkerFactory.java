package fr.insa.h4401.gfaim;

import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

/**
 * Created by Pierre on 31/12/2015.
 */
public abstract class MarkerFactory {

    private static Marker createMarker(MapView mapView, GeoPoint location) {
        Marker marker = new Marker(mapView);
        marker.setPosition(location);
        return marker;
    }

    public static Marker getRestaurantMarker(Fragment fragment, MapView mapView, Restaurant restaurant) {
        Marker marker = createMarker(mapView, restaurant.getLocation());
        RestaurantMapInfoWindow popupWindow = new RestaurantMapInfoWindow(fragment, mapView, restaurant);
        marker.setInfoWindow(popupWindow);
        marker.setIcon(ContextCompat.getDrawable(mapView.getContext(), R.drawable.pin_restaurant));
        marker.setOnMarkerClickListener(new RestaurantMarkerClickListener());
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }

    public static Marker getCurrentLocationMarker(MapView mapView) {
        Marker marker = createMarker(mapView, getCurrentLocation());
        marker.setIcon(ContextCompat.getDrawable(mapView.getContext(), R.drawable.pin_current_location));
        marker.setInfoWindow(null);
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker;
    }

    public static GeoPoint getCurrentLocation() {
        return new GeoPoint(45.781713, 4.872902);
    }

}
