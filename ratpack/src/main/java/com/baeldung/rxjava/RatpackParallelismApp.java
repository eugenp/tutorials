package com.baeldung.rxjava;

import com.baeldung.model.Movie;
import com.baeldung.rxjava.service.MovieObservableService;
import com.baeldung.rxjava.service.impl.MovieObservableServiceImpl;

import ratpack.jackson.Jackson;
import ratpack.rx.RxRatpack;
import ratpack.server.RatpackServer;
import rx.Observable;

public class RatpackParallelismApp {

    /**
     * Try hitting http://localhost:5050/movies to see the application in action.
     * 
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RxRatpack.initialize();
        RatpackServer.start(def -> def.registryOf(regSpec -> regSpec.add(MovieObservableService.class, new MovieObservableServiceImpl()))
            .handlers(chain -> chain.get("movies", ctx -> {
                MovieObservableService movieSvc = ctx.get(MovieObservableService.class);
                Observable<Movie> movieObs = movieSvc.getMovies();
                Observable<String> upperCasedNames = movieObs.compose(RxRatpack::forkEach)
                    .map(movie -> movie.getName()
                        .toUpperCase())
                    .serialize();
                RxRatpack.promise(upperCasedNames)
                    .then(movie -> {
                        ctx.render(Jackson.json(movie));
                    });
            })));
    }
}
