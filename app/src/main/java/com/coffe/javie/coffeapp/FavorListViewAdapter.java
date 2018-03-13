package com.coffe.javie.coffeapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pabli on 03/12/2018.
 */

public class FavorListViewAdapter implements ListAdapter {
    private ArrayList<Favor> favorList;
    private Context context;
    private TextView tVFavorTitleTemplate, tVFavorDateTemplate;

    public FavorListViewAdapter(ArrayList<Favor> favorList, Context context) {
        this.favorList = favorList;
        this.context = context;
    }

    public ArrayList<Favor> getFavorList() {
        return favorList;
    }

    public void setFavorList(ArrayList<Favor> favorList) {
        this.favorList = favorList;
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
        return favorList.size();
    }

    @Override
    public Object getItem(int position) {
        return favorList.get(position);
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
        View favorTemplateView = inflater.inflate(R.layout.favor_list_view_template, parent, false);
        tVFavorTitleTemplate = favorTemplateView.findViewById(R.id.tVFavorTitleTemplate);
        tVFavorDateTemplate = favorTemplateView.findViewById(R.id.tVFavorDateTemplate);
        tVFavorTitleTemplate.setText(favorList.get(position).getTitle());
        tVFavorDateTemplate.setText(favorList.get(position).getDate());
        return favorTemplateView;
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
        return favorList.isEmpty();
    }
}
