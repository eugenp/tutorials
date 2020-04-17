package com.baeldung.hexagonal.clients;

import java.util.List;

import com.baeldung.hexagonal.domain.MovieData;
import com.baeldung.hexagonal.domain.inputPorts.RetrieveByRatingPort;

public class TestClient {

    private RetrieveByRatingPort retrieveByRatingPort;

    public TestClient(RetrieveByRatingPort retrieveByratingPort) {
        this.retrieveByRatingPort = retrieveByratingPort;

    }

    public List<MovieData> executeRequest(int rating) {
        return retrieveByRatingPort.retrieveMoviesAbove(rating);
    }

}
