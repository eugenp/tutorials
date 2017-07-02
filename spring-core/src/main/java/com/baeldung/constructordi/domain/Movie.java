package com.baeldung.constructordi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Movie {
    private Genre genre;
    private Language language;
    
    @Autowired
    public Movie(Genre genre, Language language) {
        super();
        this.genre = genre;
        this.language = language;
    }
    
    @Override
    public String toString() {
        return String.format("Genre: %s Language: %s", genre, language);
    }
}
