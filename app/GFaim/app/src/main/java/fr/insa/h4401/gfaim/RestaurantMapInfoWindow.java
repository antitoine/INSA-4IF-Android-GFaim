package fr.insa.h4401.gfaim;

import android.widget.RatingBar;
import android.widget.TextView;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * Created by Pierre on 24/12/2015.
 */
public class RestaurantMapInfoWindow extends MarkerInfoWindow {

    private Restaurant mRestaurant;

    public RestaurantMapInfoWindow(MapView mapView, Restaurant restaurant) {
        super(R.layout.restaurant_map_info_layout, mapView);
        mRestaurant = restaurant;
    }

    @Override
    public void onOpen(Object item) {
        ((TextView)mView.findViewById(R.id.map_restaurant_title)).setText(mRestaurant.getTitle());
        ((RatingBar)mView.findViewById(R.id.map_restaurant_rating)).setRating(mRestaurant.getRating());
        ((TextView)mView.findViewById(R.id.map_restaurant_nb_rates)).setText(Integer.toString(mRestaurant.getNbRates()));
        ((TextView)mView.findViewById(R.id.map_restaurant_status)).setText(mRestaurant.getStatus());
        ((TextView)mView.findViewById(R.id.map_restaurant_price)).setText(mRestaurant.getPrice());
        ((TextView)mView.findViewById(R.id.map_restaurant_waiting_duration)).setText(Integer.toString(mRestaurant.getWaitingDuration()));
        ((TextView)mView.findViewById(R.id.map_restaurant_route_duration)).setText(Integer.toString(mRestaurant.getRouteDuration()));

        mView.invalidate();
        mView.requestLayout();
    }
}
