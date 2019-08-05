package org.baeldung.hexa.domain;

import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService{
    
    private BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void storeBook(Book book) {
        List<Book> booksByAuthor = bookRepository.getBooksByAuthor(book.getAuthor()).stream().collect(Collectors.toList());
        if (!booksByAuthor.contains(book)) {
            bookRepository.storeBook(book);
        }
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.getBooksByAuthor(author);
    }

    @Override
    public int getNumberOfBooks() {
        return bookRepository.getAllBooks().size();
    }

    @Override
    public int getNumberOfAuthors() {
        List<Book> allBooks = bookRepository.getAllBooks();
        return allBooks.stream().map(Book::getAuthor).collect(Collectors.toSet()).size();
    }
    
}
