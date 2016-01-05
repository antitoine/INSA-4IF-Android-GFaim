package fr.insa.h4401.gfaim;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.MarkerInfoWindow;
import org.osmdroid.views.MapView;

/**
 * Created by Pierre on 24/12/2015.
 */
public class RestaurantMapInfoWindow extends MarkerInfoWindow implements View.OnClickListener {

    private Fragment mFragment;
    private Restaurant mRestaurant;

    public RestaurantMapInfoWindow(Fragment fragment, MapView mapView, Restaurant restaurant) {
        super(R.layout.restaurant_map_info_layout, mapView);
        mFragment = fragment;
        mRestaurant = restaurant;
    }

    @Override
    public void onOpen(Object item) {
        mView.findViewById(R.id.restaurant_infowindow).setClickable(true);
        mView.findViewById(R.id.restaurant_infowindow).setOnClickListener(this);

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


    @Override
    public void onClick(View v) {
        Log.d("map", "on click mapinfo !");

        DetailsRestaurantFragment detailsRestaurantFragment = DetailsRestaurantFragment.newInstance(mRestaurant.getNameId());

        FragmentManager fragmentManager = mFragment.getActivity().getSupportFragmentManager();

        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, detailsRestaurantFragment)
                .addToBackStack(null)
                .commit();

        fragmentManager.executePendingTransactions();
    }
}
