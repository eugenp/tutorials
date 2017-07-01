package com.baeldung.setterdi.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Movie {
    private Genre genre;
    private Language language;

    @Autowired
    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    @Autowired
    public void setLanguage(Language language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return String.format("Genre: %s Language: %s", genre, language);
    }
}
