package com.baeldung.hexagonal.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.Book;
import com.baeldung.hexagonal.domain.Inventory;
import com.baeldung.hexagonal.exception.BookNotFoundException;
import com.baeldung.hexagonal.repository.BookRepository;
import com.baeldung.hexagonal.repository.InventoryRepository;

@Service(value = "BookManagerImpl")
public class BookManagerImpl implements BookManager {

    private static Logger logger = LoggerFactory.getLogger(BookManagerImpl.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public Book addBook(Book book) {
        logger.info("Add Book: " + book);
        Inventory inventory = updateInventoryIfPresent(book);
        if (inventory == null) {
            inventory = addToInventory(book);
        }
        return inventory.getBook();
    }

    private Inventory addToInventory(Book registeredBook) {
        Inventory inventory = new Inventory(1, 1, registeredBook);
        inventoryRepository.save(inventory);
        return inventory;
    }

    private Inventory updateInventoryIfPresent(Book book) {
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByBookIsbn(book.getIsbn());
        Inventory inventory = null;
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
            inventory.setQuantity(inventory.getQuantity() + 1);
            inventory.setAvailable(inventory.getAvailable() + 1);
            inventoryRepository.save(inventory);
        }
        return inventory;
    }

    public Book findBookByIsbn(String isbn) throws BookNotFoundException {
        logger.info("Find book with isbn: " + isbn);
        Optional<Book> optionalBook = bookRepository.findBookByIsbn(isbn);
        if (optionalBook.isPresent()) {
            return optionalBook.get();
        } else {
            logger.error("Can't find book with isbn: " + isbn);
            throw new BookNotFoundException("The book with isbn " + isbn + " is not found");
        }
    }

    public Book deleteBook(String isbn) throws BookNotFoundException {
        logger.info("Delete book with isbn: " + isbn);
        Optional<Inventory> optionalInventory = inventoryRepository.findInventoryByBookIsbn(isbn);
        Inventory inventory = null;
        if (optionalInventory.isPresent()) {
            inventory = optionalInventory.get();
            inventoryRepository.delete(inventory);
        } else {
            logger.error("Can't find book with isbn: " + isbn);
            throw new BookNotFoundException("The book with isbn " + isbn + " is not found");
        }
        return inventory.getBook();
    }

    public List<Book> fetchAllBooks() {
        logger.info("Fetch all books");
        return bookRepository.findAll();
    }

    public Book updateBook(Book book) throws BookNotFoundException {
        logger.info("Update book: " + book);
        Book updatedBook = null;
        if (bookRepository.findBookByIsbn(book.getIsbn())
            .isPresent()) {
            updatedBook = bookRepository.save(book);
        } else {
            logger.error("Can't find book with isbn: " + book.getIsbn());
            throw new BookNotFoundException("The book with isbn " + book.getIsbn() + " is not found");
        }
        return updatedBook;
    }

}
