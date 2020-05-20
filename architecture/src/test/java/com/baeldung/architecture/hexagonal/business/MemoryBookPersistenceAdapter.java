package com.baeldung.architecture.hexagonal.business;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baeldung.architecture.hexagonal.business.bordery.driven.BookPersistencePort;
import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

class MemoryBookPersistenceAdapter implements BookPersistencePort {

    // @formatter:off
    Map<Long,BookDto> memoryDb = Stream.of(
        new BookDto(1L, "Baeldung", "Hexagonal Architecture"),
        new BookDto(2L, "Baeldung", "Clean Architecture")
        ).collect(Collectors.toMap(BookDto::getId, Function.identity()));
    // @formatter:on

    @Override
    public List<BookDto> findByNameContaining(String partOfTheName) {// @formatter:off
        return memoryDb.values().stream()
            .filter(book -> book.getName().contains(partOfTheName))
            .collect(Collectors.toList());
    }// @formatter:on

    @Override
    public BookDto save(BookDto book) {
        Long biggestId = memoryDb.keySet()
            .stream()
            .max(Comparator.naturalOrder())
            .get();

        book.setId(++biggestId);
        memoryDb.put(biggestId, book);
        return book;
    }

    BookDto get(Long id) {
        return memoryDb.get(id);
    }
}
