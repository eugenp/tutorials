package com.baeldung.hexagonalapp.application;

import com.baeldung.hexagonalapp.core.Book;
import org.springframework.stereotype.Component;

@Component
class BorrowBookHandler implements BorrowBookUseCase {

    private final BookRepositoryPort bookRepository;

    BorrowBookHandler(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto handle(BorrowBookCommand command) {
        BookDto bookDto = bookRepository.find(command.getBookId()).orElseThrow(BookNotFound::new);
        Book book = new Book(bookDto.getId(), bookDto.getName(), bookDto.getAuthor(), bookDto.getBorrower());

        book.lendOutTo(command.getBorrower());

        return bookRepository.save(
                new BookDto(book.getId(), book.getName(), book.getAuthor(), book.getBorrower()));
    }
}
