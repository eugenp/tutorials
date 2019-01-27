package com.baeldung.rxjava;

import com.baeldung.model.Movie;
import com.baeldung.rxjava.service.MovieService;
import com.baeldung.rxjava.service.impl.MovieServiceImpl;

import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Observable;

public class RatpackParallelismApp {

    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();
        RatpackServer.start(def -> def.registryOf(regSpec -> regSpec.add(MovieService.class, new MovieServiceImpl()))
            .handlers(chain -> chain.get("movies", ctx -> {
                MovieService movieSvc = ctx.get(MovieService.class);
                Observable<Movie> movieObs = movieSvc.getMovies();
                System.out.println(Thread.currentThread());
                RxRatpack.promise(movieObs.compose(RxRatpack::forkEach)
                    .map(movie -> movie.getName()
                        .toUpperCase())
                    .serialize())
                    .then(movie -> {
                        ctx.render(Jackson.json(movie));
                    });
            })));
    }
}
