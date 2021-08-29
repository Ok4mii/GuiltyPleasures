package com.example.guiltypleasures;

import android.net.Uri;

public class User {

    public String realname, username, email, profilepicture;
    boolean lightmode;

    public User(){

    }

    public User(String realname, String username, String email, String profilepic, boolean light){
        this.realname = realname;
        this.username = username;
        this.email = email;
        this.profilepicture = profilepic;
        this.lightmode = light;
    }

}
