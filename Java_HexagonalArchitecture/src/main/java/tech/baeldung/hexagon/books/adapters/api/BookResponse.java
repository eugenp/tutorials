package tech.baeldung.hexagon.books.adapters.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.baeldung.hexagon.books.domain.model.Book;
import java.io.*;

class BookResponse {
    private final String id;
    private final String title;
    private final String content;
    private final String authorName;

    private BookResponse(final String id, final String title, final String content, final String authorName) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.authorName = authorName;
        System.out.println("book Id: " +  this.id ); //print other fields as well
    }

    static BookResponse of(final Book book) {
        return new BookResponse(book.id().value(),
                book.title().value(),
                book.content().value(),
                book.author().name().value());
    }

    @JsonProperty("id")
    String id() {
        return id;
    }

    @JsonProperty("title")
    String title() {
        return title;
    }

    @JsonProperty("content")
    String content() {
        return content;
    }

    @JsonProperty("authorName")
    String authorName() {
        return authorName;
    }
}
