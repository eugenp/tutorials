package tech.baeldung.hexagon.books.adapters.notifications;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.ports.AuthorNotifier;

@Component
class AuthorSmsNotifier implements AuthorNotifier {

    @Override
    public void notifyAboutCreationOf(final Book book) {
        /**
         * SMS system integration implementation comes here
         */
        BookSmsModel.of(book);
    }
}
