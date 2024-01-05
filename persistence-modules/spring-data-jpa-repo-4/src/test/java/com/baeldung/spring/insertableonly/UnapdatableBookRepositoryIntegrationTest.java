package com.baeldung.spring.insertableonly;

import static com.baeldung.spring.insertableonly.ConstantHolder.NEW_TITLE;
import static com.baeldung.spring.insertableonly.ConstantHolder.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.spring.insertableonly.unpadatable.UnapdatableBook;
import com.baeldung.spring.insertableonly.unpadatable.UnapdatableBook;
import com.baeldung.spring.insertableonly.unpadatable.UnapdatableBookRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Application.class)
class UnapdatableBookRepositoryIntegrationTest {

    @Autowired
    private UnapdatableBookRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }
    @Test
    void givenDatasourceWhenSaveBookThenBookPersisted() {
        UnapdatableBook newBook = new UnapdatableBook(TITLE);
        UnapdatableBook persistedBook = repository.save(newBook);
        Long id = persistedBook.getId();
        assertThat(id).isNotNull();
        UnapdatableBook actualBook = getBookById(id);
        assertThat(actualBook.getTitle()).isEqualTo(TITLE);
        assertThat(actualBook.getId()).isEqualTo(id);
    }

    @Test
    void givenDatasourceWhenUpdateBookTheBookUpdatedIgnored() {
        UnapdatableBook book = new UnapdatableBook(TITLE);
        UnapdatableBook persistedBook = repository.save(book);
        Long id = persistedBook.getId();
        persistedBook.setTitle(NEW_TITLE);
        repository.save(persistedBook);
        Optional<UnapdatableBook> actualBook = repository.findById(id);
        assertTrue(actualBook.isPresent());
        assertThat(actualBook.get().getId()).isEqualTo(id);
        assertThat(actualBook.get().getTitle()).isEqualTo(TITLE);
    }

    private UnapdatableBook getBookById(long id) {
        Optional<UnapdatableBook> book = repository.findById(id);
        assertTrue(book.isPresent());
        return book.get();
    }


}
