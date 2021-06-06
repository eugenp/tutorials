package tech.baeldung.hexagon.books.adapters.messagebroker;

import tech.baeldung.hexagon.books.domain.model.Book;

import java.time.ZonedDateTime;

class BookCreatedMessage {

    private final Book book;
    private final ZonedDateTime sentAt;

    private BookCreatedMessage(final Book book, final ZonedDateTime sentAt) {
        this.book = book;
        this.sentAt = sentAt;
    }

    static BookCreatedMessage of(Book book) {
        return new BookCreatedMessage(book, ZonedDateTime.now());
    }

    @Override
    public String toString() {
        return String.format("\"book >>%s<< created\"", book.title().value());
    }
}
