package com.coffe.javie.coffeapp;

/**
 * Created by Carlos Vega Gonzalez on 11/03/2018.
 */

public class Favor {
    private String title, description, date, owner, taken;

    public Favor(String title, String description, String date, String owner, String taken) {
        this.title = title;
        this.description = description;
        this.date = date;
        this.owner = owner;
        this.taken = taken;
    }

    public Favor() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }
}
