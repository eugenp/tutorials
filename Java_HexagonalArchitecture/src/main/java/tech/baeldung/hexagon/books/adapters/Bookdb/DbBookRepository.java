package tech.baeldung.hexagon.books.adapters.Bookdb;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.model.BookId;
import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.Content;
import tech.baeldung.hexagon.books.domain.model.Title;
import tech.baeldung.hexagon.books.domain.ports.BookRepository;

import java.util.UUID;

import static tech.baeldung.hexagon.books.adapters.Bookdb.BookDatabaseModel.BookDatabaseModel;
import static tech.baeldung.hexagon.books.adapters.Bookdb.BookDatabaseModel.of;

@Component
class DbBookRepository implements BookRepository {

    @Override
    public Book save(final Author author, final Title title, final Content content) {
        /**
         * Database integration implementation comes here
         */
        final BookDatabaseModel entity = of(author, title, content);
        return entity.toDomain();
    }

    @Override
    public Book get(final BookId id) {
        /**
         * Database integration implementation comes here
         */
        final BookDatabaseModel entity = BookDatabaseModel()
        		.withId(UUID.fromString(id.value()))
                .withAuthorName("Baeldunng Author")
                .withAuthorId("10002")
                .withTitle("Hexagonal Architecture in Java")
                .withContent("HA using springBoot")
                .build();
        return entity.toDomain();
    }
}
