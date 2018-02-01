package com.example.javie.coffeapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    protected TextView mSignUpTextView;
    protected Button mLoginButtom;

    protected EditText editTextEmail;
    protected EditText editTextPass;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth= FirebaseAuth.getInstance();

        mSignUpTextView = (TextView)findViewById(R.id.signUpButton);
        mLoginButtom = (Button) findViewById(R.id.loginButton);
        editTextEmail = findViewById(R.id.etEmail1);
        editTextPass = findViewById(R.id.etPass1);

        getSupportActionBar().hide();

        mSignUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        mLoginButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               LoguearUser();
            }
        });
    }

    private void LoguearUser() {
        String email = editTextEmail.getText().toString().trim();
        String pwd = editTextPass.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.v("Login: ", "signInWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.v("Login", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}