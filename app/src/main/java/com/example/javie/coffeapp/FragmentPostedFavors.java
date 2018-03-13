package com.example.javie.coffeapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentPostedFavors extends Fragment {
    View postedFavorsView;
    ListView lvCommunityFavors;
    private FloatingActionButton fABAddFavor;
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String communityName;
    private ArrayList<String> arraycomunidades = new ArrayList<String>();
    DatabaseReference DataRef;
    String userName, FavorName;

    public FragmentPostedFavors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        communityName = getActivity().getIntent().getStringExtra("communityTitle");
        postedFavorsView = inflater.inflate(R.layout.fragment_posted_favors, container, false);
        loadComponentViews();
        getFavorsData();
        return postedFavorsView;
    }


    private void loadComponentViews() {
        lvCommunityFavors = postedFavorsView.findViewById(R.id.lvCommunityFavors);
        lvCommunityFavors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Favor Name= (Favor) lvCommunityFavors.getItemAtPosition(position);
                FavorName = Name.getTitle();
                showAcceptFavorDialog(view.getContext());
            }
        });
        fABAddFavor = postedFavorsView.findViewById(R.id.fABAddFavor);
        fABAddFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFavorDialog(v.getContext());
            }
        });
    }

    private void showAcceptFavorDialog(Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_accept_favor_template, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Accept Favor")
                .setView(dialogView)
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Communities").child(communityName).child("favors");
                        DatabaseReference mDatabase = databaseReference.child(FavorName).child("taken");
                        mDatabase.setValue(userID);
                    }
                }).setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void showAddFavorDialog(Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_favor_template, null);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.addFavorDialogTitle)
                .setView(dialogView)
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        createFavor(dialogView);
                    }
                })
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
    }

    private void createFavor(View dialogView) {
        TextInputEditText tIETTitleAddFavorCommunity, tIETDescriptionAddFavorCommunity, tIETDateAddFavorCommunity;
        tIETTitleAddFavorCommunity = dialogView.findViewById(R.id.tIETTitleAddFavorCommunity);
        tIETDescriptionAddFavorCommunity = dialogView.findViewById(R.id.tIETDescriptionAddFavorCommunity);
        tIETDateAddFavorCommunity = dialogView.findViewById(R.id.tIETDateAddFavorCommunity);
        String title = tIETTitleAddFavorCommunity.getText().toString();
        String description = tIETDescriptionAddFavorCommunity.getText().toString();
        String address = tIETDateAddFavorCommunity.getText().toString();

        if (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description) && !TextUtils.isEmpty(address)) {
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Communities").child(communityName).child("favors").child(title);
            mDatabase.setValue(new Favor(title, description, address, userID, "no"));
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("favors").child(title);
            databaseReference.setValue(new Favor(title, description, address, communityName,"no"));
        } else {
            Snackbar.make(lvCommunityFavors, "Fill the fields", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setFavorsData(ArrayList<Favor> favorList) {
        FavorListViewAdapter favorListViewAdapter = new FavorListViewAdapter(favorList, getContext());
        lvCommunityFavors.setAdapter(favorListViewAdapter);
        favorListViewAdapter.setFavorList(favorList);
    }

    private void getFavorsData() {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Communities").child(communityName).child("favors");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Favor> favorList = new ArrayList<>();
                for (DataSnapshot favorObj : dataSnapshot.getChildren()) {
                    favorList.add(favorObj.getValue(Favor.class));
                }
                setFavorsData(favorList);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
