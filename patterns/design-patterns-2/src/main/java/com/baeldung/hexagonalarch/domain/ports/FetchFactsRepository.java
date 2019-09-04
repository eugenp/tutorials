package com.baeldung.hexagonalarch.domain.ports;

import java.io.IOException;

import com.baeldung.hexagonalarch.domain.RandomFact;


public interface FetchFactsRepository {
    RandomFact fetch() throws IOException;
}
