package com.baeldung.mockito.additionalanswers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookBusinessLogic bookBusinessLogic;

    @Autowired
    public BookService(BookRepository bookRepository, BookBusinessLogic bookBusinessLogic) {
        this.bookRepository = bookRepository;
        this.bookBusinessLogic = bookBusinessLogic;
    }

    public Book getByBookId(Long id) {
        return bookRepository.getByBookId(id);
    }

    public List<Book> getAllByAuthor(String author) {
        return bookRepository.getAllByAuthor(author);
    }

    public Book getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    public Book getBookByTitleAnAndAuthorAndNumberOfPages(String title, String author, int numOfPages) {
        return bookRepository.getBookByTitleAnAndAuthorAndNumberOfPages(title, author, numOfPages);
    }

    public Book getBookByTitleAndAuthor(String title, String author){
        return bookRepository.getBookByTitleAndAuthor(title, author);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

   public Book checkifEquals(Book book1, Book book2, Book book3){
        return  bookBusinessLogic.checkIfEquals(book1, book2, book3);
    }

}
