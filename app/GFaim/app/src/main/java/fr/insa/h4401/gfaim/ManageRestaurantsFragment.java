package fr.insa.h4401.gfaim;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageRestaurantsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ManageRestaurantsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ManageRestaurantsFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ManageRestaurantsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ManageRestaurantsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ManageRestaurantsFragment newInstance(String param1, String param2) {
        ManageRestaurantsFragment fragment = new ManageRestaurantsFragment();
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
        View view = inflater.inflate(R.layout.fragment_manage_restaurants, container, false);

        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.list_restaurants);

        // Create the cardview
        linearLayout.addView(createRestaurantCardView(inflater, RestaurantFactory.getRestaurant(RestaurantFactory.name.GRILLON)));

        return view;
    }

    private View createRestaurantCardView(LayoutInflater inflater, Restaurant restaurant) {
        View cardview_layout = inflater.inflate(R.layout.cardview_restaurant_layout, null);
        View cardview = cardview_layout.findViewById(R.id.card_view);

        ((TextView) cardview.findViewById(R.id.restaurant_cardview_enum)).setText(restaurant.getNameId());

        ((ImageView) cardview.findViewById(R.id.restaurant_cardview_image)).setImageResource(restaurant.getImageResource());
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_title)).setText(restaurant.getTitle());
        ((RatingBar) cardview.findViewById(R.id.restaurant_cardview_rating)).setRating(restaurant.getRating());
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_nb_rates)).setText(Integer.toString(restaurant.getNbRates()));
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_price)).setText(restaurant.getPrice());
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_waiting_duration)).setText(Integer.toString(restaurant.getWaitingDuration()));
        ((TextView) cardview.findViewById(R.id.restaurant_cardview_route_duration)).setText(Integer.toString(restaurant.getRouteDuration()));

        cardview.setClickable(true);
        cardview.setOnClickListener(this);

        return cardview_layout;
    }


    @Override
    public void onClick(View v) {
        if (v instanceof CardView) {

            Intent myIntent = new Intent(getActivity(), RestaurateurActivity.class);
            getActivity().startActivity(myIntent);
            Fragment fragment = new DetailsRestaurantFragment();

        }
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
        void onFragmentInteraction(Uri uri);
    }
}
