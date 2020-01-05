package com.baeldung.hexagonalarchitecture;

import com.baeldung.hexagonalarchitecture.drivenports.ConsolePrinter;
import com.baeldung.hexagonalarchitecture.drivenports.IFetchMovieReviews;
import com.baeldung.hexagonalarchitecture.drivenports.IPrintMovieReviews;
import com.baeldung.hexagonalarchitecture.drivenports.MovieReviewsRepo;
import com.baeldung.hexagonalarchitecture.driverports.IUserInput;
import com.baeldung.hexagonalarchitecture.driverports.UserCommandBoundary;
import com.baeldung.hexagonalarchitecture.model.MovieSearchRequest;
import com.baeldung.hexagonalarchitecture.model.MovieUser;

public class Main {

    public static void main(String[] args) {
        IFetchMovieReviews fetchMovieReviews = new MovieReviewsRepo();
        IPrintMovieReviews printMovieReviews = new ConsolePrinter();
        IUserInput userCommandBoundary = new UserCommandBoundary(fetchMovieReviews, printMovieReviews);
        MovieUser movieUser = new MovieUser(userCommandBoundary);
        MovieSearchRequest starWarsRequest = new MovieSearchRequest("StarWars");
        MovieSearchRequest starTreckRequest = new MovieSearchRequest("StarTreck");

        System.out.println("Displaying reviews for movie " + starTreckRequest.getMovieName());
        movieUser.processInput(starTreckRequest);
        System.out.println("Displaying reviews for movie " + starWarsRequest.getMovieName());
        movieUser.processInput(starWarsRequest);
    }

}
