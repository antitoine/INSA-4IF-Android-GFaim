package fr.insa.h4401.gfaim;


import android.content.Context;
import android.graphics.Color;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DetailsRestaurantFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsRestaurantFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsRestaurantFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RESTAURANT = "restaurant";

    // TODO: Rename and change types of parameters
    private RestaurantFactory.name mRestaurant;

    private OnFragmentInteractionListener mListener;

    public DetailsRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param String Restaurant
     * @return A new instance of fragment DetailsRestaurantFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsRestaurantFragment newInstance(String restaurant) {
        DetailsRestaurantFragment fragment = new DetailsRestaurantFragment();
        Bundle args = new Bundle();
        args.putString(RESTAURANT, restaurant);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mRestaurant = RestaurantFactory.name.valueOf(getArguments().getString(RESTAURANT));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.details_restaurant, null);

        Restaurant resto = RestaurantFactory.getRestaurant(mRestaurant);

        TextView RestaurantTitle = (TextView) v.findViewById(R.id.restaurant_detail_name);
        RestaurantTitle.setText(resto.getTitle());

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.restaurant_detail_rating);
        ratingBar.setRating(resto.getRating());

        Drawable progressDrawable = ratingBar.getProgressDrawable();
       // drawable.setColorFilter(Color.parseColor("white"), PorterDuff.Mode.SRC_ATOP);
        DrawableCompat.setTint(progressDrawable, Color.WHITE);


        TextView nbRates = (TextView) v.findViewById(R.id.restaurant_details_nb_rates);
        nbRates.setText(Integer.toString(resto.getNbRates()));

        TextView status = (TextView) v.findViewById(R.id.restaurant_detail_time);
        status.setText(resto.getStatus());

        TextView price = (TextView) v.findViewById(R.id.restaurant_detail_price);
        price.setText(resto.getPrice());

        TextView timeToWait = (TextView) v.findViewById(R.id.restaurant_detail_waitingTime);
        timeToWait.setText(Integer.toString(resto.getWaitingDuration()));

        TextView type = (TextView) v.findViewById(R.id.restaurant_detail_type);
        type.setText(resto.getType());



        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            //mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        // TODO
/*        if (context instanceof OnFragmentInteractionListener) {
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
        //void onFragmentInteraction(Uri uri);
    }
}
