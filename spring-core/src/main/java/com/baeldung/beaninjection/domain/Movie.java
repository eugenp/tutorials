package com.baeldung.beaninjection.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Movie {

    private Data data;
    private Director director;

    @Autowired
    public void setDirector(Director director) {
        this.director = director;
    }

    @Autowired
    public void setData(Data data) {
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public Director getDirector() {
        return director;
    }
}
