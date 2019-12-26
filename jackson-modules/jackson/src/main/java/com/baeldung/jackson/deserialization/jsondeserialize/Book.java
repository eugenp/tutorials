package com.baeldung.jackson.deserialization.jsondeserialize;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public class Book {

    private UUID id;
    private String title;
    private float price;
    private String ISBN;

    @JsonDeserialize(using = CustomDateDeserializer.class)
    private Date published;
    private BigDecimal pages;

    public Book() {
    }

    public Book(String title) {
        this.id = UUID.randomUUID();
        this.title = title;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public BigDecimal getPages() {
        return pages;
    }

    public void setPages(BigDecimal pages) {
        this.pages = pages;
    }
    
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
