package com.baeldung.webclient.json.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reader {
    private final int id;
    private final String name;
    private final List<Book> favouriteBooks;

    @JsonCreator
    public Reader(
      @JsonProperty("id") int id,
      @JsonProperty("name") String name,
      @JsonProperty("favouriteBooks") List<Book> favoriteBooks) {
        this.id = id;
        this.name = name;
        this.favouriteBooks = favoriteBooks;
    }

    public String getName() {
        return name;
    }

    public List<Book> getFavouriteBooks() { return favouriteBooks; }
}
