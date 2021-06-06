package tech.baeldung.hexagon.books.adapters.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import tech.baeldung.hexagon.books.domain.model.AuthorId;
import tech.baeldung.hexagon.books.domain.model.Content;
import tech.baeldung.hexagon.books.domain.model.Title;

class BookRequest {
    private final String title;
    private final String content;
    private final String authorId;

    BookRequest(@JsonProperty("title") final String title, @JsonProperty("content") final String content, @JsonProperty("authorId") final String authorId) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
    }


    Title title() {
        return Title.of(title);
    }

    Content content() {
        return Content.of(content);
    }

    AuthorId authorId() {
        return AuthorId.of(authorId);
    }

}
