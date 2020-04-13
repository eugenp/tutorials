package com.baeldung.hexagonal.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class Movie {

    private final UUID id;
    private final String name;
    private final List<Rating> ratings = new ArrayList<>();

    public Movie(final UUID id, final String name) {
        this.id = id;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UUID addRating(final String reviewer, final int noOfStars) {
        final Rating rating = new Rating(UUID.randomUUID(), reviewer, noOfStars);
        this.ratings.add(rating);
        return rating.getId();
    }

    public List<Rating> getAllRatings() {
        return Collections.unmodifiableList(ratings);
    }

    public double getAverageRating() {
        return getAllRatings().stream()
            .mapToInt(r -> r.getNoOfStars())
            .summaryStatistics()
            .getAverage();
    }
}
