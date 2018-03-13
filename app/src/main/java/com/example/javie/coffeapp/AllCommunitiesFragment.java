package com.example.javie.coffeapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
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
    String CommunityName;

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
        lVallCommunities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Community Name= (Community) lVallCommunities.getItemAtPosition(position);
                CommunityName = Name.getName();
                showAcceptCommunityDialog(view.getContext());
            }
        });
    }

    private void showAcceptCommunityDialog(Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_accept_communnity_template, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("ENTRAR EN LA COMUNIDAD")
                .setView(dialogView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Communities").child(CommunityName).child("users");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final ArrayList<String> mArray = new ArrayList<>();
                                for (DataSnapshot userID: dataSnapshot.getChildren()) {
                                    String userKey = userID.getValue(String.class);
                                    mArray.add(userKey);
                                }
                                mArray.add(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                databaseReference.setValue(mArray);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }).setNegativeButton("Cancel", null)
                .create();
        dialog.show();
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
