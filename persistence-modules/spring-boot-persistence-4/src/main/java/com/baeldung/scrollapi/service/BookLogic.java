package com.baeldung.scrollapi.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.ScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.support.WindowIterator;
import org.springframework.stereotype.Service;

import com.baeldung.scrollapi.entity.BookReview;
import com.baeldung.scrollapi.repository.BookRepository;

@Service
public class BookLogic {

    @Autowired
    private BookRepository bookRepository;

    public List<BookReview> getBooksUsingOffset(String rating) {
        OffsetScrollPosition offset = ScrollPosition.offset();

        Window<BookReview> bookReviews = bookRepository.findFirst5ByBookRating(rating, offset);
        List<BookReview> bookReviewsResult = new ArrayList<>();
        do {
            bookReviews.forEach(bookReviewsResult::add);
            bookReviews = bookRepository.findFirst5ByBookRating(rating, (OffsetScrollPosition) bookReviews.positionAt(bookReviews.size() - 1));
        } while (!bookReviews.isEmpty() && bookReviews.hasNext());

        return bookReviewsResult;
    }

    public List<BookReview> getBooksUsingOffSetFilteringAndWindowIterator(String rating) {
        WindowIterator<BookReview> bookReviews = WindowIterator.of(position -> bookRepository.findFirst5ByBookRating("3.5", (OffsetScrollPosition) position))
            .startingAt(ScrollPosition.offset());
        List<BookReview> bookReviewsResult = new ArrayList<>();

        bookReviews.forEachRemaining(bookReviewsResult::add);
        return bookReviewsResult;
    }

    public List<BookReview> getBooksUsingKeySetFiltering(String rating) {
        WindowIterator<BookReview> bookReviews = WindowIterator.of(position -> bookRepository.findFirst5ByBookRating(rating, (KeysetScrollPosition) position))
            .startingAt(ScrollPosition.keyset());
        List<BookReview> bookReviewsResult = new ArrayList<>();

        bookReviews.forEachRemaining(bookReviewsResult::add);
        return bookReviewsResult;
    }
}
