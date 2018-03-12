package com.example.javie.coffeapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class PerfilActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GotUser {
    TextView tvNavDrawerUser, tvNavDrawerEmail, tvPersonNameProfile, tvEmailProfile, tvPNumberProfile;
    private Database database = new Database();
    private User user;
//***************************************************************************************************************
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;

    //private ImageView mSetImage;
    private Button mOptionButton;
    private RelativeLayout mRlView;
    private CircularImageView mSetImage;
    private ImageView mImageView;
    //private ImageView mImageView;

    private StorageReference miStorage;
    private ProgressDialog mProgressDialog;
    private String mPath;

    FirebaseAuth mAuth;

//***************************************************************************************************************

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navDrawerOpen, R.string.navDrawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        mAuth = FirebaseAuth.getInstance();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvPersonNameProfile = findViewById(R.id.tvPersonNameProfile);
        tvEmailProfile = findViewById(R.id.tvEmailProfile);
        tvPNumberProfile = findViewById(R.id.tvPNumberProfile);
        mOptionButton = findViewById(R.id.buttonimg);
        mRlView = findViewById(R.id.rlView);
        mSetImage = findViewById(R.id.imageprofile);
        miStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);

        if(myRequestStoragePermission()){
            mOptionButton.setEnabled(true);
        }else{
            mOptionButton.setEnabled(false);
        }
    }

    private boolean myRequestStoragePermission() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;
        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(mRlView, "Los permisos son necesarios para poder usar la app",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
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
        tvNavDrawerUser = findViewById(R.id.tvNavDrawerUser);
        tvNavDrawerEmail = findViewById(R.id.tvNavDrawerEmail);
        user = database.getLoggedUser(this);
        mSetImage = findViewById(R.id.imageprofile);
        mImageView = findViewById(R.id.imageViewUser23);
        getMenuInflater().inflate(R.menu.menu_derecho,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.camara) {
            showOptions();
        } else if(id == R.id.pencil){
            //dialogo editar perfil
        }


        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Intent intent = new Intent(PerfilActivity.this, HomeActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_ftd) {

        } else if (id == R.id.nav_fr) {

        } else if (id == R.id.nav_mc) {
            Intent intent = new Intent(PerfilActivity.this, CommunitiesActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_mp) {
            Intent intent = new Intent(PerfilActivity.this, PerfilActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_sett) {

        } else if (id == R.id.nav_exit) {
            Intent intent = new Intent(PerfilActivity.this, Splash_Screen.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void loggedUser(User user) {
        tvNavDrawerUser.setText(user.getPersonName());
        tvNavDrawerEmail.setText(user.getEmail());
        tvPersonNameProfile.setText(user.getPersonName());
        tvEmailProfile.setText(user.getEmail());
        tvPNumberProfile.setText(user.getPnumber());

        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageRef = storage.getReferenceFromUrl("gs://coffe-36307.appspot.com").child("Users").child(mAuth.getCurrentUser().getUid()).child("fotodeperfil");
            final File localFile;

            localFile = File.createTempFile("images", "jpg");

            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    mSetImage.setImageBitmap(bitmap);
                    mImageView.setImageBitmap(bitmap);


                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @SuppressLint("NewApi")
    public void showOptions() {
        final CharSequence[] option = {"Camara", "Galeria", "Cancelar"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
              if (option[which]=="Camara") {
                  openCamera();
              }else if(option[which]=="Galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona la app de imagenes"), SELECT_PICTURE);
              } else {
                  dialog.dismiss();
              }
            }
        });
        builder.show();
/*
        LayoutInflater inflater = this.getLayoutInflater();
        View v = inflater.inflate(R.layout.activity_imagen_profile_dialog, null);
        builder.setView(v);
        builder.create().show();
*/
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if (!isDirectoryCreated){
            isDirectoryCreated = file.mkdirs();
        }
        if (isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".png";

            mPath = Environment.getExternalStorageState() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch(requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path);
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });
                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    mProgressDialog.setTitle("Subiendo Foto");
                    mProgressDialog.setMessage("Subiendo Foto");
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();


                    final Uri path = data.getData();
                    final String foto = "fotodeperfil";
                    StorageReference filePath = miStorage.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child(foto);
                    filePath.putFile(path).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            String fotourl = "gs://coffe-36307.appspot.com/fotos/" + foto;
                            //******************************************+

                            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("Users");
                            DatabaseReference currentUserDB =mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()).child("photourl");
                            currentUserDB.setValue(fotourl);

                            //DataRef.child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("urlfoto").setValue(downloadUrl.toString());
                            mProgressDialog.dismiss();
                            mSetImage.setImageURI(path);
                            mImageView.setImageURI(path);

                            Toast.makeText(PerfilActivity.this, "Foto añadida con exito",Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(PerfilActivity.this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mOptionButton.setEnabled(true);
            }
        } else {
            showExplanation();
        }
    }
         private void showExplanation(){
        AlertDialog.Builder builder = new AlertDialog.Builder(PerfilActivity.this);
        builder.setTitle("PERMISOS DENEGADOS");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}

