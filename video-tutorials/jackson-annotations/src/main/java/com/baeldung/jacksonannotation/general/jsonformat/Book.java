package com.baeldung.jacksonannotation.general.jsonformat;

import com.baeldung.jacksonannotation.domain.Author;
import com.baeldung.jacksonannotation.domain.Item;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Source code github.com/eugenp/tutorials
 *
 * @author Alex Theedom www.baeldung.com
 * @version 1.0
 */
public class Book extends Item {

    private String ISBN;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "dd-MM-yyyy HH:mm:ss")
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
