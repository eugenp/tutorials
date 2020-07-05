package com.baeldung.hexagonalapp.application;

public class BookDto {
    private final Long id;
    private final String name;
    private final String author;
    private final String borrower;

    public BookDto(Long id, String name, String author, String borrower) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.borrower = borrower;
    }

    public BookDto(Long id, String name, String author) {
        this(id, name, author, null);
    }

    public BookDto(String name, String author) {
        this(null, name, author);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getBorrower() {
        return borrower;
    }

    @Override
    public String toString() {
        return "BookDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", borrower='" + borrower + '\'' +
                '}';
    }
}
