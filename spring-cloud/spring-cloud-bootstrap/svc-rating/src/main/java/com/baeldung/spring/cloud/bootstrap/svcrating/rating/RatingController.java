package com.baeldung.spring.cloud.bootstrap.svcrating.rating;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    @GetMapping
    public List<Rating> findRatingsByBookId(@RequestParam(required = false, defaultValue = "0") Long bookId) {
        if (bookId.equals(0L)) {
            return ratingService.findAllRatings();
        }
        return ratingService.findRatingsByBookId(bookId);
    }

    @PostMapping
    public Rating createRating(@RequestBody Rating rating) {
        return ratingService.createRating(rating);
    }

    @DeleteMapping("/{ratingId}")
    public void deleteRating(@PathVariable Long ratingId) {
        ratingService.deleteRating(ratingId);
    }

    @PutMapping("/{ratingId}")
    public Rating updateRating(@RequestBody Rating rating, @PathVariable Long ratingId) {
        return ratingService.updateRating(rating, ratingId);
    }

    @PatchMapping("/{ratingId}")
    public Rating updateRating(@RequestBody Map<String, String> updates, @PathVariable Long ratingId) {
        return ratingService.updateRating(updates, ratingId);
    }
}
