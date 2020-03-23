package com.baeldung.library.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.baeldung.library.inbound.port.IBookService;
import com.baeldung.library.outbound.port.IBookPersistence;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BookServiceUnitTest {

    @Autowired
    private IBookService service;

    @MockBean
    private IBookPersistence bookPersistenceSpy;

    @Test
    public final void whenBookIsPublished_thenAdpaterIsCalled() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("title");
        book.setAuthor("me");
        service.publishBook(book);
        Mockito.verify(bookPersistenceSpy).publishBook(book);
    }

}
