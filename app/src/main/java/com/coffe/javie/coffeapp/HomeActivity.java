package com.coffe.javie.coffeapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TextInputEditText;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GotUser {
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
    String userRef = firebaseUser.getUid();
    private Spinner communitySpinner;
    private TextInputEditText tIETTitleAddFavor, tIETDescriptionAddFavor, tIETDateAddFavor;
    private TextView tvNavDrawerUser, tvNavDrawerEmail;
    private Database database = new Database();
    protected Button mAddFavor, mAcceptFavor;
    private CircleImageView imageViewUser;
   // private ImageView imageViewUser;
    FirebaseAuth fauth;
    private ArrayList<String> arraycomunidades = new ArrayList<String>();
    DatabaseReference DataRef;
    ListView miListview;
    String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private String communityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fauth = FirebaseAuth.getInstance();
        mAddFavor = findViewById(R.id.afbttn);
        mAcceptFavor = findViewById(R.id.acfbttn);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navDrawerOpen, R.string.navDrawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mAddFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogAddFavor(v.getContext());
            }
        });
        mAcceptFavor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(HomeActivity.this, CommunitiesActivity.class);
               startActivity(intent);
            }
        });
    }

    private void dialogAddFavor(final Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.activity_add_favor_community, null);
        communitySpinner = dialogView.findViewById(R.id.communitySpinner);

        getMyCommunities();//Este método rellenará el spinner cuando Firebase le devuelva los datos
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(R.string.createCommunityDialogTitle)
                .setMessage(R.string.createCommunityDialogMessage)
                .setView(dialogView)
                .setPositiveButton(R.string.addButton, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tIETTitleAddFavor = dialogView.findViewById(R.id.tIETTitleAddFavor);
                        tIETDescriptionAddFavor = dialogView.findViewById(R.id.tIETDescriptionAddFavor);
                        tIETDateAddFavor = dialogView.findViewById(R.id.tIETDateAddFavor);
                        String title = tIETTitleAddFavor.getText().toString();
                        String description = tIETDescriptionAddFavor.getText().toString();
                        String address = tIETDateAddFavor.getText().toString();
                        communityName = communitySpinner.getSelectedItem().toString();
                        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Communities").child(communityName).child("favors").child(title);
                        mDatabase.setValue(new Favor(title, description, address, userID, "no"));
                    }
                })
                .setNegativeButton(R.string.cancelButoon, null)
                .create();
        dialog.show();


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
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvNavDrawerUser = findViewById(R.id.tvNavDrawerUser);
        tvNavDrawerEmail = findViewById(R.id.tvNavDrawerEmail);
        imageViewUser = findViewById(R.id.imageViewUser23);
        User user = database.getLoggedUser(this);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_home) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(HomeActivity.this, HomeActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_fr) {
            Intent intent = new Intent(HomeActivity.this, MyFavorsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mc) {
            Intent intent = new Intent(HomeActivity.this, CommunitiesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mp) {
            Intent intent = new Intent(HomeActivity.this, PerfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(HomeActivity.this, Splash_Screen.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loggedUser(User user) {
        tvNavDrawerUser.setText(user.getPersonName());
        tvNavDrawerEmail.setText(user.getEmail());

        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://coffe-36307.appspot.com").child("Users").child(fauth.getCurrentUser().getUid()).child("fotodeperfil");
            final File localFile;

            localFile = File.createTempFile("images", "jpg");

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    imageViewUser.setImageBitmap(bitmap);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void getMyCommunities() {
        final List<String> subscribedCommunities = new ArrayList<String>();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Communities");
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot communityData: dataSnapshot.getChildren()){
                    Community communityObj = communityData.getValue(Community.class);
                    ArrayList <String> usersList = communityObj.getUsers();
                    if (usersList == null) {

                    } else if(usersList.contains(userID)){
                        subscribedCommunities.add(communityObj.getName().toString());
                    }
                }
                //relleno spiner
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item,subscribedCommunities );
                communitySpinner.setAdapter(dataAdapter);
                //Un poco cerdo, pero mejor que antes
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        //return subscribedCommunities;
    }
}
