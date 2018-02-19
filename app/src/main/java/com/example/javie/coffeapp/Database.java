package com.example.javie.coffeapp;

import android.app.Activity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by pabli on 02/15/2018.
 */

public class Database {
    GotUser gotUser;



    private User user;

    public User getLoggedUser(Activity activity) {
        final Activity act = activity;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userRef = firebaseUser.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(userRef).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                gotUser = (GotUser) act;
                gotUser.loggedUser(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return user;
    }
}
