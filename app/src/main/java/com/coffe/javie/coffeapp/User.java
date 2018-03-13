package com.coffe.javie.coffeapp;

/**
 * Created by javie on 12/02/2018.
 */

public class User {
    private String email, personName;
    private String pnumber;
    private String photourl;

    public User(String email, String personName, String pnumber, String photourl) {
        this.email = email;
        this.personName = personName;
        this.pnumber = pnumber;
        this.photourl = photourl;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPnumber() {
        return pnumber;
    }

    public void setPnumber(String pnumber) {
        this.pnumber = pnumber;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }
}
