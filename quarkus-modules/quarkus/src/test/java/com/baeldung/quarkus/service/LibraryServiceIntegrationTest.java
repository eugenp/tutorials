package com.baeldung.quarkus.service;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static org.junit.jupiter.api.Assertions.assertFalse;

@QuarkusTest
class LibraryServiceIntegrationTest {

    @Inject
    LibraryService libraryService;

    @Test
    void whenFindByAuthor_thenBookShouldBeFound() {
        assertFalse(libraryService.find("Frank Herbert").isEmpty());
    }

}
