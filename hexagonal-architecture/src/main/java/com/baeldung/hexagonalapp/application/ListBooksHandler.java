package com.baeldung.hexagonalapp.application;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
class ListBooksHandler implements ListBooksUseCase {

    private final BookRepositoryPort bookRepository;

    ListBooksHandler(BookRepositoryPort bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<BookDto> listAll() {
        return bookRepository.findAll();
    }
}
