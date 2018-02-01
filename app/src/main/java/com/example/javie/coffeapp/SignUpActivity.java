package com.example.javie.coffeapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    EditText mNameField;
    EditText mEmailField;
    EditText mPasswordField;

    TextView miReturnLogin;

    Button mRegisterButton;

    FirebaseAuth mAuth;
    ProgressDialog mProgess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth= FirebaseAuth.getInstance();
        mNameField = findViewById(R.id.et_user);
        mEmailField = findViewById(R.id.et_email);
        mPasswordField = findViewById(R.id.et_pass);
        mRegisterButton = findViewById(R.id.btn_register);
        miReturnLogin = findViewById(R.id.txtvwLog);

        mProgess = new ProgressDialog(this);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startRegister();
            }
        });
        miReturnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void startRegister() {
        final String name = mNameField.getText().toString().trim();
        String email = mEmailField.getText().toString().trim();
        String password = mPasswordField.getText().toString().trim();

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            mProgess.setMessage("Registering new User, plase wait...");
            mProgess.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            mProgess.dismiss();
                            if(task.isSuccessful()){
                                String user_id = mAuth.getCurrentUser().getUid();
                                Toast.makeText(SignUpActivity.this, user_id, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
}