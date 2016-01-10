package fr.insa.h4401.gfaim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MapFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MapFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapFragment extends Fragment implements MapEventsReceiver {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_DEFAULT_RESTAURANT = "defaultRestaurantNameId";

    private String defaultRestaurantNameId;

    private OnFragmentInteractionListener mListener;
    private ResourceProxyImpl mResourceProxy;
    private MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.

     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String restaurantNameId) {
        MapFragment fragment = new MapFragment();
        Bundle args = new Bundle();
        args.putString(ARG_DEFAULT_RESTAURANT, restaurantNameId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            defaultRestaurantNameId = getArguments().getString(ARG_DEFAULT_RESTAURANT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("create", "create map fragment");

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        mMapView.setMultiTouchControls(true);

        IMapController mapController = mMapView.getController();
        mapController.setZoom(16);

        // Event overlay
        MapEventsOverlay evOverlay = new MapEventsOverlay(getContext(), this);
        mMapView.getOverlays().add(evOverlay);

        // Position de l'utilisateur
        Marker currentPosition = MarkerFactory.getCurrentLocationMarker(mMapView);
        MapData.getInstance().addMarker(currentPosition, MapData.KEY_CURRENT_POS_MARKER);

        Marker defaultMarker = null;

        // Restaurants
        for (Restaurant restaurant : RestaurantFactory.getAllRestaurants()) {
            Marker restaurantMarker = MarkerFactory.getRestaurantMarker(this, mMapView, restaurant);
            MapData.getInstance().addMarker(
                    restaurantMarker,
                    restaurant.getNameId()
            );
        }

        mMapView.getOverlays().addAll(MapData.getInstance().getMarkers());

        if (defaultRestaurantNameId == null) {
            mapController.setCenter(currentPosition.getPosition());
        } else {
            mapController.setCenter(MapData.getInstance().getMarker(defaultRestaurantNameId).getPosition());
        }

        if (defaultRestaurantNameId != null) {
            view.postDelayed(new Runnable() {
                @Override
                public void run() {
                    MapData.getInstance().getMarker(defaultRestaurantNameId).showInfoWindow();
                    MapData.getInstance().setCurrentMarker(MapData.getInstance().getMarker(defaultRestaurantNameId));

                    try {
                        MapQuestRouteCompute mapQuestRouteCompute = new MapQuestRouteCompute(mMapView.getContext());
                        Road road = mapQuestRouteCompute.execute(
                                MarkerFactory.getCurrentLocationMarker(mMapView).getPosition(),
                                MapData.getInstance().getMarker(defaultRestaurantNameId).getPosition()
                        ).get();
                        Polyline roadOverlay = RoadManager.buildRoadOverlay(road, mMapView.getContext());
                        MapData.getInstance().setCurrentRoad(roadOverlay);

                        mMapView.getOverlays().add(0, roadOverlay);
                    } catch (Exception e) {
                        /* On ne trace pas le chemin s'il n'a pas pu être calculé. */
                       e.printStackTrace();
                    }

                }
            }, 1);
        }

        mMapView.invalidate();

        return view;
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
    public boolean singleTapConfirmedHelper(GeoPoint p) {

        for (Marker marker : MapData.getInstance().getMarkers()) {
            marker.closeInfoWindow();
        }

        if (!mMapView.getOverlays().isEmpty()) {
            Overlay firstOverlay = mMapView.getOverlays().get(0);
            if (firstOverlay instanceof Polyline) {
                // Road
                mMapView.getOverlays().remove(firstOverlay);
            }
        }

        mMapView.invalidate();

        return true;
    }

    @Override
    public boolean longPressHelper(GeoPoint p) {
        return false;
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
