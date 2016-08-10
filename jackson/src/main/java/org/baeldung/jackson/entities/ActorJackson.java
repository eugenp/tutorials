package org.baeldung.jackson.entities;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class ActorJackson {

    private String imdbId;
    private Date dateOfBirth;
    private List<String> filmography;

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<String> getFilmography() {
        return filmography;
    }

    public void setFilmography(List<String> filmography) {
        this.filmography = filmography;
    }

    public ActorJackson(String imdbId, Date dateOfBirth, List<String> filmography) {
        super();
        this.imdbId = imdbId;
        this.dateOfBirth = dateOfBirth;
        this.filmography = filmography;
    }

    public ActorJackson() {

        super();
    }

    @Override
    public String toString() {
        return "ActorJackson [imdbId=" + imdbId + ", dateOfBirth=" + dateOfBirth + ", filmography=" + filmography + "]";
    }

}
