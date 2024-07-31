package com.baeldung.spring.insertableonly;

import static com.baeldung.spring.insertableonly.ConstantHolder.NEW_TITLE;
import static com.baeldung.spring.insertableonly.ConstantHolder.TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.baeldung.spring.insertableonly.persistable.PersistableBook;
import com.baeldung.spring.insertableonly.persistable.PersistableBookRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;

@SpringBootTest(classes = Application.class)
class PersistableBookRepositoryIntegrationTest {

    @Autowired
    private PersistableBookRepository repository;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    void givenDatasourceWhenSaveBookThenBookPersisted() {
        PersistableBook newBook = new PersistableBook(TITLE);
        PersistableBook persistedBook = repository.save(newBook);
        assertThat(persistedBook.getId()).isNotNull();
        Long id = persistedBook.getId();
        PersistableBook actualBook = getBookById(id);
        assertThat(actualBook.getTitle()).isEqualTo(TITLE);
        assertThat(actualBook.getId()).isEqualTo(id);
    }

    @Test
    void givenDatasourceWhenUpdateThenThrowException() {
        PersistableBook book = new PersistableBook(TITLE);
        PersistableBook persistedBook = repository.save(book);
        persistedBook.setTitle(NEW_TITLE);
        assertThatExceptionOfType(InvalidDataAccessApiUsageException.class)
          .isThrownBy(() -> repository.save(persistedBook));
    }

    private PersistableBook getBookById(long id) {
        Optional<PersistableBook> book = repository.findById(id);
        assertTrue(book.isPresent());
        return book.get();
    }

}
