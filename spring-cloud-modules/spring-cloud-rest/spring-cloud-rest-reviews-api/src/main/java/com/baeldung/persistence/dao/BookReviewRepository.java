package com.baeldung.persistence.dao;

import com.baeldung.persistence.model.BookReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "reviews", path = "reviews")
public interface BookReviewRepository extends CrudRepository<BookReview, Long> {
    Page<BookReview> findByBookId(@Param("bookId") long bookId, Pageable pageable);
}
