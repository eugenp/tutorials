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
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("eureka-test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class, webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(classes = { MockBookServiceConfig.class })
class ServiceDiscoveryBooksClientLiveTest {

    @Autowired
    private BooksClient booksClient;

    @Lazy
    @Autowired
    private EurekaClient eurekaClient;

    @BeforeEach
    void setUp() {
        await().atMost(60, SECONDS).until(() -> eurekaClient.getApplications().size() > 0);
    }

    /**
     *  Please ensure that Eureka is running on port , 8761 for this test to pass.
     *  The EurekaServerApplication.main in spring-cloud-eureka-server project can be
     *  run to make an Eureka Server available.
     */
    @Test
    public void whenGetBooks_thenTheCorrectBooksAreReturned() {
        List<Book> books = booksClient.getBooks();

        assertEquals(1, books.size());
        assertEquals(
          new Book("Hitchhiker's guide to the galaxy", "Douglas Adams"),
          books.stream().findFirst().get());
    }

}
