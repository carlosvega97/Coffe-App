package com.example.javie.coffeapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPostedFavors extends Fragment {
    private View postedFavorsView;


    public FragmentPostedFavors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        postedFavorsView = inflater.inflate(R.layout.fragment_posted_favors, container, false);

        return postedFavorsView;
    }

}
