package com.baeldung.rxjava;

import com.baeldung.model.Movie;
import com.baeldung.rxjava.service.MovieService;
import com.baeldung.rxjava.service.impl.MovieServiceImpl;

import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Observable;

public class RatpackPromiseApp {

    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();
        RatpackServer.start(def -> def.registryOf(rSpec -> rSpec.add(MovieService.class, new MovieServiceImpl()))
            .handlers(chain -> chain.get("movie", ctx -> {
                MovieService movieSvc = ctx.get(MovieService.class);
                Observable<Movie> movieObs = movieSvc.getMovie();
                RxRatpack.promiseSingle(movieObs)
                    .then(movie -> ctx.render(Jackson.json(movie)));
            })
                .get("movies", ctx -> {
                    MovieService movieSvc = ctx.get(MovieService.class);
                    Observable<Movie> movieObs = movieSvc.getMovies();
                    RxRatpack.promise(movieObs)
                        .then(movie -> ctx.render(Jackson.json(movie)));
                })));
    }
}
