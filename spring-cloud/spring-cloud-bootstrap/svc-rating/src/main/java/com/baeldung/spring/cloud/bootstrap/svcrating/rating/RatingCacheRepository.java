package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.util.List;

public interface RatingCacheRepository {

    List<Rating> findCachedRatingsByBookId(Long bookId);
    Rating findCachedRatingById(Long ratingId);
    List<Rating> findAllCachedRatings();
    boolean createRating(Rating persisted);
    boolean updateRating(Rating persisted);
    boolean deleteRating(Long ratingId);
}
