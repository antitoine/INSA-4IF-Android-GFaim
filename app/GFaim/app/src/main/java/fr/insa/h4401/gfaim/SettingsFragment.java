package fr.insa.h4401.gfaim;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SettingsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance(String param1, String param2) {
        SettingsFragment fragment = new SettingsFragment();
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
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.settings_regime_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.regimes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);


        final HashMap<Integer, Allergie> allergieHashMap = RestaurantFactory.getAllergies();
        final FlowLayout tagLayout = (FlowLayout) v.findViewById(R.id.tagAllergies);

        for(Map.Entry<Integer, Allergie> entry : allergieHashMap.entrySet()) {
            Integer key = entry.getKey();
            Allergie value = entry.getValue();

            if(value.isSelected()){
                tagLayout.addView(createTag(inflater, value));
            }
        }

        CardView addAllergie = (CardView) v.findViewById(R.id.setting_add_button);
        addAllergie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Integer> ids = new ArrayList<Integer>();

                for(Map.Entry<Integer, Allergie> entry : allergieHashMap.entrySet()) {
                    Integer key = entry.getKey();
                    Allergie value = entry.getValue();

                    if(value.isSelected()){
                        ids.add(key);
                    }
                }

                new MaterialDialog.Builder(getContext())
                        .title(R.string.title)
                        .widgetColor(getResources().getColor(R.color.colorPrimary))
                        .items(R.array.allergies)
                        .itemsCallbackMultiChoice(convertIntegers(ids), new MaterialDialog.ListCallbackMultiChoice() {
                            @Override
                            public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                                for(Map.Entry<Integer, Allergie> entry : allergieHashMap.entrySet()) {
                                    entry.getValue().setSelected(false);
                                }
                                for (Integer aWhich : which) {
                                    allergieHashMap.get(aWhich).setSelected(true);
                                }

                                tagLayout.removeAllViews();
                                for(Map.Entry<Integer, Allergie> entry : allergieHashMap.entrySet()) {
                                    Integer key = entry.getKey();
                                    Allergie value = entry.getValue();

                                    if(value.isSelected()){
                                        tagLayout.addView(createTag(inflater, value));
                                    }
                                }

                                return true;
                            }
                        })
                        .positiveText(R.string.choose)
                        .show();
            }
        });

        return v;
    }

    private View createTag(LayoutInflater inflater, Allergie allergie) {
        View tagView = inflater.inflate(R.layout.tag_view, null);
        TextView tagViewText = (TextView) tagView.findViewById(R.id.textTag);
        tagViewText.setText(allergie.getName());
        return tagView;
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

    public static Integer[] convertIntegers(List<Integer> integers)
    {
        Integer[] ret = new Integer[integers.size()];
        Iterator<Integer> iterator = integers.iterator();
        for (int i = 0; i < ret.length; i++) {
            ret[i] = iterator.next();
        }
        return ret;
    }
}
