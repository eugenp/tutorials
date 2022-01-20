package com.baeldung.data;

import com.baeldung.exceptions.DomainException;

import java.time.Duration;

public class Song {

    private String title;
    private String author;
    private Long duration;

    public Song() {
    }

    public Song(String title, String author, Long duration) {
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

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    private void validateDuration(Long duration) {
        if(duration == null || duration < 0){
            throw new DomainException("Duration must be more than 0 seconds");
        }
    }
}
