package com.baeldung.spring.insertableonly;

import static com.baeldung.spring.insertableonly.ConstantHolder.ID;
import static com.baeldung.spring.insertableonly.ConstantHolder.NEW_TITLE;
import static com.baeldung.spring.insertableonly.ConstantHolder.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.spring.insertableonly.query.CustomQueryBook;
import com.baeldung.spring.insertableonly.query.CustomQueryBookRepository;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest(classes = Application.class, properties = {
  "spring.datasource.url=jdbc:h2:mem:~/test;DATABASE_TO_UPPER=false"
})
class CustomQueryBookRepositoryIntegrationTest {

    @Autowired
    private CustomQueryBookRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void givenDatasourceWhenSaveBookThenBookPersisted() {
        CustomQueryBook book = new CustomQueryBook(ID, TITLE);
        repository.persist(book);
        CustomQueryBook persistedBook = getBookById(ID);
        assertThat(persistedBook.getId()).isEqualTo(ID);
        assertThat(persistedBook.getTitle()).isEqualTo(TITLE);
    }

    @Test
    void givenDatasourceWhenUpdateThenThrowException() {
        CustomQueryBook book = new CustomQueryBook(ID, TITLE);
        repository.persist(book);
        CustomQueryBook persistedBook = getBookById(ID);
        persistedBook.setTitle(NEW_TITLE);
        assertThatExceptionOfType(DataIntegrityViolationException.class)
          .isThrownBy(() -> repository.persist(persistedBook));
    }

    private CustomQueryBook getBookById(long id) {
        Optional<CustomQueryBook> book = repository.findById(id);
        assertTrue(book.isPresent());
        return book.get();
    }

}
