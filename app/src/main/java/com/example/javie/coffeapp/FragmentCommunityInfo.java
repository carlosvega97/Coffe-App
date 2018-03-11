package com.example.javie.coffeapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityInfo extends Fragment {
    private Button btnLeaveCommunity;
    private TextView tVCommunityTitleInfo, tVAddressInfo, tVCommunityDescriptionInfo;
    private View communityInfoView;
    private String communityTitle;


    public FragmentCommunityInfo() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        communityInfoView = inflater.inflate(R.layout.fragment_community_info, container, false);
        communityTitle = getActivity().getIntent().getStringExtra("communityTitle");
        getCommunityInfo();
        return communityInfoView;
    }

    private void loadComponentViews() {
        tVCommunityTitleInfo = communityInfoView.findViewById(R.id.tVCommunityTitleInfo);
        tVAddressInfo = communityInfoView.findViewById(R.id.tVAddressInfo);
        tVCommunityDescriptionInfo = communityInfoView.findViewById(R.id.tVCommunityDescriptionInfo);
        btnLeaveCommunity = communityInfoView.findViewById(R.id.btnLeaveCommunity);
        btnLeaveCommunity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leaveCommunity();
            }
        });
    }

    private void getCommunityInfo() {
        loadComponentViews();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities").child(communityTitle);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Community community = dataSnapshot.getValue(Community.class);
                tVCommunityTitleInfo.setText(community.getName());
                tVAddressInfo.setText(community.getAddress());
                tVCommunityDescriptionInfo.setText(community.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void leaveCommunity() {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities").child(communityTitle).child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    if (user.getValue().equals(userID)) {
                        String UserID = user.getKey();
                        databaseReference.child(UserID).removeValue();
                        Intent intent = new Intent(getActivity(), CommunitiesActivity.class);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
