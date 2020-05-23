package baeldung.repository;

import baeldung.model.Book;
import baeldung.model.BookStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class JpaBookStorage implements BookStorage {

    private final BookEntityRepository bookEntityRepository;

    @Autowired
    public JpaBookStorage(BookEntityRepository bookEntityRepository) {
        this.bookEntityRepository = bookEntityRepository;
    }

    @Override
    public Book save(Book book) {
        return bookEntityRepository.save(new BookEntity(book)).getBook();
    }

    @Override
    public Optional<Book> findById(UUID id) {
        return bookEntityRepository.findById(id).map(BookEntity::getBook);
    }

    @Override
    public List<Book> findByTitleOrAuthor(String query) {
        return bookEntityRepository.findByTitleOrAuthor(query, query).stream().map(BookEntity::getBook).collect(toList());
    }

}
