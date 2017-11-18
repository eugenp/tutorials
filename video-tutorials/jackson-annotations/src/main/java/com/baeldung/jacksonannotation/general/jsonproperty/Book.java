package com.baeldung.jacksonannotation.general.jsonproperty;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    private Date published;
    private BigDecimal pages;
    private String binding;

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

    @JsonProperty("binding")
    public String coverBinding() {
        return binding;
    }

    @JsonProperty("binding")
    public void configureBinding(String binding) {
        this.binding = binding;
    }
}
