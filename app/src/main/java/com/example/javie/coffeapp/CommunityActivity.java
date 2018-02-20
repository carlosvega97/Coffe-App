package com.example.javie.coffeapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CommunityActivity extends AppCompatActivity {
    private ListView lista;
    private CommunityAdapter adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community);

        lista = (ListView) findViewById(R.id.Community_list);
        adaptor = new CommunityAdapter(GetArrayItems(),this);
        lista.setAdapter(adaptor);
    }
    private ArrayList <CommunityData> GetArrayItems(){

        ArrayList <CommunityData> Lista = new ArrayList<>();

        Lista.add(new CommunityData(R.drawable.addhouse,"Mis cojones en tinta", "Pablito tontito"));
        Lista.add(new CommunityData(R.drawable.addhouse,"Mis cojones en tinta", "Pablito tontito"));
        Lista.add(new CommunityData(R.drawable.addhouse,"Mis cojones en tinta", "Pablito tontito"));

        return Lista;
    }



}
