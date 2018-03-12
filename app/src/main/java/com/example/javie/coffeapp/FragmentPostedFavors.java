package com.example.javie.coffeapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    String userName;

    public FragmentPostedFavors() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        communityName = getActivity().getIntent().getStringExtra("communityTitle");
        postedFavorsView = inflater.inflate(R.layout.fragment_posted_favors, container, false);
        getAllCommunities();
       // loadComponentViews();
        return postedFavorsView;
    }

    private void getAllCommunities() {
        lvCommunityFavors = postedFavorsView.findViewById(R.id.lvCommunityFavors);
        DataRef = FirebaseDatabase.getInstance().getReference("Communities").child(communityName).child("favors");
        DataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Favor comun = dataSnapshot.getValue(Favor.class);
                Favor comun2 = new Favor(comun.getTitle(), comun.getDescription(), comun.getDate(), comun.getOwner(), comun.getTaken());
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(comun.getOwner());
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        userName = user.getPersonName();
                        System.out.println(userName);
                        arraycomunidades.add(comun.getTitle()+"\n"+ userName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arraycomunidades);
               // final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, arraycomunidades);
                lvCommunityFavors.setAdapter(arrayAdapter);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void loadComponentViews() {
        lvCommunityFavors = postedFavorsView.findViewById(R.id.lVAllCommunities);
        fABAddFavor = postedFavorsView.findViewById(R.id.fABAddFavor);
        fABAddFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFavorDialog(v.getContext());
            }
        });
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
        TextInputEditText tIETTitleAddFavorCommunity, tIETDescriptionAddFavorCommunity,tIETDateAddFavorCommunity;
        tIETTitleAddFavorCommunity = dialogView.findViewById(R.id.tIETTitleAddFavorCommunity);
        tIETDescriptionAddFavorCommunity = dialogView.findViewById(R.id.tIETDescriptionAddFavorCommunity);
        tIETDateAddFavorCommunity = dialogView.findViewById(R.id.tIETDateAddFavorCommunity);
        String title = tIETTitleAddFavorCommunity.getText().toString();
        String description = tIETDescriptionAddFavorCommunity.getText().toString();
        String address = tIETDateAddFavorCommunity.getText().toString();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Communities").child(communityName).child("favors").child(title);
        mDatabase.setValue(new Favor(title, description, address, userID, "no"));
    }
}
