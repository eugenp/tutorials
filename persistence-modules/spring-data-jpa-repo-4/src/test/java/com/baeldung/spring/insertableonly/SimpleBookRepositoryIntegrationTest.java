package com.baeldung.spring.insertableonly;

import static com.baeldung.spring.insertableonly.ConstantHolder.NEW_TITLE;
import static com.baeldung.spring.insertableonly.ConstantHolder.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.spring.insertableonly.simple.SimpleBook;
import com.baeldung.spring.insertableonly.simple.SimpleBook;
import com.baeldung.spring.insertableonly.simple.SimpleBookRepository;
import com.baeldung.spring.insertableonly.simple.SimpleBookService;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
class SimpleBookRepositoryIntegrationTest {

    @Autowired
    private SimpleBookRepository repository;
    @Autowired
    private SimpleBookService service;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void givenDatasourceWhenSaveBookThenBookPersisted() {
        SimpleBook newBook = new SimpleBook(TITLE);
        SimpleBook persistedBook = repository.save(newBook);
        Long id = persistedBook.getId();
        assertThat(id).isNotNull();
        SimpleBook actualBook = getBookById(id);
        assertThat(actualBook.getTitle()).isEqualTo(TITLE);
        assertThat(actualBook.getId()).isEqualTo(id);
    }

    @Test
    void givenDatasourceWhenUpdateBookThenBookIsUpdated() {
        SimpleBook book = new SimpleBook(TITLE);
        SimpleBook persistedBook = repository.save(book);
        Long id = persistedBook.getId();
        persistedBook.setTitle(NEW_TITLE);
        repository.save(persistedBook);
        Optional<SimpleBook> actualBook = repository.findById(id);
        assertTrue(actualBook.isPresent());
        assertThat(actualBook.get().getId()).isEqualTo(id);
        assertThat(actualBook.get().getTitle()).isEqualTo(NEW_TITLE);
    }

    @Test
    void givenDatasourceWhenUpdateBookThenUpdatedIsIgnored() {
        SimpleBook book = new SimpleBook(TITLE);
        SimpleBook persistedBook = service.save(book);
        Long id = persistedBook.getId();
        persistedBook.setTitle(NEW_TITLE);
        service.save(persistedBook);
        Optional<SimpleBook> actualBook = service.findById(id);
        assertTrue(actualBook.isPresent());
        assertThat(actualBook.get().getId()).isEqualTo(id);
        assertThat(actualBook.get().getTitle()).isEqualTo(TITLE);
    }


    private SimpleBook getBookById(long id) {
        Optional<SimpleBook> book = repository.findById(id);
        assertTrue(book.isPresent());
        return book.get();
    }

}
