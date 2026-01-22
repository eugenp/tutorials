package com.baeldung.smartdoc;

public class Book {

    /**
     * Book ID
     */
    private Long id;
    /**
     * Author
     */
    private String author;
    /**
     * Book Title
     */
    private String title;
    /**
     * Book Price
     */
    private Double price;

    public Book() {
    }

    public Book(Long id, String author, String title, Double price) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.price = price;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
