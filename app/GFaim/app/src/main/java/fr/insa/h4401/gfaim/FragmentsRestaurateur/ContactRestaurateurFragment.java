package fr.insa.h4401.gfaim.FragmentsRestaurateur;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fr.insa.h4401.gfaim.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactRestaurateurFragment extends Fragment {


    public ContactRestaurateurFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_restaurateur, container, false);
    }

}
