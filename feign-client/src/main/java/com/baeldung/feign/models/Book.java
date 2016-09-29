package com.baeldung.feign.models;

public class Book {
    private String isbn;
    private String author;
    private String title;
    private String synopsis;
    private String language;

    public Book() {
    }

    public Book(String isbn, String author, String title, String synopsis, String language) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.synopsis = synopsis;
        this.language = language;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Book{" +
          "isbn='" + isbn + '\'' +
          ", author='" + author + '\'' +
          ", title='" + title + '\'' +
          ", synopsis='" + synopsis + '\'' +
          ", language='" + language + '\'' +
          '}';
    }
}
