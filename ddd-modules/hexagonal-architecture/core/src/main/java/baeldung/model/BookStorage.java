package baeldung.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookStorage {

    Book save(Book book);
    Optional<Book> findById(UUID id);
    List<Book> findByTitleOrAuthor(String query);

}
