package com.example.guiltypleasures;

import android.net.Uri;

public class User {

    public String realname, username, email, profilepicture;

    public User(){

    }

    public User(String realname, String username, String email, String profilepic){
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.profilepicture = profilepic;
    }
}
