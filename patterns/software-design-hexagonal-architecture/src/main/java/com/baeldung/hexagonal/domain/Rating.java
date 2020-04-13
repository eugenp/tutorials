package com.baeldung.hexagonal.domain;

import java.util.UUID;

public class Rating {

    private final UUID id;
    private final String reviewer;
    private final int noOfStars;

    public Rating(final UUID id, final String reviewer, final int noOfStars) {
        this.id = id;
        this.reviewer = reviewer;
        this.noOfStars = noOfStars;
    }

    public int getNoOfStars() {
        return noOfStars;
    }

    public String getReviewer() {
        return reviewer;
    }

    public UUID getId() {
        return id;
    }

}
