package com.baeldung.scrollapi.repository;

import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.data.domain.OffsetScrollPosition;
import org.springframework.data.domain.Window;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.Repository;

import com.baeldung.scrollapi.entity.BookReview;

public interface BookRepository extends JpaRepository<BookReview, Long> {

    Window<BookReview> findFirst5ByBookRating(String bookRating, OffsetScrollPosition position);

    Window<BookReview> findFirst5ByBookRating(String bookRating, KeysetScrollPosition position);
}
