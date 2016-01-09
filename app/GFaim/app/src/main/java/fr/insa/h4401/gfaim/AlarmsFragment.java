package fr.insa.h4401.gfaim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.aakira.expandablelayout.Utils;
import com.github.clans.fab.FloatingActionMenu;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AlarmsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AlarmsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AlarmsFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {

    private OnFragmentInteractionListener mListener;
    static final List<ItemModel> data = new ArrayList<>();
    static RecyclerView recyclerView;
    public AlarmsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AlarmsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AlarmsFragment newInstance() {
        AlarmsFragment fragment = new AlarmsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_alarms, container, false);

        final FloatingActionMenu fab = (FloatingActionMenu) v.findViewById(R.id.alarm_add_button);

        final TimePickerDialog.OnTimeSetListener that = this;
        fab.setOnMenuButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar now = Calendar.getInstance();
                TimePickerDialog dpd = TimePickerDialog.newInstance(that,
                        now.get(Calendar.HOUR_OF_DAY),
                        now.get(Calendar.MINUTE),
                        true);
                dpd.show(getActivity().getFragmentManager(), "TimePickerDialog");
            }
        });

        recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        data.add(new ItemModel(
                "11:55",
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));
        data.add(new ItemModel(
                "12:55",
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));

        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(data));



        return v;
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
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        String hour;
        String min;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        else hour = "" + hourOfDay;

        if (minute < 10)
            min = "0" + minute;
        else min = "" + minute;

        data.add(new ItemModel(
                hour + ":" + min,
                Utils.createInterpolator(Utils.FAST_OUT_SLOW_IN_INTERPOLATOR)));

        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(data));


    }

    public static void remove(ItemModel item) {
        data.remove(item);
        recyclerView.setAdapter(new RecyclerViewRecyclerAdapter(data));
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
