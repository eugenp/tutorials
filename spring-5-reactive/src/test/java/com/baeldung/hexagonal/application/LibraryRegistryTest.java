package com.baeldung.hexagonal.application;

import com.baeldung.hexagonal.adapter.InMemoryLibraryRepository;
import com.baeldung.hexagonal.domain.Article;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

class LibraryRegistryTest {

    @Test
    void searchByTitle_record_exists() {

        // given
        LibraryRepository libraryRepository = new InMemoryLibraryRepository();
        LibraryInput libraryInput = () -> "Title 1";

        LibraryRegistry libraryRegistry = new LibraryRegistry(libraryRepository, libraryInput);

        // when
        Optional<Article> result = libraryRegistry.searchByTitle();

        // then
        assertThat(result.isPresent(), is(true));

        Article article = result.get();
        assertEquals("Title 1", article.getTitle());
    }

    @Test
    void searchByTitle_record_not_exists() {

        // given
        LibraryRepository libraryRepository = new InMemoryLibraryRepository();
        LibraryInput libraryInput = () -> "Foo";

        LibraryRegistry libraryRegistry = new LibraryRegistry(libraryRepository, libraryInput);

        // when
        Optional<Article> result = libraryRegistry.searchByTitle();

        // then
        assertThat(result.isPresent(), is(false));
    }
}