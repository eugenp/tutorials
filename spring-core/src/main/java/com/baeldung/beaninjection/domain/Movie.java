package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;

public class Movie {

    private String title;
    private Director director;

    @Autowired
    public void setDirector(Director director) {
        this.director = director;
    }

    @Autowired
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", director=" + director +
                '}';
    }
}
