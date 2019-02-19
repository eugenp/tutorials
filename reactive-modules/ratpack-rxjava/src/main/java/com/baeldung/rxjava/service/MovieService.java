package com.baeldung.rxjava.service;

import com.baeldung.model.Movie;

import rx.Observable;

public interface MovieService {

    Observable<Movie> getMovies();

    Observable<Movie> getMovie();

}
