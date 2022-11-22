package com.example.guiltypleasures;

public class UpcomingMovieClass {
    //movie variables
    String id;
    String title;
    String img;

    //constructor
    public UpcomingMovieClass(String id, String title, String img) {
        this.id = id;
        this.title = title;
        this.img = img;
    }

    //constructor 2
    public UpcomingMovieClass() {
    }

    //getters and setters
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }
    public void setImg(String img) {
        this.img = img;
    }
}
