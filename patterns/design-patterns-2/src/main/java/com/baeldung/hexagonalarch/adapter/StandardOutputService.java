package com.baeldung.hexagonalarch.adapter;

import com.baeldung.hexagonalarch.domain.RandomFact;
import com.baeldung.hexagonalarch.domain.ports.StorageService;

public class StandardOutputService implements StorageService {

    @Override
    public void save(RandomFact randomFact) {
        System.out.println(randomFact);
    }
}
