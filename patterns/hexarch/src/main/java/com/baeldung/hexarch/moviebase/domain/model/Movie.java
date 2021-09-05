package com.baeldung.hexarch.moviebase.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * A model class for a movie.
 */
@Data
@AllArgsConstructor
public class Movie {

    @JsonIgnore
    private String id;

    @NonNull
    private String title;

    @NonNull
    private String language;

    @NonNull
    private int year;

    private String country;

    @NonNull
    private Genre genre;

    @NonNull
    private String description;

    @NonNull
    private long duration;

    private double rating;

    @NonNull
    private Person director;

    @NonNull
    private Person[] actors;
}
