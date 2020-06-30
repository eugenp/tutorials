package com.baeldung.hexagonalarchitecture.application.response;

import java.util.List;

import com.baeldung.hexagonalarchitecture.business.entities.Concert;

public class GetConcertsResponse {
    private final List<Concert> concerts;

    public GetConcertsResponse(final List<Concert> concerts) {
        this.concerts = concerts;
    }

    public List<Concert> getConcerts() {
        return concerts;
    }
}
