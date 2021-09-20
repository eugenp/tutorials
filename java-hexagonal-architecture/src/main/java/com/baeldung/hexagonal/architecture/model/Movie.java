package com.baeldung.hexagonal.architecture.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The class defines the domain object model
 */
public class Movie {
    
    public Movie() {
        super();
    }
    
    public Movie(Integer movieId, String movieName, String movieTrailer, String movieOverview,
        String moviePoster, Integer movieLength) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieTrailer = movieTrailer;
        this.movieOverview = movieOverview;
        this.moviePoster = moviePoster;
        this.movieLength = movieLength;
    }

    @JsonProperty("movie_id")
    private Integer movieId;

    @JsonProperty("movie_name")
    private String movieName;

    @JsonProperty("movie_trailer")
    private String movieTrailer;

    @JsonProperty("movie_overview")
    private String movieOverview;

    @JsonProperty("movie_poster")
    private String moviePoster;

    @JsonProperty("length")
    private Integer movieLength;

    public Integer getMovieId() {
            return movieId;
    }

    public void setMovieId(Integer movieId) {
            this.movieId = movieId;
    }

    public String getMovieName() {
            return movieName;
    }

    public void setMovieName(String movieName) {
            this.movieName = movieName;
    }

    public String getMovieTrailer() {
            return movieTrailer;
    }

    public void setMovieTrailer(String movieTrailer) {
            this.movieTrailer = movieTrailer;
    }

    public String getMovieOverview() {
            return movieOverview;
    }

    public void setMovieOverview(String movieOverview) {
            this.movieOverview = movieOverview;
    }

    public String getMoviePoster() {
            return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
            this.moviePoster = moviePoster;
    }

    public Integer getMovieLength() {
            return movieLength;
    }

    public void setMovieLength(Integer movieLength) {
            this.movieLength = movieLength;
    }

    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
        result = prime * result + ((movieLength == null) ? 0 : movieLength.hashCode());
        result = prime * result + ((movieName == null) ? 0 : movieName.hashCode());
        result = prime * result + ((movieOverview == null) ? 0 : movieOverview.hashCode());
        result = prime * result + ((moviePoster == null) ? 0 : moviePoster.hashCode());
        result = prime * result + ((movieTrailer == null) ? 0 : movieTrailer.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Movie other = (Movie) obj;
        if (movieId == null) {
            if (other.movieId != null)
                return false;
        } else if (!movieId.equals(other.movieId))
            return false;
        if (movieLength == null) {
            if (other.movieLength != null)
                return false;
        } else if (!movieLength.equals(other.movieLength))
            return false;
        if (movieName == null) {
            if (other.movieName != null)
                return false;
        } else if (!movieName.equals(other.movieName))
            return false;
        if (movieOverview == null) {
            if (other.movieOverview != null)
                return false;
        } else if (!movieOverview.equals(other.movieOverview))
            return false;
        if (moviePoster == null) {
            if (other.moviePoster != null)
                return false;
        } else if (!moviePoster.equals(other.moviePoster))
            return false;
        if (movieTrailer == null) {
            if (other.movieTrailer != null)
                return false;
        } else if (!movieTrailer.equals(other.movieTrailer))
            return false;
        return true;
    }

    @Override
    public String toString() {
            return "MovieOutput [movieId=" + movieId + ", movieName=" + movieName + ", movieTrailer=" + movieTrailer
                            + ", movieOverview=" + movieOverview + ", moviePoster=" + moviePoster + ", movieLength=" + movieLength
                            + "]";
    }
}
