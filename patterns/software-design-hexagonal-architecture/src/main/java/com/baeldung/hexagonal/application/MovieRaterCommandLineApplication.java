package com.baeldung.hexagonal.application;

import java.util.Scanner;
import java.util.UUID;

import com.baeldung.hexagonal.application.service.impl.MovieSearchServiceImpl;
import com.baeldung.hexagonal.application.service.impl.MovieServiceImpl;
import com.baeldung.hexagonal.domain.Movie;
import com.baeldung.hexagonal.domain.service.MovieSearchService;
import com.baeldung.hexagonal.domain.service.MovieService;
import com.baeldung.hexagonal.repository.impl.InMemoryMovieRepository;

/**
 * Sample command line interface for movie rating system
 */
public final class MovieRaterCommandLineApplication {
    private final MovieService movieService;
    private final MovieSearchService movieSearchService;

    private MovieRaterCommandLineApplication(final MovieService movieService, final MovieSearchService movieSearchService) {
        this.movieSearchService = movieSearchService;
        this.movieService = movieService;
    }

    public static void main(final String[] args) {

        final InMemoryMovieRepository inMemoryMovieRepository = new InMemoryMovieRepository();
        final MovieServiceImpl movieService = new MovieServiceImpl(inMemoryMovieRepository);
        final MovieSearchServiceImpl movieSearchService = new MovieSearchServiceImpl(inMemoryMovieRepository);

        final MovieRaterCommandLineApplication commandLineMovieRaterApplication = new MovieRaterCommandLineApplication(movieService, movieSearchService);
        commandLineMovieRaterApplication.start();
    }

    private void start() {
        boolean doNotQuit = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (doNotQuit) {
                System.out.println("Enter movie name: ");
                final String movieName = scanner.next();

                System.out.println("Enter your rating (no of stars): ");
                final int noOfStars = scanner.nextInt();

                System.out.println("Enter your name: ");
                final String reviewer = scanner.next();

                final Movie movie = movieSearchService.findByName(movieName);
                final UUID uuid = (movie == null) ? movieService.saveMovie(movieName) : movie.getId();
                movieService.addRating(uuid, reviewer, noOfStars);

                System.out.println("Thank you for rating movie: " + movieName);
                System.out.println("Average rating for movie: " + movieName + " is " + movieService.getAverageRating(uuid) + " stars");

                System.out.println("Do you wish to quit (yes/no) ?");
                final String answer = scanner.next();
                doNotQuit = "yes".equals(answer) || "y".equals(answer);
            }
        }
        System.out.println("Goodbye!");
    }
}
