package services.implementation;

import models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import repository.BookRepository;
import services.BookService;

import java.util.List;

public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void saveBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public List<Book> getBooks() {
        return bookRepository.findAll();
    }
}
