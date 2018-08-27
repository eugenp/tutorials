package com.baeldung.jackson.serialization.jsonserialize;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Source code github.com/readlearncode
 *
 * @author Alex Theedom www.readlearncode.com
 * @version 1.0
 */
public class Book extends Item {

    private String ISBN;

    @JsonSerialize(using = CustomDateSerializer.class)
    private Date published;
    private BigDecimal pages;

    public Book() {
    }

    public Book(String title, Author author) {
        super(title, author);
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
}
