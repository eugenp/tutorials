package com.baeldung.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movie", propOrder = { "imdbId", "title" })
public class Movie {

    protected String imdbId;
    protected String title;

    public Movie(String imdbId, String title) {
        this.imdbId = imdbId;
        this.title = title;
    }

    public Movie() {}

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Movie movie = (Movie) o;

        if (imdbId != null ? !imdbId.equals(movie.imdbId) : movie.imdbId != null)
            return false;
        return title != null ? title.equals(movie.title) : movie.title == null;

    }

    @Override
    public int hashCode() {
        int result = imdbId != null ? imdbId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "imdbId='" + imdbId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
