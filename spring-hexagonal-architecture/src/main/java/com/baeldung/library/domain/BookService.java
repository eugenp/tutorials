package com.baeldung.library.domain;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.library.inbound.port.IBookService;
import com.baeldung.library.outbound.port.IBookPersistence;

@Transactional
@Service("BookService")
public class BookService implements IBookService {

    @Autowired
    private IBookPersistence bookRepository;

    public void publishBook(Book book) {
        bookRepository.publishBook(book);
    }
}
