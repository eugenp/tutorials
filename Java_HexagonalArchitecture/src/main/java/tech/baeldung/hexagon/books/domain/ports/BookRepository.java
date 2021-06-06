package tech.baeldung.hexagon.books.domain.ports;

import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.model.BookId;
import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.Content;
import tech.baeldung.hexagon.books.domain.model.Title;

public interface BookRepository {

    Book save(Author author, Title title, Content content);

    Book get(BookId id);
}
