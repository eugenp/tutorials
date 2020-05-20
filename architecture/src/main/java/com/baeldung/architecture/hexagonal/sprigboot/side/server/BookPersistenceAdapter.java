package com.baeldung.architecture.hexagonal.sprigboot.side.server;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baeldung.architecture.hexagonal.business.bordery.driven.BookPersistencePort;
import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

@Service
class BookPersistenceAdapter implements BookPersistencePort {

    private final BookJpaRepository bookRepository;

    BookPersistenceAdapter(BookJpaRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> findByNameContaining(String partOfTheName) {
        return bookRepository.findByNameContaining(partOfTheName)
            .stream()
            .map(this::convertToBookDto)
            .collect(Collectors.toList());
    }

    @Override
    public BookDto save(BookDto bootDto) {
        BookEntity savedBook = bookRepository.save(convertToBook(bootDto));
        return convertToBookDto(savedBook);
    }

    private BookDto convertToBookDto(BookEntity book) {
        return new BookDto(book.getId(),  book.getAuthor(), book.getName());
    }
 
    private BookEntity convertToBook(BookDto book) {
        return new BookEntity(book.getId(), book.getAuthor(),book.getName());
    }
}
