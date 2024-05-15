package com.baeldung.monads;

import static com.baeldung.monads.AuthorsRepository.findAuthorByName;
import static com.baeldung.monads.AuthorsRepository.findLatestBookByAuthorId;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import reactor.core.publisher.Mono;

class MonadsUnitTest {

    @Test
    void whenCallingMap_thenTransformsTheValue() {
        Stream<Long> longs = Stream.of(1712000000L, 1713000000L, 1714000000L);

        Stream<Instant> instants = longs.map(Instant::ofEpochSecond);

        assertThat(instants).isNotNull()
            .hasSize(3);
    }

    @Test
    void whenCallingThenApply_thenTransformsTheValue() {
        CompletableFuture<Long> timestamp = CompletableFuture.completedFuture(1713000000L);

        CompletableFuture<Instant> instant = timestamp.thenApply(Instant::ofEpochSecond);

        assertThat(instant.join()).isNotNull()
            .isEqualTo(Instant.ofEpochSecond(1713000000L));
    }

    @Test
    void whenUsingMapInsteadOfFlatMap_thenReturnsNestedMonads() {
        Mono<Author> author = findAuthorByName("John Doe");
        Mono<Mono<Book>> book = author.map(it -> findLatestBookByAuthorId(it.getAuthorId()));

        assertThat(book).isInstanceOf(Mono.class);
        assertThat(book.block()).isInstanceOf(Mono.class);
        assertThat(book.block()
            .block()).isInstanceOf(Book.class);
    }

    @Test
    void whenUsingFlatMap_thenReturnsFlattenTheNestedMonads() {
        Mono<Author> author = findAuthorByName("John Doe");
        Mono<Book> book = author.flatMap(it -> findLatestBookByAuthorId(it.getAuthorId()));

        assertThat(book).isInstanceOf(Mono.class);
        assertThat(book.block()).isInstanceOf(Book.class);
    }

    @Test
    void givenInvalidBook_whenValidatingInImperativeStyle_thenThrowsException() {
        BooksService booksService = new BooksService();
        Book invalidBook = new Book("-INVALID ISBN-", 1L, "---");

        assertThatThrownBy(() -> booksService.validateBookImperativeStyle(invalidBook))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid ISBN");
    }

    @Test
    void givenInvalidBook_whenValidatingInFunctionalStyle_thenThrowsException() {
        BooksService booksService = new BooksService();
        Book invalidBook = new Book("-INVALID ISBN-", 1L, "---");

        assertThatThrownBy(() -> booksService.validateBookFunctionalStyle(invalidBook))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid ISBN");
    }


}
