package com.baeldung.rxjava.service;

import com.baeldung.model.Movie;

import rx.Observable;

public interface MovieObservableService {

    Observable<Movie> getMovies();

    Observable<Movie> getMovie();

}
