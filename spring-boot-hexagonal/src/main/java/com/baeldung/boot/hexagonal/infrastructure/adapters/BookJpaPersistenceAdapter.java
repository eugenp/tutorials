package com.baeldung.boot.hexagonal.infrastructure.adapters;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import com.baeldung.boot.hexagonal.infrastructure.BookJpaRepository;
import com.baeldung.boot.hexagonal.infrastructure.entities.BookEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Primary
public class BookJpaPersistenceAdapter implements BookPersistence {

    private final BookJpaRepository repository;
    private final ModelMapper modelMapper;

    @Override
    public void addBook(Book book) {
        log.info("Adding the book to database.. ");
        BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
        repository.save(bookEntity);
    }

    @Override
    public void updateBook(Book book) {
        log.info("Adding the book in database.. ");
        BookEntity bookEntity = modelMapper.map(book, BookEntity.class);
        repository.save(bookEntity);
    }

    @Override
    public Book getBook(long id) {
        return modelMapper.map(repository.getById(id), Book.class);
    }

    @Override
    public List<Book> getBooks() {
        return modelMapper.map(repository.findAll(), new TypeToken<List<Book>>() {
        }.getType());
    }

    @Override
    public void deleteBook(long id) {
        repository.deleteById(id);
    }
}
