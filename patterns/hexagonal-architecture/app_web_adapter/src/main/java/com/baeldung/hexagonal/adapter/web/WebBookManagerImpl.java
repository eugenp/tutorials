package com.baeldung.hexagonal.adapter.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.exception.BookNotFoundException;
import com.baeldung.hexagonal.service.BookManager;

@RestController("bookmanager")
public class WebBookManagerImpl implements WebBookManager {

    private static Logger logger = LoggerFactory.getLogger(WebBookManagerImpl.class);
    
    @Autowired
    BookManager bookManager;

    @Override
    @RequestMapping(path = "add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book addBook(@RequestBody Book book) {
        Book registeredBook = bookManager.addBook(book);
        return registeredBook;
    }

    @Override
    @RequestMapping(path = "update", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book updateBook(@RequestBody Book book) {
        Book updatedBook = null;
        try {
            updatedBook = bookManager.updateBook(book);
        } catch (BookNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return updatedBook;
    }

    @Override
    @RequestMapping(path = "delete/{isbn}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book deleteBook(@PathVariable("isbn") String isbn) {
        Book deletedBook = null;
        try {
            deletedBook = bookManager.deleteBook(isbn);
        } catch (BookNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return deletedBook;
    }

    @Override
    @RequestMapping(path = "list", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> fetchAllBooks() {
        List<Book> bookList = bookManager.fetchAllBooks();
        return bookList;
    }

    @Override
    @RequestMapping(path = "search/{isbn}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Book findBookByIsbn(@PathVariable("isbn") String isbn) {
        Book searchedBook = null;
        try {
            searchedBook = bookManager.findBookByIsbn(isbn);
        } catch (BookNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return searchedBook;
    }

}
