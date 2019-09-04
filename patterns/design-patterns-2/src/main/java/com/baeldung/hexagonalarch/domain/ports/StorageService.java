package com.baeldung.hexagonalarch.domain.ports;

import java.io.IOException;

import com.baeldung.hexagonalarch.domain.RandomFact;


public interface StorageService {
    void save(RandomFact randomFacts) throws IOException;
}
