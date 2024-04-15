package com.baeldung.shallowvsdeepcopy;

public class Book {

    private String title;
    private String isbn;
    private Author author;

    public Book(String title, String isbn, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
    }

    // Shallow copy
    public Book(Book book) {
        this.title = book.title;
        this.isbn = book.isbn;
        this.author = book.author;
    }

    // Deep copy
    public Book deepCopy() {
        return new Book(this.title, this.isbn, new Author(this.author.getName(), this.author.getAge()));
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

    @Override
    public String toString() {
        return "{" + "title='" + title + '\''
            + ", isbn='" + isbn + '\''
            + ", author=" + author + '\''
            + ", hashcode=" + hashCode()
            + '}';
    }

}
