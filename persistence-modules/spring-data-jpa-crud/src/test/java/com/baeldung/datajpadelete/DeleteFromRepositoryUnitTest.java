package com.baeldung.datajpadelete;

import com.baeldung.Application;
import com.baeldung.datajpadelete.entity.Book;
import com.baeldung.datajpadelete.repository.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
public class DeleteFromRepositoryUnitTest {

    @Autowired
    private BookRepository repository;

    Book book1;
    Book book2;

    @Before
    public void setup() {
        book1 = new Book("The Hobbit");
        book2 = new Book("All Quiet on the Western Front");

        repository.saveAll(Arrays.asList(book1, book2));
    }

    @After
    public void teardown() {
        repository.deleteAll();
    }

    @Test
    public void whenDeleteByIdFromRepository_thenDeletingShouldBeSuccessful() {
        repository.deleteById(book1.getId());

        assertThat(repository.count()).isEqualTo(1);
    }

    @Test
    public void whenDeleteAllFromRepository_thenRepositoryShouldBeEmpty() {
        repository.deleteAll();

        assertThat(repository.count()).isEqualTo(0);
    }

    @Test
    @Transactional
    public void whenDeleteFromDerivedQuery_thenDeletingShouldBeSuccessful() {
        long deletedRecords = repository.deleteByTitle("The Hobbit");

        assertThat(deletedRecords).isEqualTo(1);
    }

    @Test
    @Transactional
    public void whenDeleteFromCustomQuery_thenDeletingShouldBeSuccessful() {
        repository.deleteBooks("The Hobbit");

        assertThat(repository.count()).isEqualTo(1);
    }

}