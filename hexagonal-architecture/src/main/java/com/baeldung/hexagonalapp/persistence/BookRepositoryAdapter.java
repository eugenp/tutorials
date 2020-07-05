package com.baeldung.hexagonalapp.persistence;

import com.baeldung.hexagonalapp.application.BookDto;
import com.baeldung.hexagonalapp.application.BookRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
class BookRepositoryAdapter implements BookRepositoryPort {

    private final JpaBookRepository jpaBookRepository;

    BookRepositoryAdapter(JpaBookRepository jpaBookRepository) {
        this.jpaBookRepository = jpaBookRepository;
    }

    @Override
    public BookDto save(BookDto bookDto) {
        BookEntity book = new BookEntity(bookDto);
        book = jpaBookRepository.save(book);
        return book.toDto();
    }

    @Override
    public Optional<BookDto> find(Long id) {
        return jpaBookRepository.findById(id)
          .flatMap(bookEntity -> Optional.of(bookEntity.toDto()));
    }

    @Override
    public List<BookDto> findAll() {
        return jpaBookRepository.findAll()
          .stream()
          .map(BookEntity::toDto)
          .collect(Collectors.toList());
    }
}
