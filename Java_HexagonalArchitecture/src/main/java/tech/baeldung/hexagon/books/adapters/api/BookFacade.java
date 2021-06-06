package tech.baeldung.hexagon.books.adapters.api;

import org.springframework.stereotype.Component;
import tech.baeldung.hexagon.books.domain.model.Book;
import tech.baeldung.hexagon.books.domain.model.BookId;
import tech.baeldung.hexagon.books.domain.ports.BookService;

@Component
class BookFacade {

    private final BookService bookService;

    BookFacade(final BookService bookService) {
        this.bookService = bookService;
    }

    BookResponse get(final String bookId) {
        final Book book = bookService.get(BookId.of(bookId));
        return BookResponse.of(book);
    }

    BookIdResponse create(final BookRequest bookRequest) {
        final BookId bookId = bookService.create(bookRequest.authorId(), bookRequest.title(), bookRequest.content());
        return BookIdResponse.of(bookId);
    }
}
