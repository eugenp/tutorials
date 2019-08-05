package com.baeldung.hexagon.model;

import java.time.LocalDateTime;

public class Ticket {
    private String movie;
    private LocalDateTime timestamp;

    public Ticket(String movie, LocalDateTime timestamp) {
        this.movie = movie;
        this.timestamp = timestamp;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
