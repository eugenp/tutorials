package com.baeldung.gson.entities;

import java.util.List;

public class Movie {

    private String imdbId;
    private String director;
    private List<ActorGson> actors;

    public Movie(String imdbID, String director, List<ActorGson> actors) {
        super();
        this.imdbId = imdbID;
        this.director = director;
        this.actors = actors;
    }

    @Override
    public String toString() {
        return "Movie [imdbId=" + imdbId + ", director=" + director + ", actors=" + actors + "]";
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