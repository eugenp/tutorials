package com.baeldung.hibernateparameters;

public class Event {
    private Long id;

    private String title;

    public Event() {
    }

    public Event(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    private void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}