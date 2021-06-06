package tech.baeldung.hexagon.books.adapters.messagebroker;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.ports.BookMessageSender;

@Component
class MessageBrokerBookMessageSender implements BookMessageSender {

    @Override
    public void sendMessageForCreated(final Book book) {
        /**
         * message broker integration implementation comes here
         */
        BookCreatedMessage.of(book);
    }

    @Override
    public void sendMessageForRetrieved(final Book book) {
        /**
         * message broker integration implementation comes here
         */
        BookRetrievedMessage.of(book);
    }
}
