package com.baeldung.spring.cloud.bootstrap.svcrating;

import com.baeldung.spring.cloud.bootstrap.svcrating.rating.Rating;
import com.baeldung.spring.cloud.bootstrap.svcrating.rating.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private RatingService ratingService;

    @Autowired
    public DataLoader(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        this.ratingService.createRating(new Rating(1L, 1));
        this.ratingService.createRating(new Rating(1L, 2));
        this.ratingService.createRating(new Rating(2L, 3));
        this.ratingService.createRating(new Rating(2L, 4));
        this.ratingService.createRating(new Rating(2L, 5));
    }
}
