package com.baeldung.criteriaquery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.baeldung.criteriaquery.Book.hasAuthor;
import static com.baeldung.criteriaquery.Book.titleContains;
import static org.junit.Assert.assertEquals;
import static org.springframework.data.jpa.domain.Specification.where;

@DataJpaTest(showSql = false)
@RunWith(SpringRunner.class)
public class CriteriaQueryIntegrationTest {

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private BookRepository repository;

    @Before
    public void before() {
        entityManager.persist(new Book("title1", "author1"));
        entityManager.persist(new Book("title2", "author2"));
    }

    @Test
    public void givenAuthorAndTextInTitle_whenFindWithSpecification_ThenFound() {
        List<Book> books = repository.findAll(where(hasAuthor("author1")).and(titleContains("1")));
        assertEquals(1, books.size());
        assertEquals("author1", books.get(0).getAuthor());
        assertEquals("title1", books.get(0).getTitle());
    }

    @Test
    public void givenAuthorAndTextInTitle_whenFindWithCriteriaQuery_ThenFound() {
        List<Book> books = repository.findBooksByAuthorNameAndTitle("author2", "2");
        assertEquals(1, books.size());
        assertEquals("author2", books.get(0).getAuthor());
        assertEquals("title2", books.get(0).getTitle());
    }

}
