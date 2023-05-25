package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface RatingRepository extends JpaRepository<Rating, Long>{
    List<Rating> findRatingsByBookId(Long bookId);
}
