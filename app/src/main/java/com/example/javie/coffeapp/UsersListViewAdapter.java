package com.example.javie.coffeapp;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pabli on 03/11/2018.
 */

public class UsersListViewAdapter implements ListAdapter {
    private ArrayList<User> userList;
    private TextView tVPersonNameTemplate, tVEmailTemplate;
    private Context context;

    public UsersListViewAdapter(ArrayList<User> userList, Context context) {
        this.userList = userList;
        this.context = context;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void setUserList(ArrayList<User> userList) {
        this.userList = userList;
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
        return userList.size();
    }

    @Override
    public Object getItem(int position) {
        return userList.get(position);
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
        View userTemplateView = inflater.inflate(R.layout.users_list_view_item_template, parent, false);
        tVPersonNameTemplate = userTemplateView.findViewById(R.id.tVPersonNameTemplate);
        tVEmailTemplate = userTemplateView.findViewById(R.id.tVEmailTemplate);
        tVPersonNameTemplate.setText(userList.get(position).getPersonName());
        tVEmailTemplate.setText(userList.get(position).getEmail());
        return userTemplateView;
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
        return userList.isEmpty();
    }
}
