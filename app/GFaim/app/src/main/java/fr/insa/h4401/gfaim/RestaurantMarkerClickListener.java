package fr.insa.h4401.gfaim;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.views.MapView;

import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 02/01/2016.
 */
public class RestaurantMarkerClickListener implements Marker.OnMarkerClickListener {

    private static Polyline currentRoad = null;

    @Override
    public boolean onMarkerClick(Marker marker, MapView mapView) {
        hideOldMarker();
        mapView.getOverlays().remove(MapData.getInstance().getCurrentRoad());

        marker.showInfoWindow();

        // Chemin entre la position et le restaurant sélectionné
        try {
            MapQuestRouteCompute mapQuestRouteCompute = new MapQuestRouteCompute(mapView.getContext());
            Road road = mapQuestRouteCompute.execute(MarkerFactory.getCurrentLocation(), marker.getPosition()).get();
            Polyline roadOverlay = RoadManager.buildRoadOverlay(road, mapView.getContext());

            if (currentRoad != null) {
                mapView.getOverlays().remove(currentRoad);
            }

            MapData.getInstance().setCurrentMarker(marker);
            MapData.getInstance().setCurrentRoad(roadOverlay);
            mapView.getOverlays().add(0, roadOverlay);
            mapView.invalidate();

            currentRoad = roadOverlay;
        } catch (Exception e) {
            /* On ne trace pas le chemin s'il n'a pas pu être calculé. */
            e.printStackTrace();
        }

        return true;
    }

    private void hideOldMarker() {
        for (Marker marker : MapData.getInstance().getMarkers()) {
            marker.closeInfoWindow();
        }
    }

}