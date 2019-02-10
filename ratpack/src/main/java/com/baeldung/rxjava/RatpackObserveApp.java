package com.baeldung.rxjava;

import java.util.List;

import com.baeldung.model.Movie;
import com.baeldung.rxjava.service.MoviePromiseService;
import com.baeldung.rxjava.service.impl.MoviePromiseServiceImpl;

import ratpack.exec.Promise;
import ratpack.handling.Handler;
import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;

public class RatpackObserveApp {

    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();

        Handler moviePromiseHandler = ctx -> {
            MoviePromiseService promiseSvc = ctx.get(MoviePromiseService.class);
            Promise<Movie> moviePromise = promiseSvc.getMovie();
            RxRatpack.observe(moviePromise)
                .subscribe(movie -> ctx.render(Jackson.json(movie)));
        };

        Handler moviesPromiseHandler = ctx -> {
            MoviePromiseService promiseSvc = ctx.get(MoviePromiseService.class);
            Promise<List<Movie>> moviePromises = promiseSvc.getMovies();
            RxRatpack.observeEach(moviePromises)
                .toList()
                .subscribe(movie -> ctx.render(Jackson.json(movie)));
        };

        RatpackServer.start(def -> def.registryOf(regSpec -> regSpec.add(MoviePromiseService.class, new MoviePromiseServiceImpl()))
            .handlers(chain -> chain.get("movie", moviePromiseHandler)
                .get("movies", moviesPromiseHandler)));
    }
}
