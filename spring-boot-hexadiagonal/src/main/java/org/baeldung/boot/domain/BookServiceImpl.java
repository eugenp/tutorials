package org.baeldung.boot.domain;

import java.util.List;

public class BookServiceImpl implements BookService{
    
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void storeBook(Book book) {
        bookRepository.storeBook(book);
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.getBooksByAuthor(author);
    }
}
