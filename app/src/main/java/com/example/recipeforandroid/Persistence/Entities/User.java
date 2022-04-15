package com.example.recipeforandroid.Persistence.Entities;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("ID")
    private long mID;
    @SerializedName("userName")
    private String mUsername;
    @SerializedName("password")
    private String mPassword;


    public User(String username, String password) {
        this.mUsername = username;
        this.mPassword = password;
    }

    @Override
    public String toString() {
        return "Users{" +
                "ID=" + mID +
                "userName='" + mUsername + '\'' +
                ", password='" + mPassword + '\'' +
                "}";
    }

    public long getID() {
        return mID;
    }

    public void setID(long ID) {
        this.mID = ID;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        this.mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}
