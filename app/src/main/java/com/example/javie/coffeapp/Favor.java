package com.example.javie.coffeapp;

/**
 * Created by Carlos Vega Gonzalez on 11/03/2018.
 */

public class Favor {
    private String title, description, date;

    public Favor(String title, String description, String date) {
        this.title = title;
        this.description = description;
        this.date = date;
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
}
