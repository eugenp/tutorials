package tech.baeldung.hexagon.books.adapters.messagebroker;

import tech.baeldung.hexagon.books.domain.model.Book;

import java.time.ZonedDateTime;

class BookRetrievedMessage {

    private final Book book;

    private final ZonedDateTime sentAt;

    private BookRetrievedMessage(final Book book, final ZonedDateTime sentAt) {
        this.book = book;
        this.sentAt = sentAt;
    }

    static BookRetrievedMessage of(Book book) {
        return new BookRetrievedMessage(book, ZonedDateTime.now());
    }

    @Override
    public String toString() {
        return String.format("\"book >>%s<< retrieved\"", book.title().value());
    }
}
