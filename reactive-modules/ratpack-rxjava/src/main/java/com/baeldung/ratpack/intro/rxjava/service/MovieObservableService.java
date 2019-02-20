package com.baeldung.ratpack.intro.rxjava.service;

import com.baeldung.ratpack.intro.model.Movie;

import rx.Observable;

public interface MovieObservableService {

    Observable<Movie> getMovies();

    Observable<Movie> getMovie();

}
