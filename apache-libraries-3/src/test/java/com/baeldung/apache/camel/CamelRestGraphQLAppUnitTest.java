package com.baeldung.apache.camel;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

public class CamelRestGraphQLAppUnitTest {

    private static CamelContext context;
    private static ProducerTemplate template;

    @BeforeAll
    public static void setup() throws Exception {
        context = new DefaultCamelContext();
        context.getRegistry().bind("graphqlSchemaLoader", new CustomSchemaLoader());
        context.addRoutes(new BookRoute());
        context.start();
        template = context.createProducerTemplate();

        Thread.sleep(1000); // Give the server time to start
    }

    @AfterAll
    public static void tearDown() throws Exception {
        if (template != null) {
            template.stop();
        }
        if (context != null) {
            context.stop();
        }
    }

    @Test
    void whenCallingRestGetAllBooks_thenShouldReturnBookList() {
        String response = template.requestBody("jetty:http://localhost:8080/api/books", null, String.class);

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Effective Java"));
        assertTrue(response.contains("Baeldung"));
        assertTrue(response.contains("Joshua"));
    }

    @Test
    void whenCallingRestGetBookById_thenShouldReturnSpecificBook() {
        String response = template.requestBody("jetty:http://localhost:8080/api/books/1", null, String.class);

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Baeldung"));
        assertFalse(response.contains("Effective Java"));
    }

    @Test
    void whenPostingNewBook_thenShouldAddToCollection() {
        String bookJson = "{\"id\":\"3\",\"title\":\"Camel in Action\",\"author\":\"Claus Ibsen\"}";

        String postResponse = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/api/books",
            bookJson,
            "Content-Type",
            "application/json",
            String.class
        );

        String getResponse = template.requestBody("jetty:http://localhost:8080/api/books", null, String.class);

        assertNotNull(postResponse);
        assertTrue(getResponse.contains("Camel in Action"));
        assertTrue(getResponse.contains("Claus Ibsen"));
    }

    @Test
    void whenCallingBooksQuery_thenShouldReturnAllBooks() {
        String query = "{\"query\":\"{ books { id title author } }\"}";

        String response = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/graphql",
            query,
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("books"));
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Effective Java"));
        assertTrue(response.contains("Baeldung"));
        assertTrue(response.contains("Joshua"));
    }

    @Test
    void whenCallingBookByIdQuery_thenShouldReturnSpecificBook() {
        String query = "{\"query\":\"{ bookById(id: \\\"1\\\") { title author } }\"}";

        String response = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/graphql",
            query,
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Baeldung"));
        assertFalse(response.contains("Effective Java"));
    }

    @Test
    void whenAddingBookViaMutation_thenShouldPersist() {
        String mutation = "{\"query\":\"mutation { " +
            "addBook(id: \\\"3\\\", title: \\\"Camel in Action\\\", author: \\\"Claus Ibsen\\\") { id title }" +
            "}\"}";

        String mutationResponse = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/graphql",
            mutation,
            "Content-Type",
            "application/json",
            String.class
        );

        String queryResponse = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/graphql",
            "{\"query\":\"{ books { title } }\"}",
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(mutationResponse);
        assertTrue(mutationResponse.contains("Camel in Action"));
        assertTrue(queryResponse.contains("Camel in Action"));
    }

    @Test
    void whenAddingInvalidBook_thenShouldReturnError() {
        String mutation = "{\"query\":\"mutation { " +
            "addBook(id: \\\"4\\\", title: \\\"\\\", author: \\\"Test\\\") { id title }" +
            "}\"}";

        String response = template.requestBodyAndHeader(
            "jetty:http://localhost:8080/graphql",
            mutation,
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("errors"));
        assertTrue(response.contains("Title cannot be empty"));
    }
}