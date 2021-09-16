package com.example.guiltypleasures;

import android.net.Uri;

public class User {

    public String realname;
    public String username;
    public String email;
    public String profilepicture;
    public String userID;

    public User(){

    }

    public User(String realname, String username, String email, String profilepic){
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.profilepicture = profilepic;
        this.userID = "fake";
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilepicture() {
        return profilepicture;
    }

    public void setProfilepicture(String profilepicture) {
        this.profilepicture = profilepicture;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }
}
