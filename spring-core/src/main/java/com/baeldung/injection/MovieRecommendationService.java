package com.baeldung.injection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieRecommendationService {

    private MovieFinder finder;

    @Autowired
    public void setFinder(MovieFinder finder) {
        this.finder = finder;
    }

    public List<Movie> recommend(Long userId) {
        return finder.find(userId);
    }

}
