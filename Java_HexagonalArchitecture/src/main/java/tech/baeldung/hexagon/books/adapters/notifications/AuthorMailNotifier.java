package tech.baeldung.hexagon.books.adapters.notifications;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.ports.AuthorNotifier;

@Component
class AuthorMailNotifier implements AuthorNotifier {

    @Override
    public void notifyAboutCreationOf(final Book book) {
        /**
         * Mail system integration implementation comes here
         */
        BookMailModel.of(book);
    }
}
