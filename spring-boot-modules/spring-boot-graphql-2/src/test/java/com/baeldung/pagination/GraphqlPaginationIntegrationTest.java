package com.baeldung.pagination;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.tester.AutoConfigureGraphQlTester;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;

import com.baeldung.pagination.entity.Book;
import com.baeldung.pagination.repository.BookRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
@ActiveProfiles("pagination")
class GraphqlPaginationIntegrationTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private BookRepository bookRepository;

    @BeforeEach
    void setup() {
        bookRepository.deleteAll();

        for (int i = 1; i <= 50; i++) {
            Book book = new Book();
            book.setTitle("Test Book " + i);
            book.setAuthor("Test Author " + i);
            bookRepository.save(book);
        }
    }

    @Test
    void givenPageAndSize_whenQueryBooks_thenShouldReturnCorrectPage() {
        String query = """
            query {
                books(page: 0, size: 5) {
                    content {
                        id
                        title
                        author
                    }
                    totalPages
                    totalElements
                    number
                    size
                }
            }
            """;

        graphQlTester.document(query)
            .execute()
            .path("data.books")
            .entity(BookPageResponse.class)
            .satisfies(bookPage -> {
                assertEquals(5, bookPage.getContent().size());
                assertEquals(0, bookPage.getNumber());
                assertEquals(5, bookPage.getSize());
                assertEquals(50, bookPage.getTotalElements());
                assertEquals(10, bookPage.getTotalPages());
            });
    }

    @Test
    void givenCursorAndLimit_whenQueryBooksByCursor_thenShouldReturnNextBatch() {
        // First page
        String firstPageQuery = """
            query {
                booksByCursor(limit: 5) {
                    edges {
                        node {
                            id
                        }
                        cursor
                    }
                    pageInfo {
                        endCursor
                        hasNextPage
                    }
                }
            }
            """;

        BookConnectionResponse firstPage = graphQlTester.document(firstPageQuery)
            .execute()
            .path("data.booksByCursor")
            .entity(BookConnectionResponse.class)
            .get();

        assertEquals(5, firstPage.getEdges().size());
        assertTrue(firstPage.getPageInfo().isHasNextPage());
        assertNotNull(firstPage.getPageInfo().getEndCursor());

        // Second page
        String secondPageQuery = String.format("""
            query {
                booksByCursor(cursor: "%s", limit: 5) {
                    edges {
                        node {
                            id
                        }
                    }
                    pageInfo {
                        hasNextPage
                    }
                }
            }
            """, firstPage.getPageInfo().getEndCursor());

        graphQlTester.document(secondPageQuery)
            .execute()
            .path("data.booksByCursor")
            .entity(BookConnectionResponse.class)
            .satisfies(secondPage -> {
                assertEquals(5, secondPage.getEdges().size());
                assertTrue(secondPage.getPageInfo().isHasNextPage());
            });
    }

    private static class BookPageResponse {
        private List<Book> content;
        private int totalPages;
        private long totalElements;
        private int number;
        private int size;

        public List<Book> getContent() {
            return content;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public long getTotalElements() {
            return totalElements;
        }

        public int getNumber() {
            return number;
        }

        public int getSize() {
            return size;
        }
    }

    private static class BookConnectionResponse {
        private List<BookEdgeResponse> edges;
        private PageInfoResponse pageInfo;

        public List<BookEdgeResponse> getEdges() {
            return edges;
        }

        public PageInfoResponse getPageInfo() {
            return pageInfo;
        }
    }

    private static class BookEdgeResponse {
        private Book node;
        private String cursor;

        public Book getNode() {
            return node;
        }

        public String getCursor() {
            return cursor;
        }
    }

    private static class PageInfoResponse {
        private boolean hasNextPage;
        private String endCursor;

        public boolean isHasNextPage() {
            return hasNextPage;
        }

        public String getEndCursor() {
            return endCursor;
        }
    }
}