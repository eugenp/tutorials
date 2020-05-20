package com.baeldung.architecture.hexagonal.business.bordery.dto;

public class BookDto {

    private Long id;
    private String author;
    private String name;

    public BookDto(String author, String name) {
        super();
        this.author = author;
        this.name = name;
    }

    public BookDto(Long id, String author, String name) {
        super();
        this.id = id;
        this.author = author;
        this.name = name;
    }

    public BookDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
