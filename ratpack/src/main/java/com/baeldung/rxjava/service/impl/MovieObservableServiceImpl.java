package com.baeldung.rxjava.service.impl;

import com.baeldung.model.Movie;
import com.baeldung.rxjava.service.MovieObservableService;

import rx.Observable;

public class MovieObservableServiceImpl implements MovieObservableService {

    @Override
    public Observable<Movie> getMovie() {
        Movie movie = new Movie();
        movie.setName("The Godfather");
        movie.setYear("1972");
        movie.setDirector("Coppola");
        movie.setRating(9.2);
        return Observable.just(movie);
    }

    @Override
    public Observable<Movie> getMovies() {
        Movie movie = new Movie();
        movie.setName("The Godfather");
        movie.setYear("1972");
        movie.setDirector("Coppola");
        movie.setRating(9.2);
        Movie movie2 = new Movie();
        movie2.setName("The Godfather Part 2");
        movie2.setYear("1974");
        movie2.setDirector("Coppola");
        movie2.setRating(9.0);
        return Observable.just(movie, movie2);

    }
}
