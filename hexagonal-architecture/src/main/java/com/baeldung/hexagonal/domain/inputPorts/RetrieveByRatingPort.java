package com.baeldung.hexagonal.domain.inputPorts;

import java.util.List;

import com.baeldung.hexagonal.domain.MovieData;

public interface RetrieveByRatingPort {

    List<MovieData> retrieveMoviesAbove(float rating);

}
