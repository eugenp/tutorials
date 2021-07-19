package com.baeldung.gson.entities;

import com.google.gson.annotations.Expose;

import java.util.List;

public class MovieWithNullValue {

    @Expose
    private String imdbId;
    private String director;

    @Expose
    private List<ActorGson> actors;

    public MovieWithNullValue(String imdbID, String director, List<ActorGson> actors) {
        super();
        this.imdbId = imdbID;
        this.director = director;
        this.actors = actors;
    }

    public String getImdbID() {
        return imdbId;
    }

    public void setImdbID(String imdbID) {
        this.imdbId = imdbID;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public List<ActorGson> getActors() {
        return actors;
    }

    public void setActors(List<ActorGson> actors) {
        this.actors = actors;
    }
}