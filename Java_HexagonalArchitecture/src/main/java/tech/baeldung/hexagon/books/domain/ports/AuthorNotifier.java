package tech.baeldung.hexagon.books.domain.ports;

import tech.baeldung.hexagon.books.domain.model.Book;

public interface AuthorNotifier {

    void notifyAboutCreationOf(Book book);

}
