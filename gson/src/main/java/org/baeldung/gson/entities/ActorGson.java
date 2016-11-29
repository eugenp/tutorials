package org.baeldung.gson.entities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class ActorGson {

    private String imdbId;
    private Date dateOfBirth;
    private List<String> filmography;

    public ActorGson(String imdbId, Date dateOfBirth, List<String> filmography) {
        super();
        this.imdbId = imdbId;
        this.dateOfBirth = dateOfBirth;
        this.filmography = filmography;
    }

    @Override
    public String toString() {
        return "ActorGson [imdbId=" + imdbId + ", dateOfBirth=" + formatDateOfBirth() + ", filmography=" + filmography + "]";
    }

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

    private String formatDateOfBirth() {
        final DateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
        return formatter.format(dateOfBirth);
    }

}