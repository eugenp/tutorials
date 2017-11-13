package com.baeldung.injection;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);

        MusicRecommendationService musicRecommendationService = applicationContext.getBean(MusicRecommendationService.class);
        System.out.println("List of recommended music:");
        musicRecommendationService.recommend(Long.valueOf(1L))
            .forEach(music -> {
                System.out.println(music);
            });

        MovieRecommendationService movieRecommendationService = applicationContext.getBean(MovieRecommendationService.class);
        System.out.println("List of recommended movies:");
        movieRecommendationService.recommend(Long.valueOf(1L))
            .forEach(movie -> {
                System.out.println(movie);
            });

        RestaurantRecommendationService restaurantRecommendationService = applicationContext.getBean(RestaurantRecommendationService.class);
        System.out.println("List of recommended music:");
        restaurantRecommendationService.recommend(Long.valueOf(1L))
            .forEach(restaurant -> {
                System.out.println(restaurant);
            });

        System.exit(0);

    }

}
