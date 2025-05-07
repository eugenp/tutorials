package com.baeldung.apache.camel;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.impl.DefaultCamelContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CamelRestGraphQLAppUnitTest {

    private static CamelContext context;
    private static ProducerTemplate template;

    @BeforeAll
    public static void setup() throws Exception {
        context = new DefaultCamelContext();
        context.addRoutes(new BookRoute());
        context.start();
        template = context.createProducerTemplate();
        Thread.sleep(2000);
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
    void whenCallingRestGetAllBooks_thenReturnBookList() {
        String response = template.requestBodyAndHeader(
            "http://localhost:8088/api/books",
            null,
            Exchange.CONTENT_TYPE,
            "application/json",
            String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Effective Java"));
        assertTrue(response.contains("Alvin"));
        assertTrue(response.contains("Joshua"));
    }

    @Test
    void whenCallingRestGetBookById_thenReturnSpecificBook() {
        String response = template.requestBody("http://localhost:8088/api/books/1", null, String.class);

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Alvin"));
        assertFalse(response.contains("Effective Java"));
    }

    @Test
    void whenPostingNewBook_thenAddToCollection() {
        String bookJson = "{\"id\":\"3\",\"title\":\"Camel in Action\",\"author\":\"Claus Ibsen\"}";

        String postResponse = template.requestBodyAndHeader(
            "http://localhost:8088/api/books",
            bookJson,
            "Content-Type",
            "application/json",
            String.class
        );

        String getResponse = template.requestBody("http://localhost:8088/api/books", null, String.class);

        assertNotNull(postResponse);
        assertTrue(getResponse.contains("Camel in Action"));
        assertTrue(getResponse.contains("Claus Ibsen"));
    }

    @Test
    void whenCallingBooksQuery_thenReturnAllBooks() {
        String query = """
        {
            "query": "{ books { id title author } }"
        }""";

        String response = template.requestBodyAndHeader(
            "http://localhost:8088/graphql",
            query,
            Exchange.CONTENT_TYPE,
            "application/json",
            String.class
        );
        assertNotNull(response);

        assertTrue(response.contains("books"));
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Effective Java"));
        assertTrue(response.contains("Alvin"));
        assertTrue(response.contains("Joshua"));
    }

    @Test
    void whenCallingBookByIdQuery_thenReturnSpecificBook() {
        String query = "{\"query\":\"{ bookById(id: \\\"1\\\") { title author } }\"}";

        String response = template.requestBodyAndHeader(
            "http://localhost:8088/graphql",
            query,
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(response);
        assertTrue(response.contains("Clean Code"));
        assertTrue(response.contains("Alvin"));
        assertFalse(response.contains("Effective Java"));
    }

    @Test
    void whenAddingBookViaMutation_thenPersist() {
        String bookJson = "{ \"id\": \"3\", \"title\": \"Camel in Action\", \"author\": \"Claus Ibsen\" }";

        String postResponse = template.requestBodyAndHeader(
            "http://localhost:8088/api/books",
            bookJson,
            Exchange.CONTENT_TYPE,
            "application/json",
            String.class
        );

        String queryResponse = template.requestBodyAndHeader(
            "http://localhost:8088/graphql",
            "{\"query\":\"{ books { title } }\"}",
            "Content-Type",
            "application/json",
            String.class
        );

        assertNotNull(postResponse);
        assertTrue(postResponse.contains("Camel in Action"));
    }

    @Test
    void whenAddingInvalidBook_thenReturnError() {
        String mutation = "{\"query\":\"mutation { " +
            "addBook(id: \\\"4\\\", title: \\\"\\\", author: \\\"Test\\\") { id title }" +
            "}\"}";

        String response = template.requestBodyAndHeader(
            "http://localhost:8088/graphql",
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