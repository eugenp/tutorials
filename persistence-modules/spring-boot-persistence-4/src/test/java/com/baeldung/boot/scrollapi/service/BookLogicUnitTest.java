package com.baeldung.boot.scrollapi.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.scrollapi.ScrollAPIApplication;
import com.baeldung.scrollapi.entity.BookReview;
import com.baeldung.scrollapi.repository.BookRepository;
import com.baeldung.scrollapi.service.BookLogic;

@SpringBootTest(classes = ScrollAPIApplication.class)
class BookLogicUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookLogic bookLogic;

    @BeforeEach
    public void beforeEach() {
        IntStream.rangeClosed(1, 5)
            .forEach(i -> insertBookReview());
    }

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void givenBookReviewInTable_whenGetBooksUsingOffset_returnsBookReviews() {
        List<BookReview> bookReviews = bookLogic.getBooksUsingOffset("3.5");
        assertThat(bookReviews.size()).isEqualTo(5);
    }

    @Test
    public void givenBookReviewInTable_whenGetBooksUsingOffSetFilteringAndWindowIterator_returnsBookReviews() {
        List<BookReview> bookReviews = bookLogic.getBooksUsingOffSetFilteringAndWindowIterator("3.5");
        assertThat(bookReviews.size()).isEqualTo(5);
    }

    @Test
    public void givenBookReviewInTable_whenGetBooksUsingKeySetFiltering_returnsBookReviews() {
        List<BookReview> bookReviews = bookLogic.getBooksUsingKeySetFiltering("3.5");
        assertThat(bookReviews.size()).isEqualTo(5);
    }

    private void insertBookReview() {
        BookReview bookReview = getBookReview();
        bookRepository.save(bookReview);
    }

    private static BookReview getBookReview() {
        BookReview bookReview = new BookReview();
        String seed = UUID.randomUUID()
            .toString();
        bookReview.setIsbn("isbn" + seed);
        bookReview.setBookRating("3.5");
        bookReview.setUserId(seed);

        return bookReview;
    }
}
