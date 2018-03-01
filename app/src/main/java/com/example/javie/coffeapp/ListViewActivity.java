package com.example.javie.coffeapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    private ArrayList<String> arraycomunidades = new ArrayList<String>();
    DatabaseReference DataRef;
    ListView miListview;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        miListview = (ListView)findViewById(R.id.listview44);

        DataRef = FirebaseDatabase.getInstance().getReference("Communities");
        DataRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Community comun = dataSnapshot.getValue(Community.class);
                Community comun2 = new Community(comun.getName(), comun.getDescription(), comun.getAddress(), comun.getUsers());
                arraycomunidades.add(comun.getName()+"\n"+comun.getAddress());

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1, arraycomunidades);
                miListview.setAdapter(arrayAdapter);
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

}
