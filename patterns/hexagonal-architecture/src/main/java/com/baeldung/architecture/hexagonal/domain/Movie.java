package com.baeldung.architecture.hexagonal.domain;

public class Movie {

    private int id;
    private String name;
    private String director;
    private int duration;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public static Movie fromMovieDetails(Movie movieCreated) {
        // TODO Auto-generated method stub
        return null;
    }

}
