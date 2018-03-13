package com.example.javie.coffeapp;

import java.util.ArrayList;

/**
 * Created by pabli on 03/01/2018.
 */
public class Community {
    private String name, description, address;
    private ArrayList<String> users;
    private ArrayList<Favor> favorsList;

    public Community(String name, String description, String address, ArrayList<String> users) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.users = users;
    }

    public Community() {
    }

    public Community(String name, String description, String address, ArrayList<String> users, ArrayList<Favor> favorsList) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.users = users;
        this.favorsList = favorsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public ArrayList<Favor> getFavorsList() {return favorsList;}

    public void setFavorsList(ArrayList<Favor> favorsList) {this.favorsList = favorsList;}
}