package com.baeldung.shallowcopyvsdeepcopy;

public class Book {

    private String title;
    private String author;
    private Publisher publisher;

    public Book(String title, String author, Publisher publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public Book(Book original) {
        this.title = original.getTitle();
        this.author = original.getAuthor();
        this.publisher = new Publisher(original.getPublisher());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }
}
