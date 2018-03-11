package com.example.javie.coffeapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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


    public FragmentCommunityUsers() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        communityUsersView = inflater.inflate(R.layout.fragment_community_users, container, false);
        communityTitle = getActivity().getIntent().getStringExtra("communityTitle");
        getUsers();
        return communityUsersView;
    }

    private void loadComponentViews() {
        lVCommunityUsers = communityUsersView.findViewById(R.id.lVCommunityUsers);
    }

    private void getUsersData(ArrayList<User> usersList) {
        loadComponentViews();
        UsersListViewAdapter usersListViewAdapter = new UsersListViewAdapter(usersList, getContext());
        lVCommunityUsers.setAdapter(usersListViewAdapter);
        usersListViewAdapter.setUserList(usersList);
    }

    private void getUsers() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final ArrayList<User> userList = new ArrayList<>();
        final ArrayList<String> userIDs = getUserIDs();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userObj : dataSnapshot.getChildren()) {
                    if (userIDs.contains(userObj.getKey())) {
                        User user = userObj.getValue(User.class);
                        userList.add(user);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        getUsersData(userList);
    }
    private ArrayList<String> getUserIDs() {
        final ArrayList<String> userIDs = new ArrayList<>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities").child(communityTitle).child("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userIDstr : dataSnapshot.getChildren()) {
                    String userID = userIDstr.getValue(String.class);
                    userIDs.add(userID);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userIDs;
    }
}
