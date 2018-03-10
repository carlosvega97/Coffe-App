package com.example.javie.coffeapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityUsers extends Fragment {
    private View communityUsersView;


    public FragmentCommunityUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        communityUsersView = inflater.inflate(R.layout.fragment_community_users, container, false);
        return communityUsersView;
    }

}
