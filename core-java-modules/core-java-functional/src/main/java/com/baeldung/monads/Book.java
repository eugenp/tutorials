package com.baeldung.monads;

public class Book {
    private String isbn;
    private Long authorId;
    private String genre;

    public Book(String isbn, Long authorId, String genre) {
        this.isbn = isbn;
        this.authorId = authorId;
        this.genre = genre;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public String getIsbn() {
        return isbn;
    }

    public String genre() {
        return genre;
    }
}
