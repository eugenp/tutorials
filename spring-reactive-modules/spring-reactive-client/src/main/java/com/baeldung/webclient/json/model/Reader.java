package com.baeldung.webclient.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reader {
    private final int id;
    private final String name;
    private final Book favouriteBook;
    private final List<Book> booksRead;

    @JsonCreator
    public Reader(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("favouriteBook") Book favouriteBook,
      @JsonProperty("booksRead") List<Book> booksRead) {
        this.id = id;
        this.name = name;
        this.favouriteBook = favouriteBook;
        this.booksRead =booksRead;
    }

    public Book getFavouriteBook() {
        return favouriteBook;
    }

    public List<Book> getBooksRead() { return booksRead; }
}
