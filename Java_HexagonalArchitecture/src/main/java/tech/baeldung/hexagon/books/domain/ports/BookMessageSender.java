package tech.baeldung.hexagon.books.domain.ports;

import tech.baeldung.hexagon.books.domain.model.Book;

public interface BookMessageSender {

    void sendMessageForCreated(Book book);

    void sendMessageForRetrieved(Book book);

}
