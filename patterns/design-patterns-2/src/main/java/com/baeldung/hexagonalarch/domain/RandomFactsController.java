package com.baeldung.hexagonalarch.domain;

import java.io.IOException;

import com.baeldung.hexagonalarch.domain.ports.FetchFactsRepository;
import com.baeldung.hexagonalarch.domain.ports.StorageService;

public class RandomFactsController {
    private FetchFactsRepository fetchFactsRepository;
    private StorageService storageService;

    public RandomFactsController(FetchFactsRepository fetchFactsRepository, StorageService storageService) {
        this.fetchFactsRepository = fetchFactsRepository;
        this.storageService = storageService;
    }

    public void execute() {
        try {
            RandomFact randomFact = fetchFactsRepository.fetch();
            storageService.save(randomFact);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
