package com.baeldung.sequencenaming;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Movie {

    @Id
    @MovieGeneratedId
    private String id;

    private String title;
    private String director;

    // Constructors, getters, and setters
    public Movie() {}

    public Movie(String id, String title, String director) {
        this.id = id;
        this.title = title;
        this.director = director;
    }

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

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
