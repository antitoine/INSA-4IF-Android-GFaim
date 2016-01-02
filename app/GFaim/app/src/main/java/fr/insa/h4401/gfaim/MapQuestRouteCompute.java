package fr.insa.h4401.gfaim;

import android.content.Context;
import android.os.AsyncTask;

import org.osmdroid.bonuspack.routing.MapQuestRoadManager;
import org.osmdroid.bonuspack.routing.Road;
import org.osmdroid.bonuspack.routing.RoadManager;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Pierre on 31/12/2015.
 */
public class MapQuestRouteCompute extends AsyncTask<GeoPoint, Void, Road> {

    private Context mContext;
    private static final String API_KEY = "8zESOvN6QVizy8PtaAahAKusrTP62NYe";

    public MapQuestRouteCompute (Context context){
        mContext = context;
    }

    @Override
    protected Road doInBackground(GeoPoint... params) {
        RoadManager roadManager = new MapQuestRoadManager(API_KEY, mContext);
        roadManager.addRequestOption("routeType=pedestrian");
        ArrayList<GeoPoint> waypoints = new ArrayList<>();
        Collections.addAll(waypoints, params);
        return roadManager.getRoad(waypoints);
    }
}
