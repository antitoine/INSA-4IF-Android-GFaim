package fr.insa.h4401.gfaim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.techery.properratingbar.ProperRatingBar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RestaurantsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RestaurantsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private FloatingActionMenu favoritesFloatingButton;
    private Map<Restaurant, View> restaurantsCardViews = new HashMap<>();

    public RestaurantsFragment() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RestaurantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RestaurantsFragment newInstance(String param1, String param2) {
        RestaurantsFragment fragment = new RestaurantsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurants, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.list_restaurants);

        // Create the cardview
        for (Restaurant restaurant : RestaurantFactory.getAllRestaurants()) {
            View restaurantView = createRestaurantCardView(inflater, restaurant);
            restaurantsCardViews.put(restaurant, restaurantView);
            linearLayout.addView(restaurantView);
        }

        // Favorites floating button
        favoritesFloatingButton = (FloatingActionMenu) view.findViewById(R.id.list_restaurants_favorite);
        favoritesFloatingButton.hideMenuButton(false);

        favoritesFloatingButton.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (favoritesFloatingButton.isOpened()) {
                    favoritesFloatingButton.getMenuIconView().setImageResource(R.drawable.ic_star_outline_24dp);
                    updateFavoritesRestaurants(false);
                } else {
                    favoritesFloatingButton.getMenuIconView().setImageResource(R.drawable.ic_star_24dp);
                    updateFavoritesRestaurants(true);
                }

                favoritesFloatingButton.toggle(true);
            }
        });

        view.postDelayed(new Runnable() {

            @Override
            public void run() {
                favoritesFloatingButton.showMenuButton(true);
            }

        }, 500);

        return view;
    }

    private void updateFavoritesRestaurants(boolean favoritesRequired) {
        for (Map.Entry<Restaurant, View> entry : restaurantsCardViews.entrySet()) {
            if (favoritesRequired) {
                entry.getValue().setVisibility(
                        (entry.getKey().isFavorite()) ? View.VISIBLE : View.GONE
                );
            } else {
                entry.getValue().setVisibility(View.VISIBLE);
            }
        }
    }

    private View createRestaurantCardView(LayoutInflater inflater, Restaurant restaurant) {
        View cardview_layout = inflater.inflate(R.layout.cardview_restaurant_layout, null);
        View cardview = cardview_layout.findViewById(R.id.card_view);

        ((TextView) cardview.findViewById(R.id.restaurant_cardview_enum)).setText(restaurant.getNameId());

        ImageView imageRestaurant = (ImageView) cardview.findViewById(R.id.restaurant_cardview_image);
        imageRestaurant.setImageBitmap(BitmapImageHelper.decodeBitmapFromResource(getResources(), restaurant.getImageResource(), 100, 100));

        if (restaurant.isFavorite()) {
            cardview.findViewById(R.id.restaurant_cardview_favorite).setVisibility(View.VISIBLE);
        }
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_title)).setText(restaurant.getTitle());
        ((ProperRatingBar) cardview.findViewById(R.id.restaurant_cardview_rating)).setRating((int)restaurant.getRating());
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_nb_rates)).setText(Integer.toString(restaurant.getNbRates()));
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_price)).setText(restaurant.getPrice());
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_waiting_duration)).setText(Integer.toString(restaurant.getWaitingDuration()));
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_route_duration)).setText(Integer.toString(restaurant.getRouteDuration()));

        cardview.setClickable(true);
        cardview.setOnClickListener(this);

        return cardview_layout;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {
            DetailsRestaurantFragment detailsRestaurantFragment = DetailsRestaurantFragment.newInstance(
                    ((TextView) v.findViewById(R.id.restaurant_cardview_enum)).getText().toString());

            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_content, detailsRestaurantFragment)
                    .addToBackStack(null)
                    .commit();

            fragmentManager.executePendingTransactions();
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
