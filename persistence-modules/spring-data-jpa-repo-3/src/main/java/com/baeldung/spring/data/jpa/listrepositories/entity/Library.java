package com.baeldung.spring.data.jpa.listrepositories.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "library")
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Convert(converter = StringListConverter.class)
    @Column(name = "addresses", nullable = false)
    private List<String> addresses = new ArrayList<>();

    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "books", joinColumns = @JoinColumn(name = "library_id"))
    @Column(name = "book", nullable = false)
    private List<String> books = new ArrayList<>();

    public Library() {
    }

    public Library(String name, List<String> addresses, List<String> books) {
        this.name = name;
        this.addresses = addresses;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<String> addresses) {
        this.addresses = addresses;
    }

    public List<String> getBooks() {
        return books;
    }

    public void setBooks(List<String> books) {
        this.books = books;
    }
}
