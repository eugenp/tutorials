package com.baeldung.hexagonal.controller;

import com.baeldung.hexagonal.service.RecommendationService;

public class RecommendationController {

    private final RecommendationService service;

    public RecommendationController(RecommendationService service) {
        this.service = service;
    }
}
