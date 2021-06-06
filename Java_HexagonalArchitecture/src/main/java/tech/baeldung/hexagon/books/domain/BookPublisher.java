package tech.baeldung.hexagon.books.domain;

import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.ports.BookMessageSender;
import tech.baeldung.hexagon.books.domain.ports.AuthorNotifier;


import java.util.List;

public class BookPublisher {
    private final BookMessageSender messageSender;
    private final List<AuthorNotifier> bookAuthorNotifiers;

    public BookPublisher(final BookMessageSender messageSender,
                            final List<AuthorNotifier> bookAuthorNotifiers) {
        this.messageSender = messageSender;
        this.bookAuthorNotifiers = bookAuthorNotifiers;
    }

    public void publishCreationOf(final Book book) {
        messageSender.sendMessageForCreated(book);
        bookAuthorNotifiers.forEach(bookAuthorNotifier -> bookAuthorNotifier.notifyAboutCreationOf(book));
    }

    public void publishRetrievalOf(final Book book) {
        messageSender.sendMessageForCreated(book);
    }
}
