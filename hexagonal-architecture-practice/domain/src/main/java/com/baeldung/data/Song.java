package com.baeldung.data;

import com.baeldung.exceptions.DomainException;

import java.time.Duration;

public class Song {

    private String title;
    private String author;
    private Duration duration;

    public Song() {
    }

    public Song(String title, String author, Duration duration) {
        validateDuration(duration);
        this.title = title;
        this.author = author;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    private void validateDuration(Duration duration) {
        if(duration == null || duration.isZero() || duration.isNegative()){
            throw new DomainException("Duration must be more than 0 seconds");
        }
    }
}
