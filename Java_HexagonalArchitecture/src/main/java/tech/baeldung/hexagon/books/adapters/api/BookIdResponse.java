package tech.baeldung.hexagon.books.adapters.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.baeldung.hexagon.books.domain.model.BookId;

class BookIdResponse {

    private final String id;

    private BookIdResponse(final String id) {
        this.id = id;
    }

    static BookIdResponse of(final BookId bookId) {
        return new BookIdResponse(bookId.value());
    }

    @JsonProperty("id")
    String id() {
        return id;
    }

}
