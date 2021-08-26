package com.example.guiltypleasures;

import android.net.Uri;

public class User {

    public String realname, username, email;
    public Uri profilepicture;

    public User(){

    }

    public User(String realname, String username, String email){
        this.realname = realname;
        this.username = username;
        this.email = email;
        profilepicture = null;
    }

}
