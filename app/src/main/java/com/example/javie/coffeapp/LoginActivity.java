package com.example.javie.coffeapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class LoginActivity extends AppCompatActivity {
    protected RelativeLayout relativeLayout;

    protected TextView mSignUpTextView;
    protected  TextView mRmPass;
    protected Button mLoginButtom;

    protected EditText editTextEmail;
    protected EditText editTextPass;

    ProgressDialog mProgess;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        mSignUpTextView = (TextView)findViewById(R.id.signUpButton);
        mLoginButtom = (Button) findViewById(R.id.loginButton);
        editTextEmail = findViewById(R.id.etEmail1);
        editTextPass = findViewById(R.id.etPass1);
        mRmPass = findViewById(R.id.txtRmPass);
        relativeLayout = findViewById(R.id.relLayLogin);

        mLoginButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LoguearUser();
            }
        });

        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        mRmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(LoginActivity.this, ResetPassActivity.class);
                startActivity(intent);*/
                createMessageDialogo();
            }
        });
    }

    private void LoguearUser() {
        final String email = editTextEmail.getText().toString().trim();
        String pwd = editTextPass.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.v("Login: ", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    Snackbar.make(relativeLayout, "Welcome" + email, Snackbar.LENGTH_SHORT).show();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.v("Login", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @SuppressLint("NewApi")
    public void createMessageDialogo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = this.getLayoutInflater();

        View v = inflater.inflate(R.layout.actoivity_dialogrempass, null);
        builder.setView(v);



        builder.create().show();
    }



}