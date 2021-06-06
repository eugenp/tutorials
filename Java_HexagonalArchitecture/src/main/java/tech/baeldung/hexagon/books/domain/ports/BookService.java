package tech.baeldung.hexagon.books.domain.ports;

import tech.baeldung.hexagon.books.domain.ports.BookRepository;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.model.BookId;
import tech.baeldung.hexagon.books.domain.BookPublisher;
import tech.baeldung.hexagon.books.domain.model.Author;
import tech.baeldung.hexagon.books.domain.model.AuthorId;
import tech.baeldung.hexagon.books.domain.model.Content;
import tech.baeldung.hexagon.books.domain.model.Title;

public class BookService{

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final BookPublisher eventPublisher;


    public BookService(final BookRepository bookRepository,
                          final AuthorRepository authorRepository,
                          final BookPublisher eventPublisher) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.eventPublisher = eventPublisher;
    }

    public BookId create(final AuthorId authorId, final Title title, final Content content) {
        final Author author = authorRepository.get(authorId);
        final Book book = bookRepository.save(author, title, content);

        book.validateEligibilityForPublication();

        eventPublisher.publishCreationOf(book);
        return book.id();
    }

    public Book get(final BookId id) {
        final Book book = bookRepository.get(id);
        eventPublisher.publishRetrievalOf(book);
        return book;
    }
}
