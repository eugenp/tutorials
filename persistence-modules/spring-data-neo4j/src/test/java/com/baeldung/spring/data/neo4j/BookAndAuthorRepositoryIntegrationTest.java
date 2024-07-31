package com.baeldung.spring.data.neo4j;

import com.baeldung.spring.data.neo4j.domain.Book;
import com.baeldung.spring.data.neo4j.repository.AuthorRepository;
import com.baeldung.spring.data.neo4j.repository.BookRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.neo4j.harness.Neo4j;
import org.neo4j.harness.Neo4jBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.neo4j.DataNeo4jTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import java.util.List;

@DataNeo4jTest
public class BookAndAuthorRepositoryIntegrationTest {

    private static Neo4j newServer;

    @BeforeAll
    static void initializeNeo4j() {
        newServer = Neo4jBuilders.newInProcessBuilder()
          .withDisabledServer()
          .withFixture("CREATE (b:Book {isbn: '978-0547928210', name: 'The Fellowship of the Ring', year: 1954})" +
            "-[:WRITTEN_BY]->(a:Author {id: 1, name: 'J. R. R. Tolkien'})" +
            "CREATE (b2:Book {isbn: '978-0547928203', name: 'The Two Towers', year: 1956})-[:WRITTEN_BY]->(a)")
        .build();
    }

    @AfterAll
    static void stopNeo4j() {
        newServer.close();
    }

    @DynamicPropertySource
    static void neo4jProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.neo4j.uri", newServer::boltURI);
        registry.add("spring.neo4j.authentication.username", () -> "neo4j");
        registry.add("spring.neo4j.authentication.password", () -> "null");
    }

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void givenBookExists_whenFindOneByTitle_thenBookIsReturned() {
        Book book = bookRepository.findOneByTitle("The Two Towers");
        Assertions.assertEquals("978-0547928203", book.getIsbn());
    }

    @Test
    void givenOneBookExistsForYear_whenFindAllByYear_thenOneBookIsReturned() {
        List<Book> books = bookRepository.findAllByYear(1954);
        Assertions.assertEquals(1, books.size());
    }

    @Test
    void givenOneBookExistsAfterYear_whenFindBooksAfterYear_thenOneBookIsReturned() {
        List<Book> books = authorRepository.findBooksAfterYear("J. R. R. Tolkien", 1955);
        Assertions.assertEquals(1, books.size());
    }
}