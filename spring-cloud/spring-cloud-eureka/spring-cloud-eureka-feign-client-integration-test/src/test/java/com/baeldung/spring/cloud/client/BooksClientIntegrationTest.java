package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.Application;
import com.baeldung.spring.cloud.model.Book;
import com.netflix.discovery.EurekaClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = {MockBookServiceConfig.class}, initializers = {EurekaContainerConfig.Initializer.class})
class BooksClientIntegrationTest {

    @Autowired
    private BooksClient booksClient;

    @Autowired
    @Lazy
    private EurekaClient eurekaClient;

    @BeforeEach
    void setUp() {
        await().atMost(60, SECONDS).until(() -> eurekaClient.getApplications().size() > 0);
    }

    @Test
    public void whenGetBooks_thenListBooksSizeGreaterThanZero() throws InterruptedException {
        List<Book> books = booksClient.getBooks();

        assertTrue(books.size() == 1);
    }

}