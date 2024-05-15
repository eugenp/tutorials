package com.baeldung.entitydtodifferences.entity;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private String address;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Book> books;

    // Default constructor for JPA deserialization
    public User() {
    }

    public User(String firstName, String lastName, String address, List<Book> books) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.books = books;
    }

    public String getNameOfMostOwnedBook() {
        Map<String, Long> bookOwnershipCount = books.stream()
            .collect(Collectors.groupingBy(Book::getName, Collectors.counting()));
        return bookOwnershipCount.entrySet()
            .stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
