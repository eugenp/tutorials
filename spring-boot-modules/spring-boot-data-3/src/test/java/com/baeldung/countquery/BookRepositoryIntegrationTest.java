package com.baeldung.countquery;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.baeldung.criteriabuilder.Book;
import com.baeldung.criteriabuilder.BookRepositoryImpl;

@DataJpaTest
public class BookRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepositoryImpl bookRepository;

    @Test
    public void givenBookDataAdded_whenCountAllBooks_thenReturnsCount() {

        entityManager.persist(new Book("Java Book 1", "Author 1", 1990, "Fiction"));
        entityManager.persist(new Book("Java Book 2", "Author 2", 1996, "Fiction"));

        long count = bookRepository.countAllBooks();

        assertEquals(2, count);
    }

    @Test
    public void givenBookDataAdded_whenCountBooksByTitle_thenReturnsCount() {
        entityManager.persist(new Book("Java Book 1", "Author 1", 2024, "Fiction"));
        entityManager.persist(new Book("Java Book 2", "Author 2", 2000, "Fiction"));
        entityManager.persist(new Book("Spring Book", "Author 3", 2010, "Fiction"));

        long count = bookRepository.countBooksByTitle("Java");

        assertEquals(2, count);
    }

    @Test
    void givenBookDataAdded_whenCountBooksByAuthor_thenReturnsCount() {
        entityManager.persist(new Book("Java Book 1", "Author 1", 1967, "Non Fiction"));
        entityManager.persist(new Book("Java Book 2", "Author 1", 1999, "Non Fiction"));
        entityManager.persist(new Book("Spring Book", "Author 2", 2007, "Non Fiction"));

        long count = bookRepository.countBooksByAuthor("Author 1");

        assertEquals(2, count);
    }

    @Test
    public void givenBookDataAdded_testCountBooksByTitleAndAuthor_thenReturnsCount() {
        entityManager.persist(new Book("Java Book 1", "Author 1", 2010, "Non Fiction"));
        entityManager.persist(new Book("Java Book 2", "Author 2", 2020, "Fiction"));
        entityManager.persist(new Book("Spring Book", "Author 1", 2021, "Drama"));

        long count = bookRepository.countBooksByTitleAndAuthor("Java", "Author 1");

        assertEquals(1, count);
    }

    @Test
    public void givenBookDataAdded_testCountBooksByAuthorOrYear_thenReturnsCount() {
        entityManager.persist(new Book("Java Book", "Author 1", 2000, "Non Fiction"));
        entityManager.persist(new Book("Python Book", "Author 2", 2000, "Fiction"));
        entityManager.persist(new Book("Spring Book", "Author 3", 2020, "Non Fiction"));

        long count = bookRepository.countBooksByAuthorOrYear("Author 1", 2000);

        assertEquals(3, count);
    }

    @Test
    public void givenBookDataAdded_whenCountBooksByTitleOrYearAndAuthor_thenReturnsCount() {
        entityManager.persist(new Book("Java Book", "buzz1", 2000, "Non Fiction"));
        entityManager.persist(new Book("Python Book", "buzz1", 2001, "Fiction"));
        entityManager.persist(new Book("Spring Book", "Foo", 2020, "Non Fiction"));

        long count = bookRepository.countBooksByTitleOrYearAndAuthor("buzz1", 2000, "Spring");

        assertEquals(2, count);
    }
}
