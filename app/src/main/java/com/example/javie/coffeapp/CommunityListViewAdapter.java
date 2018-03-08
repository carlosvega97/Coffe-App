package com.example.javie.coffeapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by pabli on 03/08/2018.
 */

public class CommunityListViewAdapter implements ListAdapter {
    private ArrayList<Community> communities;
    private TextView tVTitleCommunityTemplate;
    private Context context;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;

    public CommunityListViewAdapter(ArrayList<Community> communities, Context context) {
        this.communities = communities;
        this.context = context;
    }

    public ArrayList<Community> getCommunities() {
        return communities;
    }

    public void setCommunities(ArrayList<Community> communities) {
        this.communities = communities;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return communities.size();
    }

    @Override
    public Object getItem(int position) {
        return communities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View communityTemplateView = inflater.inflate(R.layout.community_list_view_item_template, parent, false);
        tVTitleCommunityTemplate = communityTemplateView.findViewById(R.id.tvCommunityTitleTemplate);
        tVTitleCommunityTemplate.setText(communities.get(position).getName());
        return communityTemplateView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return communities.isEmpty();
    }
}
