package fr.insa.h4401.gfaim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.MapEventsOverlay;
import org.osmdroid.bonuspack.overlays.MapEventsReceiver;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.util.ResourceProxyImpl;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;
import java.util.List;


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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private ResourceProxyImpl mResourceProxy;
    private MapView mMapView;

    public MapFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MapFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
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
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        mMapView = (MapView) view.findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
        //map.setBuiltInZoomControls(true);
        mMapView.setMultiTouchControls(true);


        IMapController mapController = mMapView.getController();
        mapController.setZoom(18);

        // Event overlay
        MapEventsOverlay evOverlay = new MapEventsOverlay(getContext(), this);
        mMapView.getOverlays().add(evOverlay);

        // Position de l'utilisateur
        Marker currentPosition = MarkerFactory.getCurrentLocationMarker(mMapView);
        MapData.getInstance().addMarker(currentPosition, MapData.KEY_CURRENT_POS_MARKER);

        // Restaurants
        for (Restaurant restaurant : RestaurantFactory.getAllRestaurants()) {
            MapData.getInstance().addMarker(MarkerFactory.getRestaurantMarker(this, mMapView, restaurant), restaurant.getNameId());
        }

        mMapView.getOverlays().addAll(MapData.getInstance().getMarkers());

        mapController.setCenter(currentPosition.getPosition());

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
