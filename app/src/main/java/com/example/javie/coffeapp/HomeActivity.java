package com.example.javie.coffeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class HomeActivity extends AppCompatActivity {

    Spinner mSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mSpinner = (Spinner) findViewById(R.id.spinner2);
        String[] datos = new String[] {"MY PROFILE", "FAVORS TO DO", "DONE FAVORS", "FAVORS REQUESTED", "CONFIGURATIONS", "EXIT"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, datos);
        mSpinner.setAdapter(adapter);
    }
}
