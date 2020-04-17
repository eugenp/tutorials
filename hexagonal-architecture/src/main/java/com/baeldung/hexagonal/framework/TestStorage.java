package com.baeldung.hexagonal.framework;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.hexagonal.domain.MovieData;
import com.baeldung.hexagonal.domain.outputPorts.Persistence;

public class TestStorage implements Persistence {

    @Override
    public List<MovieData> retrieveMovies() {
        List<MovieData> list = new ArrayList<>();
        list.add(new MovieData("A", 5));
        list.add(new MovieData("B", 6));
        list.add(new MovieData("C", 9));
        list.add(new MovieData("D", 10));

        return list;
    }

}
