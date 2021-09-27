package com.example.guiltypleasures;

public class UpcomingMovieClass {
    //movie variables
    String id;
    String title;
    String img;
    String backImg;
    String overview;
    String releaseDate;


    //constructor
    public UpcomingMovieClass(String id, String title, String img, String backImg, String overview, String releaseDate) {
        this.id = id;
        this.title = title;
        this.img = img;
        this.backImg = backImg;
        this.overview = overview;
        this.releaseDate = releaseDate;

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
    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}
