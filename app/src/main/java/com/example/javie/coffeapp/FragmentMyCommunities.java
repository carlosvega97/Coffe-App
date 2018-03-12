package com.example.javie.coffeapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
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
public class FragmentMyCommunities extends Fragment {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userRef = firebaseUser.getUid();
    private TextInputEditText tIETTitleAddCommunity, tIETDescriptionAddCommunity, tIETAddressAddCommunity;
    private ListView lVMyCommunities;
    private View myCommunitiesView;

    public FragmentMyCommunities() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myCommunitiesView = inflater.inflate(R.layout.fragment_my_communities, container, false);
        FloatingActionButton fab = (FloatingActionButton) myCommunitiesView.findViewById(R.id.fABAddCommunity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCreateCommunityDialog(getContext());
            }
        });
        getMyCommunities();
        return myCommunitiesView;
    }

    private void showCreateCommunityDialog(Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.add_community_dialog_template, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.createCommunityDialogTitle)
                .setMessage(R.string.createCommunityDialogMessage)
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tIETTitleAddCommunity = dialogView.findViewById(R.id.tIETTitleAddCommunity);
                        tIETDescriptionAddCommunity = dialogView.findViewById(R.id.tIETDescriptionAddCommunity);
                        tIETAddressAddCommunity = dialogView.findViewById(R.id.tIETAddressAddCommunity);
                        String title = tIETTitleAddCommunity.getText().toString();
                        String description = tIETDescriptionAddCommunity.getText().toString();
                        String address = tIETAddressAddCommunity.getText().toString();
                        ArrayList<String> users = new ArrayList<String>();
                        users.add(userRef);
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Communities").child(title);
                        mDatabase.setValue(new Community(title, description, address, users));
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void loadListView() {
        lVMyCommunities = myCommunitiesView.findViewById(R.id.lVMyCommunities);
        lVMyCommunities.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Community community = (Community) lVMyCommunities.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), SingleCommunityActivity.class);
                intent.putExtra("communityTitle", community.getName().toString());
                startActivity(intent);
            }
        });
    }

    private void getMyCommunitiesData(ArrayList<Community> myCommunities) {
        loadListView();
        CommunityListViewAdapter myCommunitiesAdapter = new CommunityListViewAdapter(myCommunities, getContext());
        lVMyCommunities.setAdapter(myCommunitiesAdapter);
        myCommunitiesAdapter.setCommunities(myCommunities);
    }

    private void getMyCommunities() {
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Community> subscribedCommunities = new ArrayList<>();
                for (DataSnapshot communityData: dataSnapshot.getChildren()){
                    Community communityObj = communityData.getValue(Community.class);
                    ArrayList <String> usersList = communityObj.getUsers();
                    if (usersList == null) {
                        Log.v("Vacio", "Vacio");
                    } else if (usersList.contains(userID)){
                        subscribedCommunities.add(communityObj);
                    }
                }
                getMyCommunitiesData(subscribedCommunities);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}