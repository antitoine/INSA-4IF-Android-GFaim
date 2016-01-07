package fr.insa.h4401.gfaim;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.osmdroid.api.IMapController;
import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.MapView;

/**
 * Details Restaurant fragment
 */
public class DetailsRestaurantFragment extends Fragment implements View.OnTouchListener{

    private static final String RESTAURANT = "restaurant";

    private FloatingActionButton fab_call;
    private FloatingActionButton fab_menu;
    private FloatingActionButton fab_web;
    private FloatingActionButton fab_star;
    private FloatingActionButton fab_pictures;
    private FloatingActionMenu menu;
    private RestaurantFactory.name mRestaurant;
    private OnFragmentInteractionListener mListener;
    private ImageView starIcon;

    public DetailsRestaurantFragment() {
        // Required empty public constructor
    }

    /**
     * @return A new instance of fragment DetailsRestaurantFragment.
     */
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
        View v = inflater.inflate(R.layout.fragment_details_restaurant, null);

        final Restaurant restau = RestaurantFactory.getRestaurant(mRestaurant);

        TextView RestaurantTitle = (TextView) v.findViewById(R.id.restaurant_detail_name);
        RestaurantTitle.setText(restau.getTitle());

        RatingBar ratingBar = (RatingBar) v.findViewById(R.id.restaurant_detail_rating);
        ratingBar.setRating(restau.getRating());

        Drawable progressDrawable = ratingBar.getProgressDrawable();
        DrawableCompat.setTint(progressDrawable, Color.WHITE);

        TextView nbRates = (TextView) v.findViewById(R.id.restaurant_details_nb_rates);
        nbRates.setText(Integer.toString(restau.getNbRates()));

        TextView status = (TextView) v.findViewById(R.id.restaurant_detail_time);
        status.setText(restau.getStatus());

        TextView price = (TextView) v.findViewById(R.id.restaurant_detail_price);
        price.setText(restau.getPrice());

        TextView timeToWait = (TextView) v.findViewById(R.id.restaurant_detail_waitingTime);
        timeToWait.setText(Integer.toString(restau.getWaitingDuration()));

        TextView type = (TextView) v.findViewById(R.id.restaurant_detail_type);
        type.setText(restau.getType());

        starIcon = (ImageView) v.findViewById(R.id.restaurant_detail_favorite);
        if (restau.isFavorite()) {
            starIcon.setVisibility(View.VISIBLE);
        }

        // --- Actions des boutons

        menu = (FloatingActionMenu) v.findViewById(R.id.menu);

        menu.hideMenuButton(false);

        v.postDelayed(new Runnable() {

            @Override
            public void run() {
                menu.showMenuButton(true);
            }

        }, 500);

        fab_call = (FloatingActionButton) v.findViewById(R.id.fab_call);
        fab_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0628948371"));
                startActivity(intent);
                menu.toggle(false);
            }
        });

        fab_web = (FloatingActionButton) v.findViewById(R.id.fab_web);
        fab_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                startActivity(browserIntent);
                menu.toggle(false);
            }
        });

        fab_star = (FloatingActionButton) v.findViewById(R.id.fab_star);
        fab_star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (restau.isFavorite()) {
                    starIcon.setVisibility(View.INVISIBLE);
                    fab_star.setLabelText("Ajouter aux favoris");
                    restau.setFavorite(false);
                } else {
                    starIcon.setVisibility(View.VISIBLE);
                    fab_star.setLabelText("Retirer des favoris");
                    restau.setFavorite(true);
                }
                menu.toggle(false);
            }
        });

        fab_menu = (FloatingActionButton) v.findViewById(R.id.fab_menu);
        fab_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle(false);
                MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                        .title(R.string.menu_tile)
                        .customView(R.layout.menu_dialog_layout, false)
                        .positiveText("Fermer")
                        .show();

                View view = dialog.getCustomView();
                SliderLayout sliderShow = (SliderLayout) view.findViewById(R.id.slider);

                DefaultSliderView defaultSliderView2 = new DefaultSliderView(getContext());
                defaultSliderView2.image(R.drawable.menu1);
                sliderShow.addSlider(defaultSliderView2);

                DefaultSliderView defaultSliderView3 = new DefaultSliderView(getContext());
                defaultSliderView3.image(R.drawable.menu2);
                sliderShow.addSlider(defaultSliderView3);

            }
        });

        fab_pictures = (FloatingActionButton) v.findViewById(R.id.fab_pictures);
        fab_pictures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.toggle(false);
                MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                        .customView(R.layout.slider_photos_dialog, false)
                        .positiveText("Fermer")
                        .show();

                View view = dialog.getCustomView();
                SliderLayout sliderShow = (SliderLayout) view.findViewById(R.id.slider);
                DefaultSliderView defaultSliderView1 = new DefaultSliderView(getContext());
                defaultSliderView1.image(R.drawable.snack_campus);
                sliderShow.addSlider(defaultSliderView1);

                DefaultSliderView defaultSliderView2 = new DefaultSliderView(getContext());
                defaultSliderView2.image(R.drawable.snack_campus);
                sliderShow.addSlider(defaultSliderView2);

                DefaultSliderView defaultSliderView3 = new DefaultSliderView(getContext());
                defaultSliderView3.image(R.drawable.snack_campus);
                sliderShow.addSlider(defaultSliderView3);

            }
        });

        v.findViewById(R.id.restaurant_detail_scrollview).setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closeFloatingMenu();
                return false;
            }
        });


        // --- Mise à jour de la map
        MapView mapView = (MapView) v.findViewById(R.id.mapview_min);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls(false);
        mapView.setMultiTouchControls(false);

        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (menu.isOpened()) {
                    closeFloatingMenu();
                } else {
                    if ((event.getAction() & MotionEvent.ACTION_MASK) == MotionEvent.ACTION_UP) {
                        MapFragment mapFragment = MapFragment.newInstance(restau.getNameId());

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                        fragmentManager.beginTransaction()
                                .replace(R.id.frame_content, mapFragment)
                                .addToBackStack(null)
                                .commit();

                        fragmentManager.executePendingTransactions();
                    }
                }
                return false;
            }
        });

        IMapController mapController = mapView.getController();
        mapController.setZoom(18);

        Marker currentPos = MapData.getInstance().getMarker(MapData.KEY_CURRENT_POS_MARKER);
        Marker restauMarker = MapData.getInstance().getMarker(restau.getNameId());

        mapView.getOverlays().add(currentPos);
        mapView.getOverlays().add(restauMarker);

        try {
            MapQuestRouteCompute mapQuestRouteCompute = new MapQuestRouteCompute(mapView.getContext());
            Road road = mapQuestRouteCompute.execute(currentPos.getPosition(), restauMarker.getPosition()).get();
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road, mapView.getContext());

            mapView.getOverlays().add(0, roadOverlay);
        } catch (Exception e) {
            /* On ne trace pas le chemin s'il n'a pas pu être calculé. */
            e.printStackTrace();
        }

        mapController.setCenter(restauMarker.getPosition());

        mapView.invalidate();

        return v;
    }

    private void closeFloatingMenu() {
        if (menu.isOpened()) {
            menu.toggle(true);
        }
    }

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

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        menu.toggle(false);
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
        //void onFragmentInteraction(Uri uri);
    }
}
