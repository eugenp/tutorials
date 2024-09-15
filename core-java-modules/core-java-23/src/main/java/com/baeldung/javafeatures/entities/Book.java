package com.baeldung.javafeatures.entities;

class Book {

    private Title title;
    private String author;

    public Book(Title title, String author) {
        this.title = title;
        this.author = author;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Book deepCopy() {
        return new Book(title.deepCopy(), author);
    }
}