package com.baeldung.architecture.hexagonal.personal.library.application.adapter.business.service;

import com.baeldung.architecture.hexagonal.personal.library.core.domain.Book;
import com.baeldung.architecture.hexagonal.personal.library.core.port.business.service.GetBookService;
import com.baeldung.architecture.hexagonal.personal.library.core.port.infrastructure.BookRepository;

import java.util.List;

public class GetBookServiceImpl implements GetBookService {

    private final BookRepository bookRepository;

    public GetBookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll(){
        return bookRepository.getAll();
    }

}
