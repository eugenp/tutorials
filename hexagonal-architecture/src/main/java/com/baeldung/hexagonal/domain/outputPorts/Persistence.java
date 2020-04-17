package com.baeldung.hexagonal.domain.outputPorts;

import java.util.List;

import com.baeldung.hexagonal.domain.MovieData;

public interface Persistence {

    List<MovieData> retrieveMovies();

}
