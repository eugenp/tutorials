package com.baeldung.hexagonalarchitecture.core.service;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import com.baeldung.hexagonalarchitecture.port.BookRepositoryPort;

import com.baeldung.hexagonalarchitecture.port.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookServicePort {

    @Autowired
    private BookRepositoryPort bookRepositoryPort;

    @Override
    public Book getBook(Long id) {
        return bookRepositoryPort.getBook(id);
    }
}
