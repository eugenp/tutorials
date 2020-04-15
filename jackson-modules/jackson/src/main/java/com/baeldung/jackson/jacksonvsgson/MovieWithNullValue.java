package com.baeldung.jackson.jacksonvsgson;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class MovieWithNullValue {

    private String imdbId;

    @JsonIgnore
    private String director;

    private List<ActorJackson> actors;

    public MovieWithNullValue(String imdbID, String director, List<ActorJackson> actors) {
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

    public List<ActorJackson> getActors() {
        return actors;
    }

    public void setActors(List<ActorJackson> actors) {
        this.actors = actors;
    }
}