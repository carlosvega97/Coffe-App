package com.example.javie.coffeapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


/**
 * TODO
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCommunityUsers extends Fragment {
    private View communityUsersView;
    private ListView lVCommunityUsers;
    private String communityTitle;
    private ArrayList<User> userObjs = new ArrayList<>();


    public FragmentCommunityUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        communityUsersView = inflater.inflate(R.layout.fragment_community_users, container, false);
        communityTitle = getActivity().getIntent().getStringExtra("communityTitle");
        getCommunityUserIDs();
        return communityUsersView;
    }

    private void loadComponentViews() {
        lVCommunityUsers = communityUsersView.findViewById(R.id.lVCommunityUsers);
    }

    private void getCommunityUserIDs() {
        final ArrayList<String> userIDs = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities").child(communityTitle).child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ID : dataSnapshot.getChildren()) {
                    userIDs.add(ID.getValue().toString());
                }
                getCommunityUserObjects(userIDs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    private void getCommunityUserObjects(final ArrayList<String> userList) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot user: dataSnapshot.getChildren()) {
                    if (userList.contains(user.getKey())) {
                        userObjs.add(user.getValue(User.class));
                    }
                }
                loadComponentViews();
                UsersListViewAdapter usersListViewAdapter = new UsersListViewAdapter(userObjs, getContext());
                lVCommunityUsers.setAdapter(usersListViewAdapter);
                usersListViewAdapter.setUserList(userObjs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
