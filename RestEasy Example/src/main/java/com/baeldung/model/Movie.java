
package com.baeldung.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "movie", propOrder = {
    "actors",
    "awards",
    "country",
    "director",
    "genre",
        "imdbId",
    "imdbRating",
    "imdbVotes",
    "language",
    "metascore",
    "plot",
    "poster",
    "rated",
    "released",
    "response",
    "runtime",
    "title",
    "type",
    "writer",
    "year"
})
public class Movie {

    protected String actors;
    protected String awards;
    protected String country;
    protected String director;
    protected String genre;
    protected String imdbId;
    protected String imdbRating;
    protected String imdbVotes;
    protected String language;
    protected String metascore;
    protected String plot;
    protected String poster;
    protected String rated;
    protected String released;
    protected String response;
    protected String runtime;
    protected String title;
    protected String type;
    protected String writer;
    protected String year;

    public String getActors() {
        return actors;
    }

    public void setActors(String value) {
        this.actors = value;
    }

    public String getAwards() {
        return awards;
    }

    public void setAwards(String value) {
        this.awards = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String value) {
        this.director = value;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String value) {
        this.genre = value;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String value) {
        this.imdbId = value;
    }

    public String getImdbRating() {
        return imdbRating;
    }

    public void setImdbRating(String value) {
        this.imdbRating = value;
    }

    public String getImdbVotes() {
        return imdbVotes;
    }

    public void setImdbVotes(String value) {
        this.imdbVotes = value;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String value) {
        this.language = value;
    }

    public String getMetascore() {
        return metascore;
    }

    public void setMetascore(String value) {
        this.metascore = value;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String value) {
        this.plot = value;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String value) {
        this.poster = value;
    }

    public String getRated() {
        return rated;
    }

    public void setRated(String value) {
        this.rated = value;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String value) {
        this.released = value;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String value) {
        this.response = value;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String value) {
        this.runtime = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String value) {
        this.type = value;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String value) {
        this.writer = value;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String value) {
        this.year = value;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "actors='" + actors + '\'' +
                ", awards='" + awards + '\'' +
                ", country='" + country + '\'' +
                ", director='" + director + '\'' +
                ", genre='" + genre + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", imdbRating='" + imdbRating + '\'' +
                ", imdbVotes='" + imdbVotes + '\'' +
                ", language='" + language + '\'' +
                ", metascore='" + metascore + '\'' +
                ", poster='" + poster + '\'' +
                ", rated='" + rated + '\'' +
                ", released='" + released + '\'' +
                ", response='" + response + '\'' +
                ", runtime='" + runtime + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", writer='" + writer + '\'' +
                ", year='" + year + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (imdbId != null ? !imdbId.equals(movie.imdbId) : movie.imdbId != null) return false;
        return title != null ? title.equals(movie.title) : movie.title == null;

    }

    @Override
    public int hashCode() {
        int result = imdbId != null ? imdbId.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }
}
