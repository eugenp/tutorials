package com.baeldung.ratpack.intro.rxjava;

import com.baeldung.ratpack.intro.model.Movie;
import com.baeldung.ratpack.intro.rxjava.service.MovieObservableService;
import com.baeldung.ratpack.intro.rxjava.service.impl.MovieObservableServiceImpl;

import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Observable;

public class RatpackPromiseApp {

    /**
     * Try hitting http://localhost:5050/movies or http://localhost:5050/movie to see the application in action.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();

        Handler movieHandler = (ctx) -> {
            MovieObservableService movieSvc = ctx.get(MovieObservableService.class);
            Observable<Movie> movieObs = movieSvc.getMovie();
            RxRatpack.promiseSingle(movieObs)
                .then(movie -> ctx.render(Jackson.json(movie)));
        };

        Handler moviesHandler = (ctx) -> {
            MovieObservableService movieSvc = ctx.get(MovieObservableService.class);
            Observable<Movie> movieObs = movieSvc.getMovies();
            RxRatpack.promise(movieObs)
                .then(movie -> ctx.render(Jackson.json(movie)));
        };

        RatpackServer.start(def -> def.registryOf(rSpec -> rSpec.add(MovieObservableService.class, new MovieObservableServiceImpl()))
            .handlers(chain -> chain.get("movie", movieHandler)
                .get("movies", moviesHandler)));
    }

}
