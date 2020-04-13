package com.baeldung.hexagonal.application;

import static spark.Spark.*;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.UUID;

import com.baeldung.hexagonal.application.service.impl.MovieSearchServiceImpl;
import com.baeldung.hexagonal.application.service.impl.MovieServiceImpl;
import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.service.MovieSearchService;
import com.baeldung.hexagonal.domain.service.MovieService;
import com.baeldung.hexagonal.repository.impl.MongoDbMovieRepository;
import com.google.gson.Gson;

/**
 * Sample web interface for movie rating system
 */
public final class MovieRaterWebApplication {

    public static void main(final String[] args) throws UnknownHostException, IOException {
        port(8181);

        final MongoDbMovieRepository mongoDbMovieRepository = new MongoDbMovieRepository();
        final MovieService movieService = new MovieServiceImpl(mongoDbMovieRepository);
        final MovieSearchService movieSearchService = new MovieSearchServiceImpl(mongoDbMovieRepository);
        final Gson gson = new Gson();

        postMovieAPI(movieService, movieSearchService, gson);

        putMovieRatingAPI(movieService, gson);

        getMovieRatingAPI(movieService, gson);

        exception(AppException.class, (exception, request, response) -> {
            response.status(exception.getStatus());
            response.body(gson.toJson(new AppResponse(exception.getStatus(), exception.getMessage())));
        });

    }

    private static void postMovieAPI(final MovieService movieService, final MovieSearchService movieSearchService, final Gson gson) {
        post("/api/movies", (req, res) -> {
            final String movieName = req.queryParams("movieName");
            res.type("application/json");
            if (movieName == null || movieName.trim()
                .length() == 0) {
                throw new AppException(400, "movieName must be provided");
            }

            final Movie movie = movieSearchService.findByName(movieName);
            if (movie != null) {
                throw new AppException(409, "movieName already exists with id " + movie.getId());
            }

            final UUID uuid = movieService.saveMovie(movieName);
            return gson.toJson(new AppResponse(200, "movie created with id " + uuid));
        });
    }

    private static void putMovieRatingAPI(final MovieService movieService, final Gson gson) {
        put("/api/movies/:id/rating", (req, res) -> {
            final String movieId = req.params("id");
            res.type("application/json");
            if (movieId == null || movieId.trim()
                .length() == 0) {
                throw new AppException(400, "movieId must be provided");
            }
            int noOfStars;
            try {
                noOfStars = Integer.valueOf(req.queryParamOrDefault("noOfStars", "0"));
            } catch (NumberFormatException nfe) {
                throw new AppException(400, "noOfStars must be an integer", nfe);
            }
            final String reviewer = req.queryParams("reviewer");
            if (reviewer == null || reviewer.trim()
                .length() == 0) {
                throw new AppException(400, "reviewer must be provided");
            }

            try {
                final Movie movie = movieService.findMovieById(UUID.fromString(movieId));
                if (movie == null) {
                    
                }
                movieService.addRating(UUID.fromString(movieId), reviewer, noOfStars);
                return gson.toJson(new AppResponse(200, "Done"));
            } catch (IllegalArgumentException e) {
                throw new AppException(400, "invalid movie id provided", e);
            } catch (IllegalStateException e) {
                throw new AppException(404, "moview with id " + movieId + " not found", e);
            }
        });
    }

    private static void getMovieRatingAPI(final MovieService movieService, final Gson gson) {
        get("/api/movies/:id/avg_rating", (req, res) -> {
            final String movieId = req.params("id");
            res.type("application/json");
            if (movieId == null || movieId.trim()
                .length() == 0) {
                throw new AppException(400, "movieId must be provided");
            }
            try {
                final UUID uuid = UUID.fromString(movieId);
                final Movie movie = movieService.findMovieById(uuid);
                if (movie == null) {
                    throw new AppException(404, "movie with id " + movieId + " not found");
                }
                return gson.toJson(new AppResponse(200, "movie : " + movie.getName() + " has average rating of : " + movieService.getAverageRating(uuid)));
            } catch (IllegalArgumentException e) {
                throw new AppException(400, "invalid movie id provided", e);
            } catch (IllegalStateException e) {
                throw new AppException(404, "moview with id " + movieId + " not found", e);
            }
        });
    }

    @SuppressWarnings("unused")
    static class AppResponse {
        private final int status;
        private final String message;

        AppResponse(final int status, final String message) {
            this.status = status;
            this.message = message;
        }

        int getStatus() {
            return status;
        }

        String getMessage() {
            return message;
        }
    }

    static class AppException extends RuntimeException {
        private static final long serialVersionUID = 1L;
        private final int status;

        AppException(final int status, final String message) {
            this(status, message, null);
        }

        AppException(final int status, final String message, final Throwable cause) {
            super(message, cause);
            this.status = status;
        }

        int getStatus() {
            return status;
        }
    }
}
