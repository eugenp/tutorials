package com.baeldung.copyobjects.eval;

public class Book {
    private String title;
    private String isbn;
    private Author author;

    // Deep Copy Constructor
    public Book(Book book) {
        this(book.getTitle(), book.getIsbn(), new Author(book.getAuthor()));
    }

    // Shallow Copy Constructor
    public Book(String title, String isbn, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
