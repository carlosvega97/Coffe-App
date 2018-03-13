package com.coffe.javie.coffeapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by javie on 13/03/2018.
 */

public class MyFavorsActivity extends AppCompatActivity {
    private ArrayList<Favor> myFavors = new ArrayList<>();
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private ListView lVMyFavors;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favors);
        getMyFavorsData();
    }

    private void loadComponentViews() {
        lVMyFavors = findViewById(R.id.lVMyFavors);
        Toolbar toolbar = findViewById(R.id.myFavorsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setMyFavorsData(ArrayList<Favor> myFavors) {
        loadComponentViews();
        FavorListViewAdapter favorListViewAdapter = new FavorListViewAdapter(myFavors, getBaseContext());
        lVMyFavors.setAdapter(favorListViewAdapter);
        favorListViewAdapter.setFavorList(myFavors);
    }

    private void getMyFavorsData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userID).child("favors");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot favorObj : dataSnapshot.getChildren()) {
                    Favor favor = favorObj.getValue(Favor.class);
                    myFavors.add(favor);
                }
                setMyFavorsData(myFavors);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
