package com.example.javie.coffeapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvNavDrawerUser, tvNavDrawerEmail, tvPersonNameProfile, tvEmailProfile, tvPNumberProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvPersonNameProfile = findViewById(R.id.tvPersonNameProfile);
        tvEmailProfile = findViewById(R.id.tvEmailProfile);
        tvPNumberProfile = findViewById(R.id.tvPNumberProfile);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userRef=user.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(userRef).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> userlist=new ArrayList<User>();

                User user=dataSnapshot.getValue(User.class);
                userlist.add(user);


                tvPersonNameProfile.setText(user.getPersonName());
                tvEmailProfile.setText(user.getEmail());
                tvPNumberProfile.setText(user.getPnumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        tvNavDrawerUser = findViewById(R.id.tvNavDrawerUser);
        tvNavDrawerEmail = findViewById(R.id.tvNavDrawerEmail);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userRef=user.getUid();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
        mDatabase.child(userRef).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> userlist=new ArrayList<User>();

                User user=dataSnapshot.getValue(User.class);
                userlist.add(user);


                tvNavDrawerUser.setText(user.getPersonName());
                tvNavDrawerEmail.setText(user.getEmail());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home){
            Intent intent = new Intent(PerfilActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ftd) {

        } else if (id == R.id.nav_df) {

        } else if (id == R.id.nav_fr) {

        } else if (id == R.id.nav_mc) {

        } else if (id == R.id.nav_mp) {
            Intent intent = new Intent(PerfilActivity.this, PerfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(PerfilActivity.this, Splash_Screen.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

