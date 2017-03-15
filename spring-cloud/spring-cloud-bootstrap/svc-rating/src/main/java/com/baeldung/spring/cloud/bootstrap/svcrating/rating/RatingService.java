package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional(readOnly = true)
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public Rating findRatingById(Long ratingId) {
        return Optional.ofNullable(ratingRepository.findOne(ratingId))
            .orElseThrow(() -> new RatingNotFoundException("Rating not found. ID: " + ratingId));
    }

    public List<Rating> findRatingsByBookId(Long bookId) {
        return ratingRepository.findRatingsByBookId(bookId);
    }

    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rating createRating(Rating rating) {
        final Rating newRating = new Rating();
        newRating.setBookId(rating.getBookId());
        newRating.setStars(rating.getStars());
        return ratingRepository.save(newRating);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteRating(Long ratingId) {
        ratingRepository.delete(ratingId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Rating updateRating(Map<String, String> updates, Long ratingId) {
        final Rating rating = findRatingById(ratingId);
        updates.keySet()
            .forEach(key -> {
                switch (key) {
                case "stars":
                    rating.setStars(Integer.parseInt(updates.get(key)));
                    break;
                }
            });
        return ratingRepository.save(rating);
    }

    public Rating updateRating(Rating rating, Long ratingId) {
        Preconditions.checkNotNull(rating);
        Preconditions.checkState(rating.getId() == ratingId);
        Preconditions.checkNotNull(ratingRepository.findOne(ratingId));
        return ratingRepository.save(rating);
    }
}
