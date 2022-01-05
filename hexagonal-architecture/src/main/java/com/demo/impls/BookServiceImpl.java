package com.demo.impls;

import com.demo.models.Book;
import com.demo.repositories.BookRepository;
import com.demo.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(UUID uuid) {
        return bookRepository.findById(uuid);
    }

    @Override
    public UUID createBook(Book newBook) {
        return bookRepository.save(newBook).getId();
    }

    @Override
    public void deleteBookById(UUID id) {
        bookRepository.deleteById(id);
    }
}
