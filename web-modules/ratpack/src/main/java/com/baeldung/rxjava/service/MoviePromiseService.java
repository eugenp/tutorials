package com.baeldung.rxjava.service;

import java.util.List;

import com.baeldung.model.Movie;

import ratpack.exec.Promise;

public interface MoviePromiseService {

    Promise<List<Movie>> getMovies();

    Promise<Movie> getMovie();

}
