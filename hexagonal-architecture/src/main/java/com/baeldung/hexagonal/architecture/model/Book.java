package com.baeldung.hexagonal.architecture.model;

public class Book {
    private Long id;
    private String title;
    private User renter;

    public Book(Long id, String title, User renter) {
        this.id = id;
        this.title = title;
        this.renter = renter;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public User getRenter() {
        return renter;
    }

    public void setRenter(User renter) {
        this.renter = renter;
    }
}
