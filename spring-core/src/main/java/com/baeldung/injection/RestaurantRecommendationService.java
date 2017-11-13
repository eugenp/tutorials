package com.baeldung.injection;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantRecommendationService {

    @Autowired
    private RestaurantFinder finder;

    public List<Restaurant> recommend(Long userId) {
        return finder.find(userId);
    }

}
