package com.baeldung.mockito.additionalanswers;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getByBookId(Long id) {
        return bookRepository.getByBookId(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public Book selectRandomBook(Book book1, Book book2, Book book3) {
        return bookRepository.selectRandomBook(book1, book2, book3);
    }
}

