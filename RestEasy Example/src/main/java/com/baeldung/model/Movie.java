
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

    /**
     * Recupera il valore della propriet� actors.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActors() {
        return actors;
    }

    /**
     * Imposta il valore della propriet� actors.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActors(String value) {
        this.actors = value;
    }

    /**
     * Recupera il valore della propriet� awards.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAwards() {
        return awards;
    }

    /**
     * Imposta il valore della propriet� awards.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAwards(String value) {
        this.awards = value;
    }

    /**
     * Recupera il valore della propriet� country.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountry() {
        return country;
    }

    /**
     * Imposta il valore della propriet� country.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Recupera il valore della propriet� director.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirector() {
        return director;
    }

    /**
     * Imposta il valore della propriet� director.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirector(String value) {
        this.director = value;
    }

    /**
     * Recupera il valore della propriet� genre.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGenre() {
        return genre;
    }

    /**
     * Imposta il valore della propriet� genre.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGenre(String value) {
        this.genre = value;
    }

    /**
     * Recupera il valore della propriet� imdbId.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * Imposta il valore della propriet� imdbId.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImdbId(String value) {
        this.imdbId = value;
    }

    /**
     * Recupera il valore della propriet� imdbRating.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImdbRating() {
        return imdbRating;
    }

    /**
     * Imposta il valore della propriet� imdbRating.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImdbRating(String value) {
        this.imdbRating = value;
    }

    /**
     * Recupera il valore della propriet� imdbVotes.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImdbVotes() {
        return imdbVotes;
    }

    /**
     * Imposta il valore della propriet� imdbVotes.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImdbVotes(String value) {
        this.imdbVotes = value;
    }

    /**
     * Recupera il valore della propriet� language.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Imposta il valore della propriet� language.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Recupera il valore della propriet� metascore.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMetascore() {
        return metascore;
    }

    /**
     * Imposta il valore della propriet� metascore.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMetascore(String value) {
        this.metascore = value;
    }

    /**
     * Recupera il valore della propriet� plot.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPlot() {
        return plot;
    }

    /**
     * Imposta il valore della propriet� plot.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPlot(String value) {
        this.plot = value;
    }

    /**
     * Recupera il valore della propriet� poster.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPoster() {
        return poster;
    }

    /**
     * Imposta il valore della propriet� poster.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPoster(String value) {
        this.poster = value;
    }

    /**
     * Recupera il valore della propriet� rated.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRated() {
        return rated;
    }

    /**
     * Imposta il valore della propriet� rated.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRated(String value) {
        this.rated = value;
    }

    /**
     * Recupera il valore della propriet� released.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReleased() {
        return released;
    }

    /**
     * Imposta il valore della propriet� released.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReleased(String value) {
        this.released = value;
    }

    /**
     * Recupera il valore della propriet� response.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResponse() {
        return response;
    }

    /**
     * Imposta il valore della propriet� response.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResponse(String value) {
        this.response = value;
    }

    /**
     * Recupera il valore della propriet� runtime.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuntime() {
        return runtime;
    }

    /**
     * Imposta il valore della propriet� runtime.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuntime(String value) {
        this.runtime = value;
    }

    /**
     * Recupera il valore della propriet� title.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Imposta il valore della propriet� title.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Recupera il valore della propriet� type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Imposta il valore della propriet� type.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Recupera il valore della propriet� writer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWriter() {
        return writer;
    }

    /**
     * Imposta il valore della propriet� writer.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWriter(String value) {
        this.writer = value;
    }

    /**
     * Recupera il valore della propriet� year.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getYear() {
        return year;
    }

    /**
     * Imposta il valore della propriet� year.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
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
