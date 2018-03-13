package com.coffe.javie.coffeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity {

    EditText mNameField;
    EditText mEmailField;
    EditText mPasswordField;
    EditText mTlfField;
    RelativeLayout relativeLayout;
    TextView miReturnLogin;

    Button mRegisterButton;

    FirebaseAuth mAuth;
    ProgressDialog mProgess;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_sign_up);

        mAuth= FirebaseAuth.getInstance();
        mNameField = findViewById(R.id.et_user);
        mEmailField = findViewById(R.id.et_email);
        mPasswordField = findViewById(R.id.et_pass);
        mRegisterButton = findViewById(R.id.btn_register);
        miReturnLogin = findViewById(R.id.txtvwLog);
        mTlfField = findViewById(R.id.et_tlf);
        relativeLayout = findViewById(R.id.crtllyt);
        mProgess = new ProgressDialog(this);

        miReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.rigth_in, R.anim.right_out);
            }
        });
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });


    }
    private void startRegister() {
        final String personName = mNameField.getText().toString().trim();
        final String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();
        final String pNumber = mTlfField.getText().toString().trim();
        final String photourl = "null";

        if (!TextUtils.isEmpty(personName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(pNumber)){
            mProgess.setMessage("Registering new User, please wait...");
            mProgess.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgess.dismiss();
                            if(task.isSuccessful()) {
                                String user_id = mAuth.getCurrentUser().getUid();
                                Snackbar.make(relativeLayout, "You were successfully registered with this email: " + email, Snackbar.LENGTH_SHORT).show();

                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");
                                DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                currentUserDB.setValue(new User(email, personName, pNumber, photourl));

                                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else {
                                Snackbar.make(relativeLayout, "this Email:  "+ email +" is already registered", Snackbar.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }
}