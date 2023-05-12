package com.baeldung.springdatacaching.repositories;

import com.baeldung.springdatacaching.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.AopTestUtils;

import java.util.UUID;

import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
public class BookRepositoryCachingIntegrationTest {

    private static final Book DUNE = new Book(UUID.randomUUID(), "Dune");
    private static final Book FOUNDATION = new Book(UUID.randomUUID(), "Foundation");

    private BookRepository mock;

    @Autowired
    private BookRepository bookRepository;

    @EnableCaching
    @Configuration
    public static class CachingTestConfig {

        @Bean
        public BookRepository bookRepositoryMockImplementation() {
            return mock(BookRepository.class);
        }

        @Bean
        public CacheManager cacheManager() {
            return new ConcurrentMapCacheManager("books");
        }

    }

    @BeforeEach
    void setUp() {
        mock = AopTestUtils.getTargetObject(bookRepository);

        reset(mock);

        when(mock.findFirstByTitle(eq("Foundation")))
                .thenReturn(of(FOUNDATION));

        when(mock.findFirstByTitle(eq("Dune")))
                .thenReturn(of(DUNE))
                .thenThrow(new RuntimeException("Book should be cached!"));
    }

    @Test
    void givenCachedBook_whenFindByTitle_thenRepositoryShouldNotBeHit() {
        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));
        verify(mock).findFirstByTitle("Dune");

        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));
        assertEquals(of(DUNE), bookRepository.findFirstByTitle("Dune"));

        verifyNoMoreInteractions(mock);
    }

    @Test
    void givenNotCachedBook_whenFindByTitle_thenRepositoryShouldBeHit() {
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));
        assertEquals(of(FOUNDATION), bookRepository.findFirstByTitle("Foundation"));

        verify(mock, times(3)).findFirstByTitle("Foundation");
    }

}
