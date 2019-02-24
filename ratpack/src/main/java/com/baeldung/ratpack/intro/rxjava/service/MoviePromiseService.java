package com.baeldung.ratpack.intro.rxjava.service;

import java.util.List;

import com.baeldung.ratpack.intro.model.Movie;

import ratpack.exec.Promise;

public interface MoviePromiseService {

    Promise<List<Movie>> getMovies();

    Promise<Movie> getMovie();

}
