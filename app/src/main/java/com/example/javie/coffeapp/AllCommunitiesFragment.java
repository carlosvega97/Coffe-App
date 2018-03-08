package com.example.javie.coffeapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class AllCommunitiesFragment extends Fragment {
    private ListView lVallCommunities;
    private View allCommunitiesView;

    public AllCommunitiesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        allCommunitiesView = inflater.inflate(R.layout.fragment_all_communities, container, false);
        getAllCommunities();
        return allCommunitiesView;
    }

    private void loadComponentViews() {
        lVallCommunities = allCommunitiesView.findViewById(R.id.lVAllCommunities);
    }

    private void getAllCommunities() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Community> allCommunities = new ArrayList<>();
                for (DataSnapshot communitydata: dataSnapshot.getChildren()) {
                    Community community = communitydata.getValue(Community.class);
                    allCommunities.add(community);
                }
                getCommunitiesItems(allCommunities);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getCommunitiesItems(ArrayList<Community> allCommunities) {
        loadComponentViews();
        CommunityListViewAdapter communityListViewAdapter = new CommunityListViewAdapter(allCommunities, getContext());
        lVallCommunities.setAdapter(communityListViewAdapter);
        communityListViewAdapter.setCommunities(allCommunities);
    }
}
