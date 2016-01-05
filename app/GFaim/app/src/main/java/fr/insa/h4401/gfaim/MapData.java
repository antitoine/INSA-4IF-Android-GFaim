package fr.insa.h4401.gfaim;

import org.osmdroid.bonuspack.overlays.Marker;
import org.osmdroid.bonuspack.overlays.Polyline;
import org.osmdroid.views.overlay.Overlay;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pierre on 05/01/2016.
 */
public class MapData {
    private static MapData instance = null;

    public final static String KEY_CURRENT_POS_MARKER = "currentPosition";

    private Map<String, Marker> overlaysMarkers = new HashMap<>();
    private List<Overlay> specificOverlays = new ArrayList<>();

    private Polyline currentRoad;
    private Marker currentMarker;

    private MapData() {

    }

    public static MapData getInstance() {
        if (instance == null) {
            instance = new MapData();
        }

        return instance;
    }

    public void addMarker(Marker marker, String markerId) {
        overlaysMarkers.put(markerId, marker);
    }

    public Collection<Marker> getMarkers() {
        return overlaysMarkers.values();
    }

    public Marker getMarker(String markerId) {
        return overlaysMarkers.get(markerId);
    }

    public void addSpecificOverlays(List<Overlay> specificOverlays) {
        this.specificOverlays.addAll(specificOverlays);
    }

    public List<Overlay> getSpecificOverlays() {
        return Collections.unmodifiableList(specificOverlays);
    }

    public Polyline getCurrentRoad() {
        return currentRoad;
    }

    public void setCurrentRoad(Polyline currentRoad) {
        this.currentRoad = currentRoad;
    }

    public Marker getCurrentMarker() {
        return currentMarker;
    }

    public void setCurrentMarker(Marker currentMarker) {
        this.currentMarker = currentMarker;
    }
}
